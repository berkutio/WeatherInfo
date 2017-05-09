package com.weatherinfo.di.network;

import com.weatherinfo.App;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;

/**
 * Created by user on 09.05.17.
 */
@Module
public class CacheModule {

    private static final int CACHE_FILE_SIZE = 2*1024*1024;

    @Provides
    @Singleton
    public Cache getCache(){
        File cacheFile = new File(App.getAppContext().getCacheDir(), "http");
        return new Cache(cacheFile, CACHE_FILE_SIZE);
    }

}
