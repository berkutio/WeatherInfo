package com.weatherinfo.utils;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.weatherinfo.R;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Universal {

    public static String formatData(long time){
        Format formatter = new SimpleDateFormat("dd MMM");
        Date today = new Date(time*1000);
        return formatter.format(today);
    }

    public static String convertToCelicies(double temperature){
        return String.valueOf((int) temperature - 273);
    }

    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView imageView, String description){
        Log.d("myLogs", "setImageResource");
        if(description != null){
            if(description.contains("clear")){
                imageView.setImageResource(R.drawable.ic_sunny);
            } else if(description.contains("rain")){
                imageView.setImageResource(R.drawable.ic_rain);
            } else if(description.contains("snow")){
                imageView.setImageResource(R.drawable.ic_snow);
            } else if(description.contains("cloud")){
                imageView.setImageResource(R.drawable.ic_cloud);
            } else if(description.contains("thunder")){
                imageView.setImageResource(R.drawable.ic_thunder);
            }
        }
    }

    public static int getWeatherResource(String description){
        if(description.contains("clear")){
            return R.drawable.ic_sunny;
        } else if(description.contains("rain")){
            return R.drawable.ic_rain;
        } else if(description.contains("snow")){
            return R.drawable.ic_snow;
        } else if(description.contains("cloud")){
            return R.drawable.ic_cloud;
        } else if(description.contains("thunder")){
            return R.drawable.ic_thunder;
        } else {
            return 0;
        }
    }

}
