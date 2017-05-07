package com.weatherinfo.di.location;

import com.weatherinfo.activities.weatheractivity.WeatherActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by user on 06.05.17.
 */
@Singleton
@Component(modules = { LocationModule.class, GoogleApiClientModule.class, LocationPendingResultModule.class})
public interface ComponentWeatherActivity {

    void injectWeatherActivity(WeatherActivity activity);

}
