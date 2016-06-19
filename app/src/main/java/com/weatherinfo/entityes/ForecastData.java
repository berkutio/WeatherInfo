package com.weatherinfo.entityes;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ForecastData {

    private String data;
    private String locality;
    private String generalWeather;
    private String generalDescription;
    private double temperature;
    private double pressure;
    private double humidity;
    private double windSpeed;

    public ForecastData(long data, String locality, String generalWeather, String generalDescription, double temperature, double pressure, double humidity, double windSpeed) {
        this.data = getStringDateTime(data)[0];
        this.locality = locality;
        this.generalWeather = generalWeather;
        this.generalDescription = generalDescription;
        setTemperature(roundNew(kelvinToCelsius(temperature), 0));
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    public String getData() {
        return data;
    }

    public String getLocality() {
        return locality;
    }

    public String getGeneralWeather() {
        return generalWeather;
    }

    public String getGeneralDescription() {
        return generalDescription;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }


    private double kelvinToCelsius(double temperatureInKelvin){
        return temperatureInKelvin - 273;
    }

    private String[] getStringDateTime(long unixTime){
        String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date (unixTime*1000));
        String[] dateTimeArray = date.split(" ");
        return dateTimeArray;
    }

    private double roundNew(double number, int scale) {
        return new BigDecimal(number).setScale(scale, RoundingMode.UP)
                .doubleValue();
    }

}
