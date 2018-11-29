package com.weatherinfo.di.network;


import com.weatherinfo.network.WeatherServiceImpl;

import dagger.Component;

/**
 * Created by user on 09.05.17.
 */
@NetworkScope
@Component(modules = {NetworkModule.class, CacheModule.class})
public interface NetworkComponent {

    void injectService(WeatherServiceImpl service);

}
