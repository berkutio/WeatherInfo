package com.weatherinfo.activities.weatheractivity;

import com.weatherinfo.model.WeatherResponse;

/**
 * Created by user on 23.04.17.
 */

public interface IViewWeather {


    void onReceiveWeatherForecast(WeatherResponse response);


}
