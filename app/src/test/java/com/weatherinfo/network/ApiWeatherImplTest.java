package com.weatherinfo.network;

import android.content.Context;
import android.location.Location;

import com.weatherinfo.model.City;
import com.weatherinfo.model.ForecastData;
import com.weatherinfo.model.WeatherDescription;
import com.weatherinfo.model.WeatherResponse;
import com.weatherinfo.utils.Constants;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ApiWeatherImplTest {

    private WeatherServiceImpl service;
    private Location location;

    @Before
    public void setUp() {
        Context context = mock(Context.class);
        location = mock(Location.class);
        when(location.getLatitude()).thenReturn(46.9534361);
        when(location.getLongitude()).thenReturn(31.9381652);
        service = new WeatherServiceImpl(context, Constants.WEATHER_URI, Constants.WEATHER_API_KEY);
    }

    @Test
    public void testGetListData() throws IOException {
        WeatherResponse response = service.getListData(location, 6).blockingGet();
        assertNotNull(response);
        System.out.println("Weather response + " + response);
        City city = response.getCity();
        assertNotNull(city);
        ForecastData[] list = response.getList();
        assertNotNull(list);
        assertEquals(6, list.length);
        ForecastData forecastData = list[0];
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