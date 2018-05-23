package com.weatherinfo.model;

import java.util.List;

public class WeatherResponse {

    private City city;
    private String cod;
    private double message;
    private long cnt;
    private List<ForecastData> list;
    private ForecastData firstDay;

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

    public List<ForecastData>  getList() {
        return list;
    }

    public void setList(List<ForecastData>  list) {
        this.list = list;
    }

    public ForecastData getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(ForecastData firstDay) {
        this.firstDay = firstDay;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "city=" + city +
                ", cod='" + cod + '\'' +
                ", message=" + message +
                ", cnt=" + cnt +
                ", list=" + list +
                ", firstDay=" + firstDay +
                '}';
    }
}
