package com.weatherinfo.activities.weatheractivity;

import android.location.Location;

/**
 * Created by user on 23.04.17.
 */

public interface IPresenterWeather {


    void onDestroy();

    void onObtainLocation(Location location);

}
