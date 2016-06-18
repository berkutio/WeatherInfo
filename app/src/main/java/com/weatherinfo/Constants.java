package com.weatherinfo;

/**
 * Created by berkut on 17.06.16.
 */
public interface Constants {

    String WEATHER_API_KEY = "85963bbe0766059b9bcdc77bcabb0b99";
    //http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=b1b15e88fa797225412429c1c50c122a
    String WEATHER_URI = "http://api.openweathermap.org/data/2.5/";
    //String WEATHER_URI_ONE_DAY = WEATHER_URI + "weather?";
    //http://api.openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid=b1b15e88fa797225412429c1c50c122a
    String WEATHER_URI_FIVE_DAYS = WEATHER_URI + "forecast/daily?";
//api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=10


}
