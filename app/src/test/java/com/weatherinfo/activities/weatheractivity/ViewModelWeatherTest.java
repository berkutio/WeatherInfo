package com.weatherinfo.activities.weatheractivity;

import android.app.Activity;
import android.location.Location;
import android.view.View;

import com.weatherinfo.activities.weatheractivity.viewmodel.ViewModelWeather;
import com.weatherinfo.model.DataBindingForecastData;
import com.weatherinfo.model.WeatherResponse;
import com.weatherinfo.rxutils.TestSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewModelWeatherTest {

    private Location location;

    private TestScheduler mTestScheduler;

    private ViewModelWeather viewModelWeather;

    @Before
    public void setUp() throws Exception {
        Activity context = mock(Activity.class);
        location = mock(Location.class);
        when(location.getLatitude()).thenReturn(46.9534361);
        when(location.getLongitude()).thenReturn(31.9381652);
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
       //viewModelWeather = new ViewModelWeather(context, testSchedulerProvider);
    }

    @Test
    public void onObtainLocation() throws Exception {
        viewModelWeather.onObtainLocation(location);
        mTestScheduler.triggerActions();
        Thread.sleep(1000);
        //assertEquals(viewModelWeather.observableVisibility.get(), View.VISIBLE);
        //DataBindingForecastData data = viewModelWeather.observableForecastDataToday.get();
        //assertNotNull(data);
        //WeatherResponse response = viewModelWeather.observableWeatherResponse.get();
        //assertNotNull(response);
    }

}