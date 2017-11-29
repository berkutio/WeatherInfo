package com.weatherinfo.activities.weatheractivity;


import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.weatherinfo.R;
import com.weatherinfo.adapters.ConfigResView;
import com.weatherinfo.adapters.WeatherAdapter;
import com.weatherinfo.model.DataBindingForecastData;
import com.weatherinfo.model.ForecastData;
import com.weatherinfo.model.WeatherResponse;
import com.weatherinfo.network.WeatherServiceImpl;
import com.weatherinfo.utils.Constants;
import com.weatherinfo.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Observable;
import io.reactivex.observers.DisposableSingleObserver;

public class ViewModelWeatherActivity extends Observable {

    private Activity context;
    private SchedulerProvider provider;
    private WeatherAdapter weatherAdapter;

    private DisposableSingleObserver<WeatherResponse> disposableSingleObserver;

    public ObservableInt observableVisibility;
    public ObservableField<WeatherResponse> observableWeatherResponse;
    public ObservableField<DataBindingForecastData> observableForecastDataToday;

    public ConfigResView configResView = new ConfigResView();


    public ViewModelWeatherActivity(Context context, SchedulerProvider provider) {
        this.context = (Activity) context;
        this.provider = provider;
        observableVisibility = new ObservableInt(View.INVISIBLE);
        observableWeatherResponse = new ObservableField<>();
        observableForecastDataToday = new ObservableField<>();
        initRecycler();
    }

    public void onDestroy(){
        if (disposableSingleObserver != null && !disposableSingleObserver.isDisposed()) {
            disposableSingleObserver.dispose();
        }
        context = null;
    }

    public void onObtainLocation(Location location){
        WeatherServiceImpl service = new WeatherServiceImpl(context, Constants.WEATHER_URI, Constants.WEATHER_API_KEY);
        disposableSingleObserver = service.getListData(location, 6)
                .subscribeOn(provider.io())
                .observeOn(provider.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherResponse>() {
                    @Override
                    public void onSuccess(WeatherResponse response) {
                        observableWeatherResponse.set(response);
                        if (response != null && response.getList() != null && response.getList().length > 0) {
                            observableForecastDataToday.set(new DataBindingForecastData(response.getList()[0]));
                        }
                        ArrayList<ForecastData> list = new ArrayList<>();
                        list.addAll(Arrays.asList(response.getList()));
                        list.remove(0);
                        weatherAdapter.updateAdapter(list);
                        observableVisibility.set(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (context != null) {
                            Toast.makeText(context, context.getString(R.string.error_weather_response), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void initRecycler() {
        weatherAdapter = new WeatherAdapter(new LinkedList<ForecastData>());
        configResView.setLayoutManager(new LinearLayoutManager(context));
        configResView.setAdapter(weatherAdapter);
    }

}
