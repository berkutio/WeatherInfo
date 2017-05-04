package com.weatherinfo.model;

import java.util.Arrays;

public class ForecastData {

    private long dt;
    private Temperature temp;
    private double pressure;
    private long humidity;
    private WeatherDescription weather[];
    private double speed;
    private long deg;
    private long clouds;
    private double rain;

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public Temperature getTemp() {
        return temp;
    }

    public void setTemp(Temperature temp) {
        this.temp = temp;
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

    public WeatherDescription[] getWeather() {
        return weather;
    }

    public void setWeather(WeatherDescription[] weather) {
        this.weather = weather;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public long getDeg() {
        return deg;
    }

    public void setDeg(long deg) {
        this.deg = deg;
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
                "dt=" + dt +
                ", temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", weather=" + Arrays.toString(weather) +
                ", speed=" + speed +
                ", deg=" + deg +
                ", clouds=" + clouds +
                ", rain=" + rain +
                '}';
    }

    //    private double kelvinToCelsius(double temperatureInKelvin){
//        return temperatureInKelvin - 273;
//    }
//
//    private String[] getStringDateTime(long unixTime){
//        String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date (unixTime*1000));
//        String[] dateTimeArray = date.split(" ");
//        return dateTimeArray;
//    }
//
//    private double roundNew(double number, int scale) {
//        return new BigDecimal(number).setScale(scale, RoundingMode.UP)
//                .doubleValue();
//    }

}
