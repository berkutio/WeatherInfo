package com.weatherinfo.entityes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by berkut on 18.06.16.
 */
public class ForecastDataTest  {

    ForecastData forecastData;

    @Before
    public void setUp() {
        forecastData = new ForecastData(1466240400,
                "Mykolayiv",
                "Clouds",
                "few clouds",
                295.92,
                1026.94,
                75,
                4.2);
    }

    @Test
    public void testGetData() {
        assertTrue(forecastData.getData().equals("18/06/2016"));
    }

    @Test
    public void testGetLocality() {
        assertTrue(forecastData.getLocality().equals("Mykolayiv"));
    }

    @Test
    public void testGetGeneralWeather() {
        assertTrue(forecastData.getGeneralWeather().equals("Clouds"));
    }

    @Test
    public void testGetGeneralDescription() {
        assertTrue(forecastData.getGeneralDescription().equals("few clouds"));
    }

    @Test
    public void testGetTemperature() {
        assertTrue(forecastData.getTemperature() == 23.0);
    }

    @Test
    public void testGetPressure() {
        assertTrue(forecastData.getPressure() == 1026.94);
    }

    @Test
    public void testGetHumidity() {
        assertTrue(forecastData.getHumidity() == 75);
    }

    @Test
    public void testGetWindSpeed() {
        assertTrue(forecastData.getWindSpeed() == 4.2);
    }

}