package com.weatherinfo.network.config;

import com.weatherinfo.activities.weatheractivity.config.ComponentViewModelWeather;

import dagger.Subcomponent;

@ScopeNetwork
@Subcomponent(modules = {ModuleNetwork.class, ModuleCache.class, ModuleBaseProvider.class, ModuleApiWeather.class})
public interface ComponentNetwork {

    ComponentViewModelWeather getComponentViewModelWeather();

}
