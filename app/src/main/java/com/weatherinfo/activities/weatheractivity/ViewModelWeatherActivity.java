package com.weatherinfo.activities.weatheractivity;


import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.location.Location;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.weatherinfo.R;
import com.weatherinfo.model.DataBindingForecastData;
import com.weatherinfo.model.ForecastData;
import com.weatherinfo.model.WeatherResponse;
import com.weatherinfo.network.WeatherServiceImpl;
import com.weatherinfo.utils.Constants;
import com.weatherinfo.utils.rx.SchedulerProvider;

import java.util.Observable;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class ViewModelWeatherActivity extends Observable {

    private Activity context;
    private SchedulerProvider provider;
    private Disposable disposable;

    public ObservableInt observableVisibility;
    public ObservableField<WeatherResponse> observableWeatherResponse;
    public ObservableField<DataBindingForecastData> observableForecastDataToday;

    public ViewModelWeatherActivity(Context context, SchedulerProvider provider) {
        this.context = (Activity) context;
        this.provider = provider;
        observableVisibility = new ObservableInt(View.INVISIBLE);
        observableWeatherResponse = new ObservableField<>();
        observableForecastDataToday = new ObservableField<>();
    }

    public void onDestroy(){
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        context = null;
    }

    public void onObtainLocation(Location location){
        WeatherServiceImpl service = new WeatherServiceImpl(context, Constants.WEATHER_URI, Constants.WEATHER_API_KEY);
        disposable = service.getListData(location, 6)
                .subscribeOn(provider.io())
                .observeOn(provider.mainThread())
                .subscribeWith(new DisposableObserver<WeatherResponse>() {
                    @Override
                    public void onNext(WeatherResponse response) {
                        observableWeatherResponse.set(response);
                        if (response != null && response.getList() != null && response.getList().length > 0){
                            observableForecastDataToday.set(new DataBindingForecastData(response.getList()[0]));
                        }
                        observableVisibility.set(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(context != null){
                            Toast.makeText(context, context.getString(R.string.error_weather_response), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
