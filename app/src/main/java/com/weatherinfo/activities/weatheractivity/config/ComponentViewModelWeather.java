package com.weatherinfo.activities.weatheractivity.config;


import com.weatherinfo.utils.rx.ModuleSchedulerProvider;

import dagger.Subcomponent;

@ScopePresenter
@Subcomponent(modules = {ModuleFactoryViewModelWeather.class, ModuleSchedulerProvider.class})
public interface ComponentViewModelWeather {

    FactoryViewModelWeather gFactoryViewModelWeather();

}
