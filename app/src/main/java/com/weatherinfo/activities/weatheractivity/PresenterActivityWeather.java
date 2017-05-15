package com.weatherinfo.activities.weatheractivity;

import android.content.Context;
import android.location.Location;
import android.support.annotation.VisibleForTesting;
import com.weatherinfo.model.WeatherResponse;
import com.weatherinfo.network.WeatherServiceImpl;
import com.weatherinfo.utils.Constants;
import com.weatherinfo.utils.rx.SchedulerProvider;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


/**
 * Created by user on 23.04.17.
 */

public class PresenterActivityWeather implements IPresenterWeather {

    private Context applicationContext;

    private IViewWeather activity;

    private Disposable disposable;

    private SchedulerProvider provider;

    public PresenterActivityWeather(Context applicationContext, SchedulerProvider provider, IViewWeather activity) {
        this.applicationContext = applicationContext;
        this.provider = provider;
        this.activity = activity;
    }

    @Override
    public void onDestroy(){
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        activity = null;
    }

    @Override
    public void onObtainLocation(Location location) {
        WeatherServiceImpl service = new WeatherServiceImpl(applicationContext, Constants.WEATHER_URI, Constants.WEATHER_API_KEY);
        disposable = service.getListData(location, 6)
                .subscribeOn(provider.io())
                .observeOn(provider.mainThread())
                .subscribeWith(new DisposableObserver<WeatherResponse>() {
                    @Override
                    public void onNext(WeatherResponse response) {
                        activity.onReceiveWeatherForecast(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        activity.onReceiveWeatherForecast(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @VisibleForTesting
    IViewWeather getMainView(){
        return activity;
    }

}
