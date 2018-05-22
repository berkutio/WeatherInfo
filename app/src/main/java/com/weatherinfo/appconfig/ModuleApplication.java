package com.weatherinfo.appconfig;

import android.app.Application;
import android.content.res.Resources;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleApplication {

    private final Application application;

    public ModuleApplication(Application application) {
        this.application = application;
    }

    @Provides
    @ScopeApplication
    Application provideApplication() {
        return application;
    }

    @Provides
    @ScopeApplication
    Resources provideResources() {
        return application.getResources();
    }

}
