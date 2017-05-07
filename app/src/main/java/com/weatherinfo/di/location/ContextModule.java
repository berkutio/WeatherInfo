package com.weatherinfo.di.location;

import android.content.Context;

import com.weatherinfo.App;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 06.05.17.
 */
@Module
public class ContextModule {

    private final Context context;

    public ContextModule(){
        this.context = App.getAppContext();
    }

    @Provides
    @Singleton
    public Context getContext(){
        return context;
    }

}
