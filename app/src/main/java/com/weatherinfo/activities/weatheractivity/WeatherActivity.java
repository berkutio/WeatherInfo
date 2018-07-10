package com.weatherinfo.activities.weatheractivity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.weatherinfo.R;
import com.weatherinfo.activities.weatheractivity.config.FactoryViewModelWeather;
import com.weatherinfo.activities.weatheractivity.viewmodel.ViewModelWeather;
import com.weatherinfo.adapters.WeatherAdapter;
import com.weatherinfo.databinding.ActivityMvvmweatherBinding;
import com.weatherinfo.location.ModuleGoogleApiClient;
import com.weatherinfo.location.ModuleLocationPendingResult;
import com.weatherinfo.model.BaseLiveData;
import com.weatherinfo.model.LiveDataResponse;
import com.weatherinfo.model.WeatherResponse;
import com.weatherinfo.utils.PermissionsUtils;

import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

public class WeatherActivity extends BaseActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<LocationSettingsResult>, Observer {

    public static final int REQUEST_CODE_PERMISSIONS = 101;

    @Inject
    GoogleApiClient mGoogleApiClient;
    @Inject
    LocationRequest mLocationRequest;
    @Inject
    LocationSettingsRequest mLocationSettingsRequest;
    @Inject
    PendingResult<LocationSettingsResult> mResult;


    private FactoryViewModelWeather factoryViewModelWeather;

    private ActivityMvvmweatherBinding weatherBinding;
    private ViewModelWeather viewModelWeather;
    private LinearLayout mLLdataLayout;
    private WeatherAdapter mWeatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelWeather = ViewModelProviders.of(this, factoryViewModelWeather).get(ViewModelWeather.class);
        weatherBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvmweather);
        initViews();
        bindViews();
        if (viewModelWeather.getLocation() == null) {
            launchGps();
        }
    }

    private void initViews() {
        mLLdataLayout = weatherBinding.getRoot().findViewById(R.id.dataLayout);
        mWeatherAdapter = new WeatherAdapter();
        RecyclerView recyclerView = weatherBinding.getRoot().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mWeatherAdapter);
    }

    private void bindViews() {
        viewModelWeather.getmWeatherResponseData().observe(this, new BaseLiveData.OnEventListener<WeatherResponse>() {
            @Override
            public void onResult(LiveDataResponse<WeatherResponse> response) {
                if (response.getResponse() != null) {
                    mWeatherAdapter.updateAdapter(response.getResponse().getList());
                    mLLdataLayout.setVisibility(View.VISIBLE);
                    weatherBinding.setResponse(response.getResponse());
                }
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(WeatherActivity.this, errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void injectObjects() {
        factoryViewModelWeather = getComponentApplication().getComponentNetwork().getComponentViewModelWeather().gFactoryViewModelWeather();
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    startLocationUpdates();
                } else {
                    finish();
                }
                break;
            case REQUEST_CODE_PERMISSIONS:
                launchGps();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                locationInit();
            } else {
                PermissionsUtils.showPermissionDialogForResult(this, getString(R.string.requires_phone_permission), REQUEST_CODE_PERMISSIONS);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("myLogs", "onConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("myLogs", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("myLogs", "onConnectionFailed " + connectionResult.toString());
        Log.d("myLogs", "onConnectionFailed " + connectionResult.getErrorMessage());
        Log.d("myLogs", "onConnectionFailed " + connectionResult.getErrorCode());
        Toast.makeText(this, getString(R.string.error_weather_response), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        mGoogleApiClient.disconnect();
        viewModelWeather.onObtainLocation(location);
        Toast.makeText(this, location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {
                    status.startResolutionForResult(
                            WeatherActivity.this, 1);
                } catch (IntentSender.SendIntentException e) {
                }
                break;
            case LocationSettingsStatusCodes.SUCCESS:
                startLocationUpdates();
                break;
        }
    }

    private void launchGps() {
        if (PermissionsUtils.isPermissionGranted(PermissionsUtils.Permissions.ACCESS_COARSE_LOCATION)
                && PermissionsUtils.isPermissionGranted(PermissionsUtils.Permissions.ACCESS_FINE_LOCATION)) {
            locationInit();
        } else {
            PermissionsUtils.requestPermission(this, new PermissionsUtils.Permissions[]{PermissionsUtils.Permissions.ACCESS_COARSE_LOCATION,
                    PermissionsUtils.Permissions.ACCESS_FINE_LOCATION}, REQUEST_CODE_PERMISSIONS);
        }
    }

    private void locationInit() {
        getComponentApplication()
                .getComponentLocation(new ModuleGoogleApiClient(this, this), new ModuleLocationPendingResult(this))
                .injectWeatherActivity(this);
        mGoogleApiClient.connect();
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        if (PermissionsUtils.isPermissionGranted(PermissionsUtils.Permissions.ACCESS_COARSE_LOCATION)
                && PermissionsUtils.isPermissionGranted(PermissionsUtils.Permissions.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, getString(R.string.weather_response_obtaining), Toast.LENGTH_LONG).show();
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient,
                    mLocationRequest,
                    this
            );
        }
    }


}
