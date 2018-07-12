package com.weatherinfo.network;

import com.google.gson.GsonBuilder;
import com.weatherinfo.model.City;
import com.weatherinfo.model.ForecastData;
import com.weatherinfo.model.WeatherDescription;
import com.weatherinfo.model.WeatherResponse;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;


public class ApiWeatherImplTest {

    public static final double TEST_LAT = 46.9534361;
    public static final double TEST_LON = 31.9381652;

    public static final String TEST_BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String TEST_API_KEY = "85963bbe0766059b9bcdc77bcabb0b99";

    private ApiWeather apiWeather;

    @Before
    public void setUp() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TEST_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiWeather = retrofit.create(ApiWeather.class);
    }

    @Test
    public void testGetListData() {
        WeatherResponse response = apiWeather.getForecast(TEST_LAT, TEST_LON, 6, TEST_API_KEY).blockingGet();
        assertNotNull(response);
        System.out.println("Weather response + " + response);
        City city = response.getCity();
        assertNotNull(city);
        List<ForecastData> list = response.getList();
        assertNotNull(list);
        assertEquals(6, list.size());
        ForecastData forecastData = list.get(0);
        assertNotNull(forecastData);
        WeatherDescription[] descriptions = forecastData.getWeather();
        assertNotNull(descriptions);
        assertNotEquals(0, descriptions.length);
        assertNotNull(descriptions[0].getDescription());
        assertNotEquals(0, forecastData.getDt());
        assertNotEquals(0, forecastData.getDeg());
        assertNotEquals(0, forecastData.getHumidity());
        assertNotEquals(0, forecastData.getPressure());
    }

}