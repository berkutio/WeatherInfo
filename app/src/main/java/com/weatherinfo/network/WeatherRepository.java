package com.weatherinfo.network;

import android.content.Context;
import android.location.Location;

import com.weatherinfo.di.Dag2Components;
import com.weatherinfo.model.WeatherResponse;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;

/**
 * Created by user on 23.04.17.
 */

public class WeatherRepository {

    private String APIkey;

    @Inject
    Retrofit retrofit;

    public WeatherRepository(Context context, String baseUrl, String APIkey) {
        this.APIkey = APIkey;
        Dag2Components.getComponentNetwork(context, baseUrl).injectService(this);
    }

    public Single<WeatherResponse> getForecast(Location location, int days){
        WeatherApi service = retrofit.create(WeatherApi.class);
        return service.getForecast(location.getLatitude(), location.getLongitude(), days, APIkey);
    }


}
