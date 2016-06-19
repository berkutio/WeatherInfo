package com.weatherinfo;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.location.LocationSettingsResult;


/**
 * Created by berkut on 19.06.16.
 */
public class WeatherTest extends ActivityInstrumentationTestCase2<Weather> {

    private Weather weather;

    public WeatherTest() {
        super(Weather.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        weather = getActivity();
    }

    @SmallTest
    public void testActivityExists() {
        assertNotNull(weather);
    }

    @SmallTest
    public void testToolBarNotNull(){
        Toolbar toolbar = (Toolbar) weather.findViewById(R.id.toolbar);
        assertNotNull(toolbar);
    }

    @SmallTest
    public void testProgressBarLayoutNotNull(){
        LinearLayout progressBarLayout = (LinearLayout) weather.findViewById(R.id.progressBarLayout);
        assertNotNull(progressBarLayout);
    }

    @SmallTest
    public void testDataLayoutNotNull(){
        LinearLayout dataLayout = (LinearLayout) weather.findViewById(R.id.dataLayout);
        assertNotNull(dataLayout);
    }

    @SmallTest
    public void testLocalityTextViewNotNull(){
        TextView locality = (TextView) weather.findViewById(R.id.locality);
        assertNotNull(locality);
    }

    @SmallTest
    public void testDataTextViewNotNull(){
        TextView data = (TextView) weather.findViewById(R.id.data_today);
        assertNotNull(data);
    }

    @SmallTest
    public void testGeneralTextViewNotNull(){
        TextView general = (TextView) weather.findViewById(R.id.general_description_today);
        assertNotNull(general);
    }

    @SmallTest
    public void testTemperatureTextViewNotNull(){
        TextView temperature = (TextView) weather.findViewById(R.id.temperature_val_today);
        assertNotNull(temperature);
    }

    @SmallTest
    public void testPressureTextViewNotNull(){
        TextView pressure = (TextView) weather.findViewById(R.id.pressure_val_today);
        assertNotNull(pressure);
    }

    @SmallTest
    public void testHumidityTextViewNotNull(){
        TextView humidity = (TextView) weather.findViewById(R.id.humidity_val_today);
        assertNotNull(humidity);
    }

    @SmallTest
    public void testWindSpeedTextViewNotNull(){
        TextView windSpeed = (TextView) weather.findViewById(R.id.wind_speed_val_today);
        assertNotNull(windSpeed);
    }

    @SmallTest
    public void testRecycleViewNotNull(){
        RecyclerView mRecyclerView = (RecyclerView) weather.findViewById(R.id.recycler_view);
        assertNotNull(mRecyclerView);
    }

    @LargeTest
    public void testOnActivityResult() throws Exception {
        weather.onActivityResult(1, weather.RESULT_CANCELED, new Intent());
        assertTrue(weather.isFinishing());
    }

}