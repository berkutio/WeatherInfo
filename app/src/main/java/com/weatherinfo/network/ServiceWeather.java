package com.weatherinfo.network;

import android.location.Location;

import com.weatherinfo.model.ForecastData;
import com.weatherinfo.model.WeatherResponse;

import javax.inject.Inject;

import io.reactivex.Single;

public class ServiceWeather {

    private ApiWeather apiWeather;
    private BaseProvider baseProvider;

    @Inject
    public ServiceWeather(BaseProvider baseProvider, ApiWeather apiWeather) {
        this.baseProvider = baseProvider;
        this.apiWeather = apiWeather;
    }

    public Single<WeatherResponse> getListData(Location location, int days) {
        return apiWeather.getForecast(location.getLatitude(), location.getLongitude(), days, baseProvider.getApiKey())
                .map(weatherResponse -> {
                    ForecastData firstDay = weatherResponse.getList().remove(0);
                    weatherResponse.setFirstDay(firstDay);
                    return weatherResponse;
                });
    }


}
