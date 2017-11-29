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

public class WeatherServiceImpl {

    private String APIkey;

    @Inject
    Retrofit retrofit;

    public WeatherServiceImpl(Context context, String baseUrl, String APIkey) {
        this.APIkey = APIkey;
        Dag2Components.getComponentNetwork(context, baseUrl).injectService(this);
    }

    public Single<WeatherResponse> getListData(Location location, int days){
        WeatherService service = retrofit.create(WeatherService.class);
        return service.getForecast(location.getLatitude(), location.getLongitude(), days, APIkey);
    }


}
