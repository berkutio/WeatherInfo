package com.weatherinfo.adapters;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.weatherinfo.Weather;
import com.weatherinfo.entityes.ForecastData;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by berkut on 19.06.16.
 */
public class AdapterTest {

    List<ForecastData> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
        list.add(new ForecastData(getUnixTime("21/06/2016", ""),
                "Mykolayiv",
                "Clear",
                "clear sky",
                298.71,
                1031.86,
                58.0,
                3.77));
        list.add(new ForecastData(getUnixTime("22/06/2016", ""),
                "Mykolayiv",
                "Cloud",
                "Full cloud",
                303.06,
                1031.86,
                58.0,
                3.77));
        list.add(new ForecastData(getUnixTime("23/06/2016", ""),
                "Mykolayiv",
                "Rain",
                "rain",
                305.19,
                1031.86,
                56.0,
                3.77));
    }


    @Test
    public void testAdapterSize(){
        Adapter adapter = new Adapter(list);
        assertTrue(list.size() == adapter.getItemCount());
    }


    private long getUnixTime(String date, String time){
        long epochTime;
        try {
            Date epoch = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + time);
            epochTime = epoch.getTime()/1000L;
        } catch (ParseException e) {
            epochTime = 0;
        }
        return epochTime;
    }
}