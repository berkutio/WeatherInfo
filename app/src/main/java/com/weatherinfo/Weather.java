package com.weatherinfo;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import com.weatherinfo.entityes.ForecastData;
import com.weatherinfo.sup.GetResponseClass;
import com.weatherinfo.sup.JsonParsers;
import com.weatherinfo.sup.Universal;

import java.util.ArrayList;

public class Weather extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<LocationSettingsResult>, Constants {

    // {"_id":700569,"name":"Mykolayiv","country":"UA","coord":{"lon":31.9974,"lat":46.965912}}
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        locationInit();
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
    public void onLocationChanged(Location location) {
        Log.d("myLogs", "onLocationChanged lat" + location.getLatitude());
        Log.d("myLogs", "onLocationChanged log" + location.getLongitude());
        mGoogleApiClient.disconnect();
        String url2 = Universal.getMoreDaysRequest(WEATHER_URI_FIVE_DAYS, location, 6);
        Log.d("myLogs", "onLocationChanged url2 " + url2);
        new WeatherDataTask().execute(url2);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

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

    static class WeatherDataTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String res2 = GetResponseClass.getResult(params[0]);
            Log.d("myLogs", "doInBackground res " + res2);
            ArrayList<ForecastData> d = JsonParsers.getFiveDayForecastList(res2);
            Log.d("myLogs", "d.size() " + d.size());
            for(int i = 0; i < d.size(); i++){
                ForecastData a = d.get(i);
                Log.d("myLogs", "______________________");
                Log.d("myLogs", "             a.getData() " + a.getData());
                Log.d("myLogs", "         a.getLocality() " + a.getLocality());
                Log.d("myLogs", "   a.getGeneralWeather() " + a.getGeneralWeather());
                Log.d("myLogs", "a.getGeneralDescription()" + a.getGeneralDescription());
                Log.d("myLogs", "      a.getTemperature() " + a.getTemperature());
                Log.d("myLogs", "         a.getPressure() " + a.getPressure());
                Log.d("myLogs", "         a.getHumidity() " + a.getHumidity());
                Log.d("myLogs", "        a.getWindSpeed() " + a.getWindSpeed());
            }
            return res2;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}
