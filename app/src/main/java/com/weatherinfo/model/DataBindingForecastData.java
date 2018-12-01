package com.weatherinfo.model;

public class DataBindingForecastData {

    private final ForecastData forecastData;

    public DataBindingForecastData(ForecastData forecastData) {
        this.forecastData = forecastData;
    }

    public long getData() {
        return forecastData.getData();
    }

    public Temperature getTemperature() {
        return forecastData.getTemperature();
    }

    public double getPressure() {
        return forecastData.getPressure();
    }

    public long getHumidity() {
        return forecastData.getHumidity();
    }

    public String getWeatherDescription() {
        if(forecastData.getWeatherDescription() != null && forecastData.getWeatherDescription().length > 0){
            return forecastData.getWeatherDescription()[0].getDescription();
        } else {
            return "";
        }
    }

    public double getSpeed() {
        return forecastData.getSpeed();
    }

    public long getDeg() {
        return forecastData.getDegree();
    }

    public long getClouds() {
        return forecastData.getClouds();
    }

    public double getRain() {
        return forecastData.getRain();
    }

}
