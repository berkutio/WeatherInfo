package com.weatherinfo.location;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.weatherinfo.appconfig.ModuleApplication;

import dagger.Module;
import dagger.Provides;


@Module
public class ModuleGoogleApiClient {

    private GoogleApiClient.ConnectionCallbacks connectionCallback;
    private GoogleApiClient.OnConnectionFailedListener connectionFailedListener;

    public ModuleGoogleApiClient(GoogleApiClient.ConnectionCallbacks connectionCallback, GoogleApiClient.OnConnectionFailedListener connectionFailedListener) {
        this.connectionCallback = connectionCallback;
        this.connectionFailedListener = connectionFailedListener;
    }

    @Provides
    @ScopeLocation
    public GoogleApiClient getGoogleApiClient(Application application) {
        return new GoogleApiClient.Builder(application)
                .addConnectionCallbacks(connectionCallback)
                .addOnConnectionFailedListener(connectionFailedListener)
                .addApi(LocationServices.API)
                .build();
    }


}
