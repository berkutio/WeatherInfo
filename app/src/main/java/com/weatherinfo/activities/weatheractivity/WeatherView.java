package com.weatherinfo.activities.weatheractivity;

import com.weatherinfo.model.PresenterResponse;
import com.weatherinfo.model.WeatherResponse;

/**
 * Created by user on 23.04.17.
 */

public interface WeatherView {


    void onReceiveWeatherForecast(PresenterResponse<WeatherResponse> response);


}
