package com.weatherinfo.asynctasks;

import android.os.AsyncTask;
import com.weatherinfo.entityes.ForecastData;
import com.weatherinfo.sup.GetResponseClass;
import com.weatherinfo.sup.JsonParsers;
import java.util.ArrayList;
import java.util.List;

public class WeatherDataTask extends AsyncTask<String, Void, List<ForecastData>> {

    private OnCompleteListener listener;

    @Override
    protected List<ForecastData> doInBackground(String... params) {
        String res2 = GetResponseClass.getResult(params[0]);
        ArrayList<ForecastData> d = JsonParsers.getFiveDayForecastList(res2);
        return d;
    }

    @Override
    protected void onPostExecute(List<ForecastData> result) {
        if(listener != null){
            this.listener.onComplete(result);
        }
    }

    public WeatherDataTask setOnCompleteListener(OnCompleteListener listener){
        this.listener = listener;
        return this;
    }

    public interface OnCompleteListener {
        void onComplete(List<ForecastData> result);
    }
}
