package com.weatherinfo.sup;

import com.weatherinfo.entityes.ForecastData;

import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by berkut on 18.06.16.
 */
public class JsonParsersTest {

    private final String GOOD_RESPONSE = "{\"city\":{\"id\":700569,\"name\":\"Mykolayiv\",\"coord\":{\"lon\":31.9974,\"lat\":46.965912},\"country\":\"UA\",\"population\":0},\"cod\":\"200\",\"message\":0.05,\"cnt\":6,\"list\":[{\"dt\":1466240400,\"temp\":{\"day\":295.92,\"min\":293.55,\"max\":295.92,\"night\":293.55,\"eve\":295.92,\"morn\":295.92},\"pressure\":1026.94,\"humidity\":75,\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"speed\":4.2,\"deg\":208,\"clouds\":12},{\"dt\":1466326800,\"temp\":{\"day\":299.34,\"min\":293.02,\"max\":300.61,\"night\":294.03,\"eve\":300.46,\"morn\":293.02},\"pressure\":1028.46,\"humidity\":75,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"speed\":1.76,\"deg\":215,\"clouds\":0},{\"dt\":1466413200,\"temp\":{\"day\":303.32,\"min\":293.23,\"max\":304.38,\"night\":297.74,\"eve\":304.38,\"morn\":293.23},\"pressure\":1030.21,\"humidity\":62,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"speed\":2.38,\"deg\":77,\"clouds\":0},{\"dt\":1466499600,\"temp\":{\"day\":304.56,\"min\":296.42,\"max\":305.47,\"night\":297.75,\"eve\":305.42,\"morn\":296.42},\"pressure\":1031.41,\"humidity\":58,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":4.32,\"deg\":69,\"clouds\":0,\"rain\":0.9},{\"dt\":1466586000,\"temp\":{\"day\":307.51,\"min\":300.37,\"max\":307.51,\"night\":300.37,\"eve\":303.56,\"morn\":303.02},\"pressure\":1026.02,\"humidity\":0,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":5.96,\"deg\":69,\"clouds\":12,\"rain\":1.31},{\"dt\":1466672400,\"temp\":{\"day\":305.9,\"min\":299.77,\"max\":305.9,\"night\":299.77,\"eve\":303.07,\"morn\":301.82},\"pressure\":1023.19,\"humidity\":0,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":6.85,\"deg\":60,\"clouds\":57,\"rain\":0.22}]}";
    private final String BAD_RESPONSE = "{\"city\":{\"id\":700569,\"name\":\"Mykolayiv\",\"coord\":{\"lon\":31.9974,\"lat\":46.965912},\"country\":\"UA\",\"population\":0},\"cod\":\"200\",\"message\":0.05,\"cnt\":6,\"l\":[{\"dt\":1466240400,\"temp\":{\"day\":295.92,\"min\":293.55,\"max\":295.92,\"night\":293.55,\"eve\":295.92,\"morn\":295.92},\"pressure\":1026.94,\"humidity\":75,\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"speed\":4.2,\"deg\":208,\"clouds\":12},{\"dt\":1466326800,\"temp\":{\"day\":299.34,\"min\":293.02,\"max\":300.61,\"night\":294.03,\"eve\":300.46,\"morn\":293.02},\"pressure\":1028.46,\"humidity\":75,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"speed\":1.76,\"deg\":215,\"clouds\":0},{\"dt\":1466413200,\"temp\":{\"day\":303.32,\"min\":293.23,\"max\":304.38,\"night\":297.74,\"eve\":304.38,\"morn\":293.23},\"pressure\":1030.21,\"humidity\":62,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"speed\":2.38,\"deg\":77,\"clouds\":0},{\"dt\":1466499600,\"temp\":{\"day\":304.56,\"min\":296.42,\"max\":305.47,\"night\":297.75,\"eve\":305.42,\"morn\":296.42},\"pressure\":1031.41,\"humidity\":58,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":4.32,\"deg\":69,\"clouds\":0,\"rain\":0.9},{\"dt\":1466586000,\"temp\":{\"day\":307.51,\"min\":300.37,\"max\":307.51,\"night\":300.37,\"eve\":303.56,\"morn\":303.02},\"pressure\":1026.02,\"humidity\":0,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":5.96,\"deg\":69,\"clouds\":12,\"rain\":1.31},{\"dt\":1466672400,\"temp\":{\"day\":305.9,\"min\":299.77,\"max\":305.9,\"night\":299.77,\"eve\":303.07,\"morn\":301.82},\"pressure\":1023.19,\"humidity\":0,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":6.85,\"deg\":60,\"clouds\":57,\"rain\":0.22}]}";


    @Test
    public void testCheckParserSizeForResponse400 (){
        ArrayList<ForecastData> data = JsonParsers.getFiveDayForecastList(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST));
        assertTrue(data.size() == 0);
    }

    @Test
    public void testCheckParserWithNullResponse(){
        ArrayList<ForecastData> data = JsonParsers.getFiveDayForecastList(null);
        assertTrue(data.size() == 0);
    }

    @Test
    public void testCheckParserWithEmptyResponse(){
        ArrayList<ForecastData> data = JsonParsers.getFiveDayForecastList("");
        assertTrue(data.size() == 0);
    }

    @Test
    public void testCheckParserWithGoodResponse(){
        ArrayList<ForecastData> data = JsonParsers.getFiveDayForecastList(GOOD_RESPONSE);
        assertTrue(data.size() == 6);
    }

    @Test
    public void testCheckParserWithBadResponse(){
        ArrayList<ForecastData> data = JsonParsers.getFiveDayForecastList(BAD_RESPONSE);
        assertTrue(data.size() == 0);
    }

}