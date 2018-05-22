package com.weatherinfo.di;


import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationSettingsResult;
import com.weatherinfo.di.network.CacheModule;
import com.weatherinfo.di.network.ComponentNetwork;
import com.weatherinfo.di.network.DaggerComponentNetwork;
import com.weatherinfo.di.network.NetworkModule;
import com.weatherinfo.location.ComponentLocation;
import com.weatherinfo.location.ModuleGoogleApiClient;
import com.weatherinfo.location.ModuleLocation;
import com.weatherinfo.location.ModuleLocationPendingResult;

/**
 * Incapsulates dagger components
 */
public class Dag2Components {


    public static ComponentLocation getComponentWeatherActivity(
            ResultCallback<LocationSettingsResult> resultCallback,
            GoogleApiClient.ConnectionCallbacks connectionCallback,
            GoogleApiClient.OnConnectionFailedListener connectionFailedListener){
            return null;
//        return DaggerComponentWeatherActivity.builder()
//                .googleApiClientModule(new ModuleGoogleApiClient(connectionCallback, connectionFailedListener))
//                .contextModule(new ContextModule())
//                .locationModule(new ModuleLocation())
//                .locationPendingResultModule(new ModuleLocationPendingResult(resultCallback)).build();
    }

    public static ComponentNetwork getComponentNetwork(Context context, String baseUrl){
        return DaggerComponentNetwork.builder()
                .networkModule(new NetworkModule(baseUrl))
                .cacheModule(new CacheModule(context)).build();
    }


}
