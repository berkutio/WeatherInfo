package com.weatherinfo;

import android.app.Application;

import com.weatherinfo.appconfig.ComponentApplication;
import com.weatherinfo.appconfig.DaggerComponentApplication;
import com.weatherinfo.appconfig.ModuleApplication;


public class App extends Application {

    private ComponentApplication componentApp;

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initComponentApp();
    }

    private void initComponentApp(){
        componentApp = DaggerComponentApplication.builder().moduleApplication(new ModuleApplication(this)).build();
    }

    public ComponentApplication getComponentApp() {
        return componentApp;
    }

    public static Application getApp() {
        return instance;
    }

}
