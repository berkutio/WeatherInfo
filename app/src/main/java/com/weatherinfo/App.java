package com.weatherinfo;

import android.app.Application;

/**
 * Created by user on 26.04.17.
 */

public class App extends Application {

    private static App instance;

    public App(){
        instance = this;
    }

    public static App getAppContext(){
        return instance;
    }

}
