package com.weatherinfo.activities.weatheractivity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.weatherinfo.App;
import com.weatherinfo.appconfig.ComponentApplication;

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectObjects();
    }

    protected abstract void injectObjects();

    protected ComponentApplication getComponentApplication() {
        return ((App) getApplication()).getComponentApp();
    }

}
