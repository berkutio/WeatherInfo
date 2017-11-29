package com.weatherinfo.activities.weatheractivity;

import android.content.Context;
import android.location.Location;
import android.support.annotation.VisibleForTesting;
import com.weatherinfo.model.WeatherResponse;
import com.weatherinfo.network.WeatherServiceImpl;
import com.weatherinfo.utils.Constants;
import com.weatherinfo.utils.rx.SchedulerProvider;
import io.reactivex.observers.ResourceSingleObserver;


/**
 * Created by user on 23.04.17.
 */

public class PresenterActivityWeather implements IPresenterWeather {

    private Context applicationContext;

    private IViewWeather activity;

    private ResourceSingleObserver<WeatherResponse> resourceSingleObserver;

    private SchedulerProvider provider;

    public PresenterActivityWeather(Context applicationContext, SchedulerProvider provider, IViewWeather activity) {
        this.applicationContext = applicationContext;
        this.provider = provider;
        this.activity = activity;
    }

    @Override
    public void onDestroy(){
        if (resourceSingleObserver != null && !resourceSingleObserver.isDisposed()) {
            resourceSingleObserver.dispose();
        }
        activity = null;
    }

    @Override
    public void onObtainLocation(Location location) {
        WeatherServiceImpl service = new WeatherServiceImpl(applicationContext, Constants.WEATHER_URI, Constants.WEATHER_API_KEY);
        resourceSingleObserver = service.getListData(location, 6)
                .subscribeOn(provider.io())
                .observeOn(provider.mainThread())
                .subscribeWith(new ResourceSingleObserver<WeatherResponse>() {
                    @Override
                    public void onSuccess(WeatherResponse weatherResponse) {
                        activity.onReceiveWeatherForecast(weatherResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        activity.onReceiveWeatherForecast(null);
                    }
                });
    }


    @VisibleForTesting
    IViewWeather getMainView(){
        return activity;
    }

}
