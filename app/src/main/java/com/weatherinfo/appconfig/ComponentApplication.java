package com.weatherinfo.appconfig;


import dagger.Component;
import com.weatherinfo.location.ComponentLocation;
import com.weatherinfo.location.ModuleGoogleApiClient;
import com.weatherinfo.location.ModuleLocationPendingResult;
import com.weatherinfo.network.config.ComponentNetwork;

@ScopeApplication
@Component(modules = {ModuleApplication.class})
public interface ComponentApplication {

    ComponentLocation getComponentLocation(ModuleGoogleApiClient moduleGoogleApiClient, ModuleLocationPendingResult moduleLocationPendingResult);

    ComponentNetwork getComponentNetwork();

}
