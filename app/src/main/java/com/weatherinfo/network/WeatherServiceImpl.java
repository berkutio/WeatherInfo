package com.weatherinfo.network;

import android.content.Context;
import android.location.Location;
import com.weatherinfo.model.WeatherResponse;
import retrofit2.Call;

/**
 * Created by user on 23.04.17.
 */

public class WeatherServiceImpl {

    private Context context;
    private String baseUrl;
    private String APIkey;

    public WeatherServiceImpl(Context context, String baseUrl, String APIkey) {
        this.context = context;
        this.baseUrl = baseUrl;
        this.APIkey = APIkey;
    }


    public Call<WeatherResponse> getListData(Location location, int days){
        WeatherService service = ServiceConnectionFacory.getRetrofitInstance(context, baseUrl).create(WeatherService.class);
        return service.getForecast(location.getLatitude(), location.getLongitude(), days, APIkey);
    }


}
