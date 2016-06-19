package com.weatherinfo;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.weatherinfo.adapters.Adapter;
import com.weatherinfo.asynctasks.WeatherDataTask;
import com.weatherinfo.entityes.ForecastData;
import com.weatherinfo.sup.Universal;
import java.util.List;

public class Weather extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<LocationSettingsResult>, Constants {

    private LinearLayout progressBarLayout;
    private LinearLayout dataLayout;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private RecyclerView mRecyclerView;

    private TextView locality;
    private TextView data;
    private TextView general;
    private TextView temperature;
    private TextView pressure;
    private TextView humidity;
    private TextView windSpeed;

    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBarLayout = (LinearLayout) findViewById(R.id.progressBarLayout);
        dataLayout = (LinearLayout) findViewById(R.id.dataLayout);
        locationInit();
        textesInit();
        setmRecyclerInit();
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
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mGoogleApiClient.disconnect();
        String url2 = Universal.getMoreDaysRequest(WEATHER_URI_FIVE_DAYS, location, 6);
        WeatherDataTask task = new WeatherDataTask();
        task.setOnCompleteListener(new WeatherDataTask.OnCompleteListener() {
            @Override
            public void onComplete(List<ForecastData> result) {
                ForecastData data = result.remove(0);
                setDataForToday(data);
                settingList(result);
                switchViews();
            }
        });
        task.execute(url2);
    }

    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {
                    status.startResolutionForResult(
                            Weather.this, 1);
                } catch (IntentSender.SendIntentException e) {
                }
                break;
            case LocationSettingsStatusCodes.SUCCESS:
                startLocationUpdates();
                break;
        }
    }

    private void locationInit(){
        initGoogleApiClientApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
        checkLocationSettings();
    }

    private synchronized void initGoogleApiClientApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        mLocationSettingsRequest = builder.build();
    }

    private void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(2000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    private void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                mLocationRequest,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
            }
        });
    }

    private void textesInit(){
        locality = (TextView) findViewById(R.id.locality);
        data = (TextView) findViewById(R.id.data_today);
        general = (TextView) findViewById(R.id.general_description_today);
        temperature = (TextView) findViewById(R.id.temperature_val_today);
        pressure = (TextView) findViewById(R.id.pressure_val_today);
        humidity = (TextView) findViewById(R.id.humidity_val_today);
        windSpeed = (TextView) findViewById(R.id.wind_speed_val_today);
    }

    private void setmRecyclerInit(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void settingList(List<ForecastData> list){
        mAdapter = new Adapter(list);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setDataForToday(ForecastData forecastData){
        locality.setText(forecastData.getLocality());
        data.setText(forecastData.getData());
        general.setText(forecastData.getGeneralDescription());
        temperature.setText(String.valueOf(forecastData.getTemperature()));
        pressure.setText(String.valueOf(forecastData.getPressure()));
        humidity.setText(String.valueOf(forecastData.getHumidity()));
        windSpeed.setText(String.valueOf(forecastData.getWindSpeed()));
    }

    private void switchViews(){
        progressBarLayout.setVisibility(View.GONE);
        dataLayout.setVisibility(View.VISIBLE);
    }
}
