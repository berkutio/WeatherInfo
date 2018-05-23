package com.weatherinfo.activities.weatheractivity.config;


import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.weatherinfo.activities.weatheractivity.viewmodel.ViewModelWeather;
import com.weatherinfo.network.ServiceWeather;
import com.weatherinfo.utils.rx.SchedulerProvider;

public class FactoryViewModelWeather implements ViewModelProvider.Factory {

    private static FactoryViewModelWeather INSTANCE;

    private SchedulerProvider mSchedulerProvider;
    private ServiceWeather mServiceWeather;
    private Application mApplication;

    private FactoryViewModelWeather(SchedulerProvider schedulerProvider, ServiceWeather serviceWeather, Application application) {
        this.mSchedulerProvider = schedulerProvider;
        this.mServiceWeather = serviceWeather;
        this.mApplication = application;
    }


    public static FactoryViewModelWeather getInstance(Application application, SchedulerProvider provider, ServiceWeather serviceWeather) {

        if (INSTANCE == null) {
            synchronized (FactoryViewModelWeather.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FactoryViewModelWeather(provider, serviceWeather, application);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ViewModelWeather.class)) {
            return (T) new ViewModelWeather(mSchedulerProvider, mServiceWeather, mApplication);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
