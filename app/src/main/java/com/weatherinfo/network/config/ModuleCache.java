package com.weatherinfo.network.config;

import android.app.Application;
import android.content.Context;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;

@Module
public class ModuleCache {

    private static final int CACHE_FILE_SIZE = 2*1024*1024;


    @Provides
    @ScopeNetwork
    public Cache getCache(Application application){
        File cacheFile = new File(application.getCacheDir(), "http");
        return new Cache(cacheFile, CACHE_FILE_SIZE);
    }

}
