package com.weatherinfo.activities.weatheractivity.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.location.Location;

import com.weatherinfo.model.baselivedata.BaseLiveData;
import com.weatherinfo.model.LiveDataResponse;
import com.weatherinfo.model.WeatherResponse;
import com.weatherinfo.network.ServiceWeather;
import com.weatherinfo.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

public class ViewModelWeather extends ViewModel {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private SchedulerProvider mSchedulerProvider;
    private ServiceWeather mServiceWeather;

    private Location mLocation;

    private BaseLiveData<WeatherResponse, LiveDataResponse<WeatherResponse>> mWeatherResponseData;

    public ViewModelWeather(SchedulerProvider mSchedulerProvider, ServiceWeather mServiceWeather) {
        this.mSchedulerProvider = mSchedulerProvider;
        this.mServiceWeather = mServiceWeather;
        mWeatherResponseData = new BaseLiveData<>();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }

    public void onObtainLocation(Location location) {
        mLocation = location;
        mCompositeDisposable.add(mServiceWeather.getListData(location, 6)
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


    public BaseLiveData<WeatherResponse, LiveDataResponse<WeatherResponse>> getWeatherResponseData() {
        return mWeatherResponseData;
    }

    public Location getLocation() {
        return mLocation;
    }

}
