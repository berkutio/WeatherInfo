package com.weatherinfo.activities.weatheractivity;

import android.content.Context;
import android.location.Location;
import android.support.annotation.VisibleForTesting;

import com.weatherinfo.model.PresenterResponse;
import com.weatherinfo.model.WeatherResponse;
import com.weatherinfo.network.WeatherRepository;
import com.weatherinfo.utils.Constants;
import com.weatherinfo.utils.rx.SchedulerProvider;

import io.reactivex.observers.DisposableSingleObserver;


/**
 * Created by user on 23.04.17.
 */

public class WeatherActivityPresenter implements WeatherPresenter {

    private static final int DAYS_QUANTITY = 6;

    private final Context context;
    private final SchedulerProvider provider;

    private WeatherView weatherView;

    private DisposableSingleObserver<WeatherResponse> disposableSingleObserver;

    WeatherActivityPresenter(Context context, SchedulerProvider provider, WeatherView weatherView) {
        this.context = context;
        this.provider = provider;
        this.weatherView = weatherView;
    }

    @Override
    public void onDestroy() {
        if (disposableSingleObserver != null && !disposableSingleObserver.isDisposed()) {
            disposableSingleObserver.dispose();
        }

        weatherView = null;
    }

    @Override
    public void onObtainLocation(Location location) {
        WeatherRepository service = new WeatherRepository(context, Constants.WEATHER_URI, Constants.WEATHER_API_KEY);

        disposableSingleObserver = service.getForecast(location, DAYS_QUANTITY)
                .subscribeOn(provider.io())
                .observeOn(provider.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherResponse>() {
                    @Override
                    public void onSuccess(WeatherResponse response) {
                        weatherView.onReceiveWeatherForecast(new PresenterResponse<>(response));
                    }

                    @Override
                    public void onError(Throwable e) {
                        weatherView.onReceiveWeatherForecast(new PresenterResponse<>(e));
                    }
                });
    }


    @VisibleForTesting
    WeatherView getMainView() {
        return weatherView;
    }

}
