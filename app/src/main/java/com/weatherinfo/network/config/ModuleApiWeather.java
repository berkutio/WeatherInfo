package com.weatherinfo.network.config;

import com.weatherinfo.network.ApiWeather;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ModuleApiWeather {


    @Provides
    @ScopeNetwork
    public ApiWeather getApiWeather(Retrofit retrofit) {
        return retrofit.create(ApiWeather.class);
    }


}
