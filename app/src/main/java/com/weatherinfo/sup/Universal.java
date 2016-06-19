package com.weatherinfo.sup;

import android.location.Location;
import android.net.Uri;
import com.weatherinfo.Constants;

public class Universal implements Constants {


    public static String getMoreDaysRequest(String url, Location location, int days){
        if(url == null || url.equals("")) throw new IllegalArgumentException("Argument url is null or equals \"\"");
        if(location == null) throw new NullPointerException("Argument location is null!");
        if(days <= 0) throw new IllegalArgumentException("Argument days is " + days + " must be positive!");
        if(days > 16) throw new IllegalArgumentException("Argument days is " + days + " must be less than 16!");
        return Uri.parse(url).buildUpon()
                .appendQueryParameter("lat", String.valueOf(location.getLatitude()))
                .appendQueryParameter("lon", String.valueOf(location.getLongitude()))
                .appendQueryParameter("cnt", String.valueOf(days))
                .appendQueryParameter("APPID", WEATHER_API_KEY)
                .build().toString();
    }

}
