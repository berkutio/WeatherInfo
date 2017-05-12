package com.weatherinfo.di.network;


import com.weatherinfo.network.WeatherServiceImpl;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by user on 09.05.17.
 */
@ScopeNetwork
@Component(modules = {NetworkModule.class, CacheModule.class})
public interface ComponentNetwork {

    void injectService(WeatherServiceImpl service);

}
