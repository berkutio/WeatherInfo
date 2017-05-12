package com.weatherinfo.activities.weatheractivity;

import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.weatherinfo.adapters.WeatherAdapter;
import com.weatherinfo.di.Dag2Components;
import com.weatherinfo.model.ForecastData;
import com.weatherinfo.model.WeatherResponse;
import com.weatherinfo.utils.PermissionsUtils;
import com.weatherinfo.utils.Universal;

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
        ResultCallback<LocationSettingsResult>, IViewWeather {

    public static final int REQUEST_CODE_PERMISSIONS = 101;

    @BindView(R.id.dataLayout)
    LinearLayout dataLayout;

    @Inject
    GoogleApiClient mGoogleApiClient;
    @Inject
    LocationRequest mLocationRequest;
    @Inject
    LocationSettingsRequest mLocationSettingsRequest;
    @Inject
    PendingResult<LocationSettingsResult> mResult;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.im_description_icon)
    ImageView mImViewDescrImage;
    @BindView(R.id.data_today)
    TextView data;
    @BindView(R.id.general_description_today)
    TextView general;
    @BindView(R.id.temperature_val_today)
    TextView temperature;
    @BindView(R.id.pressure_val_today)
    TextView pressure;
    @BindView(R.id.humidity_val_today)
    TextView humidity;
    @BindView(R.id.wind_speed_val_today)
    TextView windSpeed;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private WeatherAdapter mWeatherAdapter;

    private IPresenterWeather presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        checkPermissions();
        presenter = new PresenterActivityWeather(App.getAppContext(), this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
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
                checkPermissions();
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
        presenter.onObtainLocation(location);
    }

    @Override
    public void onReceiveWeatherForecast(WeatherResponse response) {
        if (response != null) {
            mapDataForToday(response);
            ArrayList<ForecastData> list = new ArrayList<>();
            list.addAll(Arrays.asList(response.getList()));
            list.remove(0);
            settingList(list);
            dataLayout.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, getString(R.string.error_weather_response), Toast.LENGTH_LONG).show();
        }
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

    private void checkPermissions() {
        if (PermissionsUtils.isPermissionGranted(PermissionsUtils.Permissions.ACCESS_COARSE_LOCATION)
                && PermissionsUtils.isPermissionGranted(PermissionsUtils.Permissions.ACCESS_FINE_LOCATION)) {
            locationInit();
        } else {
            PermissionsUtils.requestPermission(this, new PermissionsUtils.Permissions[]{PermissionsUtils.Permissions.ACCESS_COARSE_LOCATION,
                    PermissionsUtils.Permissions.ACCESS_FINE_LOCATION}, REQUEST_CODE_PERMISSIONS);
        }
    }

    private void locationInit() {
        Dag2Components.getComponentWeatherActivity(this, this, this).injectWeatherActivity(this);
        mGoogleApiClient.connect();
    }

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

    private void settingList(List<ForecastData> list){
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mWeatherAdapter = new WeatherAdapter(list);
        mRecyclerView.setAdapter(mWeatherAdapter);
    }

    private void mapDataForToday(WeatherResponse response){
        toolbar.setTitle(getString(R.string.app_name) + " in " + response.getCity().getName());
        ForecastData forecastData = response.getList()[0];
        mImViewDescrImage.setImageResource(Universal.getWeatherResource(forecastData.getWeather()[0].getDescription()));
        data.setText(Universal.formatData(forecastData.getDt()));
        general.setText(forecastData.getWeather()[0].getDescription());
        temperature.setText(Universal.convertToCelicies(forecastData.getTemp().getMin()));
        pressure.setText(String.valueOf(forecastData.getPressure()));
        humidity.setText(String.valueOf(forecastData.getHumidity()));
        windSpeed.setText(String.valueOf(forecastData.getSpeed()));
    }


}
