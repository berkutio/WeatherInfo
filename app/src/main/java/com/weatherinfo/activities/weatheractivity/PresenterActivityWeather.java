package com.weatherinfo.activities.weatheractivity;

import android.content.Context;
import android.location.Location;
import android.support.annotation.VisibleForTesting;
import com.weatherinfo.App;
import com.weatherinfo.model.WeatherResponse;
import com.weatherinfo.network.WeatherServiceImpl;
import com.weatherinfo.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 23.04.17.
 */

public class PresenterActivityWeather implements IPresenterWeather {

    private Context applicationContext;

    private IViewWeather activity;

    public PresenterActivityWeather(Context applicationContext, IViewWeather activity) {
        this.applicationContext = applicationContext;
        this.activity = activity;
    }

    @Override
    public void onDestroy(){
        activity = null;
    }

    @Override
    public void onObtainLocation(Location location) {
        WeatherServiceImpl service = new WeatherServiceImpl(applicationContext, Constants.WEATHER_URI, Constants.WEATHER_API_KEY);
        service.getListData(location, 6).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if(activity != null && response.isSuccessful()){
                    activity.onReceiveWeatherForecast(response.body());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                activity.onReceiveWeatherForecast(null);
            }
        });

    }


    @VisibleForTesting
    IViewWeather getMainView(){
        return activity;
    }

}
