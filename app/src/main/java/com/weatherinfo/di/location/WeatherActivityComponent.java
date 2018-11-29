package com.weatherinfo.di.location;

import com.weatherinfo.activities.weatheractivity.WeatherActivity;

import dagger.Component;

/**
 * Created by user on 06.05.17.
 */
@LocationScope
@Component(modules = { LocationModule.class, GoogleApiClientModule.class, LocationPendingResultModule.class})
public interface WeatherActivityComponent {

    void injectWeatherActivity(WeatherActivity activity);

}
