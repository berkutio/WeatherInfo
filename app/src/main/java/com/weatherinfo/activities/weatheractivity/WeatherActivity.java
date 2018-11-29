package com.weatherinfo.activities.weatheractivity;

import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.weatherinfo.App;
import com.weatherinfo.R;
import com.weatherinfo.adapters.WeatherActivityAdapter;
import com.weatherinfo.databinding.ActivityWeatherBinding;
import com.weatherinfo.di.Dag2Components;
import com.weatherinfo.model.DataBindingForecastData;
import com.weatherinfo.model.ForecastData;
import com.weatherinfo.model.PresenterResponse;
import com.weatherinfo.model.WeatherResponse;
import com.weatherinfo.utils.PermissionsUtils;
import com.weatherinfo.utils.rx.ApplicationProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<LocationSettingsResult>, WeatherView {

    private static final int REQUEST_CODE_LOCATION = 1;
    private static final int REQUEST_CODE_PERMISSIONS = 101;

    @Inject
    GoogleApiClient googleApiClient;
    @Inject
    LocationRequest locationRequest;
    @Inject
    LocationSettingsRequest locationSettingsRequest;
    @Inject
    PendingResult<LocationSettingsResult> pendingResult;

    @BindView(R.id.dataLayout)
    LinearLayout dataLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ActivityWeatherBinding weatherBinding;

    private WeatherPresenter weatherPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initiateViews();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        initLocationDetection();

        weatherPresenter = new WeatherActivityPresenter(App.getAppContext(), ApplicationProvider.createProvider(), this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        weatherPresenter.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_LOCATION:
                if (resultCode == RESULT_OK) {
                    initiateLocationUpdates();
                } else {
                    finish();
                }
                break;

            case REQUEST_CODE_PERMISSIONS:
                initLocationDetection();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                launchLocationDetection();
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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, getString(R.string.error_weather_response), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        googleApiClient.disconnect();
        weatherPresenter.onObtainLocation(location);
    }

    @Override
    public void onReceiveWeatherForecast(PresenterResponse<WeatherResponse> response) {
        if (response.getResponse() != null) {
            WeatherResponse weatherResponse = response.getResponse();

            mapDataForToday(weatherResponse);

            ArrayList<ForecastData> list = new ArrayList<>(Arrays.asList(weatherResponse.getList()));
            list.remove(0);

            mapDataForLaterDays(list);

            dataLayout.setVisibility(View.VISIBLE);
        } else if (response.getError() != null) {
            Toast.makeText(this, getString(R.string.error_weather_response), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();

        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {
                    status.startResolutionForResult(WeatherActivity.this, REQUEST_CODE_LOCATION);
                } catch (IntentSender.SendIntentException e) {
                    Log.e("myLogs", e.getMessage());
                }
                break;

            case LocationSettingsStatusCodes.SUCCESS:
                initiateLocationUpdates();
                break;
        }
    }

    private void initiateViews() {
        setContentView(R.layout.activity_weather);
        weatherBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather);
    }

    private void initLocationDetection() {
        if (PermissionsUtils.isPermissionGranted(PermissionsUtils.Permissions.ACCESS_COARSE_LOCATION)
                && PermissionsUtils.isPermissionGranted(PermissionsUtils.Permissions.ACCESS_FINE_LOCATION)) {
            launchLocationDetection();
        } else {
            PermissionsUtils.requestPermission(this, new PermissionsUtils.Permissions[]{PermissionsUtils.Permissions.ACCESS_COARSE_LOCATION,
                    PermissionsUtils.Permissions.ACCESS_FINE_LOCATION}, REQUEST_CODE_PERMISSIONS);
        }
    }

    private void launchLocationDetection() {
        Dag2Components.getComponentWeatherActivity(this, this, this).injectWeatherActivity(this);
        googleApiClient.connect();
    }

    private void initiateLocationUpdates() {
        if (PermissionsUtils.isPermissionGranted(PermissionsUtils.Permissions.ACCESS_COARSE_LOCATION)
                && PermissionsUtils.isPermissionGranted(PermissionsUtils.Permissions.ACCESS_FINE_LOCATION)) {

            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            Toast.makeText(this, getString(R.string.weather_response_obtaining), Toast.LENGTH_LONG).show();
        }
    }

    private void mapDataForToday(WeatherResponse response) {
        toolbar.setTitle(getString(R.string.app_name) + " in " + response.getCity().getName());
        ForecastData forecastData = response.getList()[0];
        weatherBinding.setForecast(new DataBindingForecastData(forecastData));
    }

    private void mapDataForLaterDays(List<ForecastData> list) {
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        WeatherActivityAdapter weatherActivityAdapter = new WeatherActivityAdapter(list);
        recyclerView.setAdapter(weatherActivityAdapter);
    }


}
