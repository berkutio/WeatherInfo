package com.weatherinfo.di.location;


import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationModule {

    private static final int LOCATION_INTERVAL = 2000;
    private static final int LOCATION_FASTEST_INTERVAL = 1000;

    @Provides
    @LocationScope
    public LocationRequest getLocationRequest(){
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(LOCATION_INTERVAL);
        locationRequest.setFastestInterval(LOCATION_FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        return locationRequest;
    }

    @Provides
    @LocationScope
    public LocationSettingsRequest getLocationSettingsRequest(LocationRequest locationRequest){
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        return builder.build();
    }


}
