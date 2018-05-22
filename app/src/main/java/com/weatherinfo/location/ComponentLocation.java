package com.weatherinfo.location;

import com.weatherinfo.activities.weatheractivity.WeatherActivity;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by user on 06.05.17.
 */
@ScopeLocation
@Subcomponent(modules = {ModuleLocation.class, ModuleGoogleApiClient.class, ModuleLocationPendingResult.class})
public interface ComponentLocation {

    void injectWeatherActivity(WeatherActivity activity);

}
