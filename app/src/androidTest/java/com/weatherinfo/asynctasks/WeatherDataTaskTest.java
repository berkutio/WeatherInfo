package com.weatherinfo.asynctasks;

import com.weatherinfo.Constants;
import com.weatherinfo.R;
import com.weatherinfo.RecyclerViewMatcher;
import com.weatherinfo.entityes.ForecastData;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.*;

/**
 * Created by berkut on 19.06.16.
 */
public class WeatherDataTaskTest implements Constants {

    WeatherDataTask task;
    String RIGHT_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=46.9719337&lon=32.0766118&cnt=6&APPID=85963bbe0766059b9bcdc77bcabb0b99";
    String BAD_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=46.9719337&lon=32.0766118&cnt=6&APPID=85963bbdc77bcabb0b99";


    @Before
    public void setUp() throws Exception {
        task = new WeatherDataTask();

    }

    @Test
    public void testForecastDataListNotNullWithRightUrl(){
        task.setOnCompleteListener(new WeatherDataTask.OnCompleteListener() {
            @Override
            public void onComplete(List<ForecastData> result) {
                assertNotNull(result);
            }
        });
        task.execute(RIGHT_URL);
    }


    @Test
    public void testForecastDataListNotNullWithEmptyUrl(){
        task.setOnCompleteListener(new WeatherDataTask.OnCompleteListener() {
            @Override
            public void onComplete(List<ForecastData> result) {
                assertNotNull(result);
            }
        });
        task.execute("");
    }

    @Test
    public void testForecastDataListNotNullWithBadUrl(){
        task.setOnCompleteListener(new WeatherDataTask.OnCompleteListener() {
            @Override
            public void onComplete(List<ForecastData> result) {
                assertNotNull(result);
            }
        });
        task.execute(BAD_URL);
    }

    @Test
    public void testForecastDataListSizeMoreZero(){
        task.setOnCompleteListener(new WeatherDataTask.OnCompleteListener() {
            @Override
            public void onComplete(List<ForecastData> result) {
                assertTrue(result.size() > 0);
            }
        });
        task.execute(RIGHT_URL);
    }

    @Test
    public void testCheckDataSetAtCurrentDay(){
        task.setOnCompleteListener(new WeatherDataTask.OnCompleteListener() {
            @Override
            public void onComplete(List<ForecastData> result) {
                ForecastData data = result.get(0);
                onView(withId(R.id.data_today)).check(matches(withText(data.getData())));
            }
        });
        task.execute(RIGHT_URL);
    }

    @Test
    public void testCheckDescriptionSetAtCurrentDay(){
        task.setOnCompleteListener(new WeatherDataTask.OnCompleteListener() {
            @Override
            public void onComplete(List<ForecastData> result) {
                ForecastData data = result.get(0);
                onView(withId(R.id.general_description_today)).check(matches(withText(data.getGeneralDescription())));

            }
        });
        task.execute(RIGHT_URL);
    }

    @Test
    public void testCheckTemperatureSetAtCurrentDay(){
        task.setOnCompleteListener(new WeatherDataTask.OnCompleteListener() {
            @Override
            public void onComplete(List<ForecastData> result) {
                ForecastData data = result.get(0);
                onView(withId(R.id.temperature_val_today)).check(matches(withText(String.valueOf(data.getTemperature()))));
            }
        });
        task.execute(RIGHT_URL);
    }

    @Test
    public void testCheckHumiditySetAtCurrentDay(){
        task.setOnCompleteListener(new WeatherDataTask.OnCompleteListener() {
            @Override
            public void onComplete(List<ForecastData> result) {
                ForecastData data = result.get(0);
                onView(withId(R.id.humidity_val_today)).check(matches(withText(String.valueOf(data.getHumidity()))));
            }
        });
        task.execute(RIGHT_URL);
    }

    @Test
    public void testCheckPressureSetAtCurrentDay(){
        task.setOnCompleteListener(new WeatherDataTask.OnCompleteListener() {
            @Override
            public void onComplete(List<ForecastData> result) {
                ForecastData data = result.get(0);
                onView(withId(R.id.pressure_val_today)).check(matches(withText(String.valueOf(data.getPressure()))));
            }
        });
        task.execute(RIGHT_URL);
    }

    @Test
    public void testCheckWindSpeedSetAtCurrentDay(){
        task.setOnCompleteListener(new WeatherDataTask.OnCompleteListener() {
            @Override
            public void onComplete(List<ForecastData> result) {
                ForecastData data = result.get(0);
                onView(withId(R.id.wind_speed_val_today)).check(matches(withText(String.valueOf(data.getWindSpeed()))));
            }
        });
        task.execute(RIGHT_URL);
    }

    @Test
    public void testCheckDataAtFirstListItem(){
        task.setOnCompleteListener(new WeatherDataTask.OnCompleteListener() {
            @Override
            public void onComplete(List<ForecastData> result) {
                ForecastData data = result.get(1);
                onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                        .check(matches(hasDescendant(withText(data.getData()))));
            }
        });
        task.execute(RIGHT_URL);
    }

    @Test
    public void testCheckDescriptionAtFirstListItem(){
        task.setOnCompleteListener(new WeatherDataTask.OnCompleteListener() {
            @Override
            public void onComplete(List<ForecastData> result) {
                ForecastData data = result.get(1);
                onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                        .check(matches(hasDescendant(withText(data.getGeneralWeather()))));
            }
        });
        task.execute(RIGHT_URL);
    }

    @Test
    public void testCheckTemperatureAtFirstListItem(){
        task.setOnCompleteListener(new WeatherDataTask.OnCompleteListener() {
            @Override
            public void onComplete(List<ForecastData> result) {
                ForecastData data = result.get(1);
                onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                        .check(matches(hasDescendant(withText(String.valueOf(data.getTemperature())))));
            }
        });
        task.execute(RIGHT_URL);
    }

    @Test
    public void testCheckHumidityAtFirstListItem(){
        task.setOnCompleteListener(new WeatherDataTask.OnCompleteListener() {
            @Override
            public void onComplete(List<ForecastData> result) {
                ForecastData data = result.get(1);
                onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                        .check(matches(hasDescendant(withText(String.valueOf(data.getHumidity())))));
            }
        });
        task.execute(RIGHT_URL);
    }

    @Test
    public void testCheckPressureAtFirstListItem(){
        task.setOnCompleteListener(new WeatherDataTask.OnCompleteListener() {
            @Override
            public void onComplete(List<ForecastData> result) {
                ForecastData data = result.get(1);
                onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                        .check(matches(hasDescendant(withText(String.valueOf(data.getPressure())))));
            }
        });
        task.execute(RIGHT_URL);
    }

    @Test
    public void testCheckWindSpeedAtFirstListItem(){
        task.setOnCompleteListener(new WeatherDataTask.OnCompleteListener() {
            @Override
            public void onComplete(List<ForecastData> result) {
                ForecastData data = result.get(1);
                onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                        .check(matches(hasDescendant(withText(String.valueOf(data.getWindSpeed())))));
            }
        });
        task.execute(RIGHT_URL);
    }


    public RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}