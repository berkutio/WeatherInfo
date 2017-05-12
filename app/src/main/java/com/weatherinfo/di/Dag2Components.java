package com.weatherinfo.di;


import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationSettingsResult;
import com.weatherinfo.di.location.ComponentWeatherActivity;
import com.weatherinfo.di.location.ContextModule;
import com.weatherinfo.di.location.DaggerComponentWeatherActivity;
import com.weatherinfo.di.location.GoogleApiClientModule;
import com.weatherinfo.di.location.LocationModule;
import com.weatherinfo.di.location.LocationPendingResultModule;
import com.weatherinfo.di.network.CacheModule;
import com.weatherinfo.di.network.ComponentNetwork;
import com.weatherinfo.di.network.DaggerComponentNetwork;
import com.weatherinfo.di.network.NetworkModule;

/**
 * Incapsulates dagger components
 */
public class Dag2Components {


    public static ComponentWeatherActivity getComponentWeatherActivity(
            ResultCallback<LocationSettingsResult> resultCallback,
            GoogleApiClient.ConnectionCallbacks connectionCallback,
            GoogleApiClient.OnConnectionFailedListener connectionFailedListener){

        return DaggerComponentWeatherActivity.builder()
                .googleApiClientModule(new GoogleApiClientModule(connectionCallback, connectionFailedListener))
                .contextModule(new ContextModule())
                .locationModule(new LocationModule())
                .locationPendingResultModule(new LocationPendingResultModule(resultCallback)).build();
    }

    public static ComponentNetwork getComponentNetwork(Context context, String baseUrl){
        return DaggerComponentNetwork.builder()
                .networkModule(new NetworkModule(baseUrl))
                .cacheModule(new CacheModule(context)).build();
    }


}
