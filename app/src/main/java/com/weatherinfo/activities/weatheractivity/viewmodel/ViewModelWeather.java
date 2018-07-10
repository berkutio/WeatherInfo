package com.weatherinfo.activities.weatheractivity.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.location.Location;

import com.weatherinfo.model.BaseLiveData;
import com.weatherinfo.model.LiveDataResponse;
import com.weatherinfo.model.WeatherResponse;
import com.weatherinfo.network.ServiceWeather;
import com.weatherinfo.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

public class ViewModelWeather extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private SchedulerProvider mSchedulerProvider;
    private ServiceWeather mServiceWeather;
    private Application mApplication;

    private Location mLocation;

    private BaseLiveData<WeatherResponse, LiveDataResponse<WeatherResponse>> mWeatherResponseData;

    public ViewModelWeather(SchedulerProvider mSchedulerProvider, ServiceWeather mServiceWeather, Application mApplication) {
        this.mSchedulerProvider = mSchedulerProvider;
        this.mServiceWeather = mServiceWeather;
        this.mApplication = mApplication;
        mWeatherResponseData = new BaseLiveData<>();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public void onObtainLocation(Location location){
        mLocation = location;
        compositeDisposable.add(mServiceWeather.getListData(location, 6)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherResponse>() {
                    @Override
                    public void onSuccess(WeatherResponse weatherResponse) {
                        mWeatherResponseData.setValue(new LiveDataResponse<>(weatherResponse));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mWeatherResponseData.setValue(new LiveDataResponse<>(e.getMessage()));
                    }
                }));
    }


    public BaseLiveData<WeatherResponse, LiveDataResponse<WeatherResponse>> getmWeatherResponseData() {
        return mWeatherResponseData;
    }

    public Location getLocation() {
        return mLocation;
    }

}
