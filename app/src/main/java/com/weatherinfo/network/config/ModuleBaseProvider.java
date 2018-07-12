package com.weatherinfo.network.config;

import android.content.res.Resources;

import com.weatherinfo.network.BaseProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleBaseProvider {

    @Provides
    @ScopeNetwork
    public BaseProvider getBaseProvider(Resources resources) {
        return new BaseProvider(resources);
    }

}
