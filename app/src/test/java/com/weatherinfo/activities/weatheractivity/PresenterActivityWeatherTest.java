package com.weatherinfo.activities.weatheractivity;

import android.content.Context;
import android.location.Location;
import com.weatherinfo.model.WeatherResponse;
import com.weatherinfo.rxutils.TestSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by user on 26.04.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class PresenterActivityWeatherTest {


    @Mock
    private IViewWeather viewWeather;

    private Location location;

    private PresenterActivityWeather presenter;

    private TestScheduler mTestScheduler;

    @Before
    public void setUp() {
        Context context = mock(Context.class);
        location = mock(Location.class);
        when(location.getLatitude()).thenReturn(46.9534361);
        when(location.getLongitude()).thenReturn(31.9381652);
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        presenter = new PresenterActivityWeather(context, testSchedulerProvider, viewWeather);
    }

    @Test
    public void testOnDestroy(){
        presenter.onDestroy();
        assertNull(presenter.getMainView());
    }

    @Test
    public void testOnObtainLocation() throws Exception {
        presenter.onObtainLocation(location);
        mTestScheduler.triggerActions();
        Thread.sleep(1000);
        verify(viewWeather, atLeast(1)).onReceiveWeatherForecast((WeatherResponse) any());
    }

}