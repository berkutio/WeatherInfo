package com.weatherinfo.activities.weatheractivity.config;


import android.app.Application;

import com.weatherinfo.network.ServiceWeather;
import com.weatherinfo.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleFactoryViewModelWeather {


    @Provides
    @ScopePresenter
    public FactoryViewModelWeather getFactoryViewModelWeather(SchedulerProvider schedulerProvider, ServiceWeather serviceWeather) {
        return FactoryViewModelWeather.getInstance(schedulerProvider, serviceWeather);
    }


}
