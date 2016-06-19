package com.weatherinfo.sup;

import android.location.Location;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.weatherinfo.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.*;


/**
 * Created by berkut on 17.06.16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class UniversalTest implements Constants {

    private static final String URL = "";

    @Mock
    Location location;

    @Before
    public void setUp() {
        location = new Location("MyProvider");
        location.setLatitude(46.971855);
        location.setLongitude(31.9974);
    }

    @Test
    public void testContainsData(){
        assertTrue(Universal.getMoreDaysRequest(WEATHER_URI_FIVE_DAYS, location, 1).contains("46.971855"));
    }

    @Test
    public void testContaintsWeatherApiKey(){
        assertTrue(Universal.getMoreDaysRequest(WEATHER_URI_FIVE_DAYS, location, 1).contains(WEATHER_API_KEY));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyUrl(){
        Universal.getMoreDaysRequest("", location, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullUrl(){
        Universal.getMoreDaysRequest("", location, 1);
    }

    @Test(expected = NullPointerException.class)
    public void testNullLocation(){
        Universal.getMoreDaysRequest(WEATHER_URI_FIVE_DAYS, null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDaysIsNegative(){
        Universal.getMoreDaysRequest(WEATHER_URI_FIVE_DAYS, location, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDaysIsZero(){
        Universal.getMoreDaysRequest(WEATHER_URI_FIVE_DAYS, location, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDaysIsMore16(){
        Universal.getMoreDaysRequest(WEATHER_URI_FIVE_DAYS, location, 17);
    }


}