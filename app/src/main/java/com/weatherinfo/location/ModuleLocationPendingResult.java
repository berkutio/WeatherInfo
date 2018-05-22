package com.weatherinfo.location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 07.05.17.
 */

@Module
public class ModuleLocationPendingResult {

    ResultCallback<LocationSettingsResult> resultCallback;

    public ModuleLocationPendingResult(ResultCallback<LocationSettingsResult> resultCallback) {
        this.resultCallback = resultCallback;
    }

    @Provides
    @ScopeLocation
    public PendingResult<LocationSettingsResult> getPendingResult(GoogleApiClient googleApiClient, LocationSettingsRequest settingsRequest){
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        googleApiClient,
                        settingsRequest
                );
        result.setResultCallback(resultCallback);
        return result;
    }

}
