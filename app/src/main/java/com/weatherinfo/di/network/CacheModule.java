package com.weatherinfo.di.network;

import android.content.Context;
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

    private Context context;

    public CacheModule(Context context) {
        this.context = context;
    }

    @Provides
    @ScopeNetwork
    public Cache getCache(){
        File cacheFile = new File(context.getCacheDir(), "http");
        return new Cache(cacheFile, CACHE_FILE_SIZE);
    }

}
