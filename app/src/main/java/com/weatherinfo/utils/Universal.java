package com.weatherinfo.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.weatherinfo.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Universal {

    private static final String DATE_FORMAT = "dd MMM";
    private static final int DATE_OFFSET = 1000;

    private static final String[] PERCIPITATIONS = {"clear", "rain", "snow", "cloud", "thunder"};

    public static String formatData(long time) {
        Format formatter = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        Date today = new Date(time * DATE_OFFSET);
        return formatter.format(today);
    }

    public static String convertToCelcies(double temperature) {
        return String.valueOf((int) temperature - 273);
    }

    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView imageView, String description) {
        if (description != null) {
            if (description.contains(PERCIPITATIONS[0])) {
                imageView.setImageResource(R.drawable.ic_sunny);
            } else if (description.contains(PERCIPITATIONS[1])) {
                imageView.setImageResource(R.drawable.ic_rain);
            } else if (description.contains(PERCIPITATIONS[2])) {
                imageView.setImageResource(R.drawable.ic_snow);
            } else if (description.contains(PERCIPITATIONS[3])) {
                imageView.setImageResource(R.drawable.ic_cloud);
            } else if (description.contains(PERCIPITATIONS[4])) {
                imageView.setImageResource(R.drawable.ic_thunder);
            }
        }
    }

    public static int getWeatherResource(String description) {
        if (description.contains(PERCIPITATIONS[0])) {
            return R.drawable.ic_sunny;
        } else if (description.contains(PERCIPITATIONS[1])) {
            return R.drawable.ic_rain;
        } else if (description.contains(PERCIPITATIONS[2])) {
            return R.drawable.ic_snow;
        } else if (description.contains(PERCIPITATIONS[3])) {
            return R.drawable.ic_cloud;
        } else if (description.contains(PERCIPITATIONS[4])) {
            return R.drawable.ic_thunder;
        } else {
            return 0;
        }
    }

}
