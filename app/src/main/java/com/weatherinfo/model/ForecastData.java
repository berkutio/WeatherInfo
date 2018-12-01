package com.weatherinfo.model;

import java.util.Arrays;

public class ForecastData {

    private long data;
    private Temperature temperature;
    private double pressure;
    private long humidity;
    private WeatherDescription weatherDescription[];
    private double speed;
    private long degree;
    private long clouds;
    private double rain;

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public long getHumidity() {
        return humidity;
    }

    public void setHumidity(long humidity) {
        this.humidity = humidity;
    }

    public WeatherDescription[] getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(WeatherDescription[] weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public long getDegree() {
        return degree;
    }

    public void setDegree(long degree) {
        this.degree = degree;
    }

    public long getClouds() {
        return clouds;
    }

    public void setClouds(long clouds) {
        this.clouds = clouds;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    @Override
    public String toString() {
        return "ForecastData{" +
                "data=" + data +
                ", temperature=" + temperature +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", weatherDescription=" + Arrays.toString(weatherDescription) +
                ", speed=" + speed +
                ", degree=" + degree +
                ", clouds=" + clouds +
                ", rain=" + rain +
                '}';
    }

}
