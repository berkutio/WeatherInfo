package com.weatherinfo.sup;


import com.weatherinfo.entityes.ForecastData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonParsers {


    public static ArrayList<ForecastData> getFiveDayForecastList(String response){
        String city;
        JSONObject jsonObject;
        JSONArray jsonArray;
        try {
            jsonObject = new JSONObject(response);
            jsonArray = jsonObject.optJSONArray("list");
            city = jsonObject.getJSONObject("city").getString("name");
            return getWeatherList(jsonArray, city);
        }catch (NullPointerException e){
            return new ArrayList<>();
        }
        catch (JSONException e) {
            return new ArrayList<>();
        }
    }

    private static ArrayList<ForecastData> getWeatherList(JSONArray jsonArray, String city){
        ArrayList<ForecastData> forecastList = new ArrayList<>(jsonArray.length());
        for (int i = 0; i< jsonArray.length(); i++){
            try {
                JSONObject objectFromAr = jsonArray.getJSONObject(i);
                forecastList.add(getData(objectFromAr, city));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return forecastList;
    }


    private static ForecastData getData(JSONObject objectFromAr, String city) throws JSONException{
        double temperature = objectFromAr.getJSONObject("temp").getDouble("day");
        double pressure = objectFromAr.getDouble("pressure");
        double humidity = objectFromAr.getDouble("humidity");
        double windSpeed = objectFromAr.getDouble("speed");
        JSONArray array = objectFromAr.getJSONArray("weather");
        String generalWeather = array.getJSONObject(0).getString("main");
        String generalDescription = array.getJSONObject(0).getString("description");
        return new ForecastData(objectFromAr.getLong("dt"),
                city,
                generalWeather,
                generalDescription,
                temperature,
                pressure,
                humidity,
                windSpeed);
    }


}
