package com.weatherinfo.di;


import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationSettingsResult;
import com.weatherinfo.di.location.DaggerWeatherActivityComponent;
import com.weatherinfo.di.location.WeatherActivityComponent;
import com.weatherinfo.di.location.ContextModule;
import com.weatherinfo.di.location.GoogleApiClientModule;
import com.weatherinfo.di.location.LocationModule;
import com.weatherinfo.di.location.LocationPendingResultModule;
import com.weatherinfo.di.network.CacheModule;
import com.weatherinfo.di.network.DaggerNetworkComponent;
import com.weatherinfo.di.network.NetworkComponent;
import com.weatherinfo.di.network.NetworkModule;

/**
 * Incapsulates dagger components
 */
public class Dag2Components {


    public static WeatherActivityComponent getComponentWeatherActivity(
            ResultCallback<LocationSettingsResult> resultCallback,
            GoogleApiClient.ConnectionCallbacks connectionCallback,
            GoogleApiClient.OnConnectionFailedListener connectionFailedListener){

        return DaggerWeatherActivityComponent.builder()
                .googleApiClientModule(new GoogleApiClientModule(connectionCallback, connectionFailedListener))
                .contextModule(new ContextModule())
                .locationModule(new LocationModule())
                .locationPendingResultModule(new LocationPendingResultModule(resultCallback)).build();
    }

    public static NetworkComponent getComponentNetwork(Context context, String baseUrl){
        return DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule(baseUrl))
                .cacheModule(new CacheModule(context)).build();
    }


}
