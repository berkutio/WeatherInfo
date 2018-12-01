package com.weatherinfo.model;

import java.util.Arrays;

/**
 * Created by user on 23.04.17.
 */

public class WeatherResponse {

    private City city;
    private String cod;
    private double message;
    private long cnt;
    private ForecastData[] forecastData;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public long getCnt() {
        return cnt;
    }

    public void setCnt(long cnt) {
        this.cnt = cnt;
    }

    public ForecastData[] getForecastData() {
        return forecastData;
    }

    public void setForecastData(ForecastData[] forecastData) {
        this.forecastData = forecastData;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "city=" + city +
                ", cod='" + cod + '\'' +
                ", message=" + message +
                ", cnt=" + cnt +
                ", forecastData=" + Arrays.toString(forecastData) +
                '}';
    }
}
