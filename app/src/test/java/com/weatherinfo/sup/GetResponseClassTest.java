package com.weatherinfo.sup;

import com.weatherinfo.sup.GetResponseClass;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class GetResponseClassTest {

    private static final String RIGHT_REQUEST = "http://api.openweathermap.org/data/2.5/weather?lat=46.971855&lon=32.0766342&APPID=85963bbe0766059b9bcdc77bcabb0b99";
    private static final String WRONG_REQUEST = "http://wrong";


    @Test
    public void testGetResponse400ForNullRequest(){
        assertEquals(GetResponseClass.getResult(null), String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST));
    }

    @Test
    public void testGetResponse400ForEmptyRequest(){
        assertEquals(GetResponseClass.getResult(""), String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST));
    }

    @Test
    public void testGetResponse400ForWrongRequest(){
        assertEquals(GetResponseClass.getResult(WRONG_REQUEST), String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST));
    }

    @Test
    public void testGetGoodResponseForRightRequest() throws JSONException {
        String result = GetResponseClass.getResult(RIGHT_REQUEST);
        JSONObject object = new JSONObject(result);
        assertEquals(String.valueOf(object.getInt("cod")), String.valueOf(HttpURLConnection.HTTP_OK));
    }
}