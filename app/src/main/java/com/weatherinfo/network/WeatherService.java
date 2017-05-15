package com.weatherinfo.network;

import com.weatherinfo.model.WeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by user on 23.04.17.
 */

public interface WeatherService {


//    @Deprecated
//    @GET("forecast/daily?")
//    Call<WeatherResponse> getForecast(@Query("lat") double latitude, @Query("lon") double longitude,
//                                      @Query("cnt") int days, @Query("APPID") String apiKey);

    @GET("forecast/daily?")
    Observable<WeatherResponse> getForecast(@Query("lat") double latitude, @Query("lon") double longitude,
                                            @Query("cnt") int days, @Query("APPID") String apiKey);

}
