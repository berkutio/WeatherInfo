package com.weatherinfo.model;

public class DataBindingForecastData {

    private ForecastData forecastData;

    public DataBindingForecastData(ForecastData forecastData) {
        this.forecastData = forecastData;
    }

    public long getDt() {
        return forecastData.getDt();
    }

    public Temperature getTemp() {
        return forecastData.getTemp();
    }

    public double getPressure() {
        return forecastData.getPressure();
    }

    public long getHumidity() {
        return forecastData.getHumidity();
    }

    public String getWeatherDescription() {
        if(forecastData.getWeather() != null && forecastData.getWeather().length > 0){
            return forecastData.getWeather()[0].getDescription();
        } else {
            return "";
        }
    }

    public double getSpeed() {
        return forecastData.getSpeed();
    }

    public long getDeg() {
        return forecastData.getDeg();
    }

    public long getClouds() {
        return forecastData.getClouds();
    }

    public double getRain() {
        return forecastData.getRain();
    }

}
