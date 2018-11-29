package com.weatherinfo.di.network;

import android.content.Context;
import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;

/**
 * Created by user on 09.05.17.
 */
@Module
public class CacheModule {

    private static final String CHILD_FOLDER = "http";
    private static final int CACHE_FILE_SIZE = 2*1024*1024;

    private final Context context;

    public CacheModule(Context context) {
        this.context = context;
    }

    @Provides
    @NetworkScope
    public Cache getCache(){
        File cacheFile = new File(context.getCacheDir(), CHILD_FOLDER);
        return new Cache(cacheFile, CACHE_FILE_SIZE);
    }

}
