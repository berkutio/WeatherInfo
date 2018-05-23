package com.weatherinfo.utils.rx;


import com.weatherinfo.activities.weatheractivity.config.ScopePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleSchedulerProvider {


    @Provides
    @ScopePresenter
    public SchedulerProvider getSchedulerProvider(){
        return new ApplicationProvider();
    }


}
