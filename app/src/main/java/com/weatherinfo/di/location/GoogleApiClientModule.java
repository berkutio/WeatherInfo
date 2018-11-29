package com.weatherinfo.di.location;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import dagger.Module;
import dagger.Provides;


@Module(includes = ContextModule.class)
public class GoogleApiClientModule {

    private final GoogleApiClient.ConnectionCallbacks connectionCallback;
    private final GoogleApiClient.OnConnectionFailedListener connectionFailedListener;

    public GoogleApiClientModule(GoogleApiClient.ConnectionCallbacks connectionCallback, GoogleApiClient.OnConnectionFailedListener connectionFailedListener) {
        this.connectionCallback = connectionCallback;
        this.connectionFailedListener = connectionFailedListener;
    }

    @Provides
    @LocationScope
    public GoogleApiClient getGoogleApiClient(Context context){
        return new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(connectionCallback)
                .addOnConnectionFailedListener(connectionFailedListener)
                .addApi(LocationServices.API)
                .build();
    }


}
