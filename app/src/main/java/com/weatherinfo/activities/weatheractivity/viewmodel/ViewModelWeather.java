package com.weatherinfo.activities.weatheractivity.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;
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

    private MediatorLiveData<WeatherResponse> mWeatherResponseData;

    public ViewModelWeather(SchedulerProvider mSchedulerProvider, ServiceWeather mServiceWeather, Application mApplication) {
        this.mSchedulerProvider = mSchedulerProvider;
        this.mServiceWeather = mServiceWeather;
        this.mApplication = mApplication;
        mWeatherResponseData = new MediatorLiveData<>();
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
                        mWeatherResponseData.postValue(weatherResponse);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }

    public MediatorLiveData<WeatherResponse> getWeatherResponseData() {
        return mWeatherResponseData;
    }

    public Location getLocation() {
        return mLocation;
    }

    //    private Activity context;
//    private SchedulerProvider provider;
//    private WeatherAdapter weatherAdapter;
//
//    private DisposableSingleObserver<WeatherResponse> disposableSingleObserver;
//
//    public ObservableInt observableVisibility;
//    public ObservableField<WeatherResponse> observableWeatherResponse;
//    public ObservableField<DataBindingForecastData> observableForecastDataToday;
//
//    public ConfigResView configResView = new ConfigResView();
//
//
//    public ViewModelWeather(Context context, SchedulerProvider provider) {
//        this.context = (Activity) context;
//        this.provider = provider;
//        observableVisibility = new ObservableInt(View.INVISIBLE);
//        observableWeatherResponse = new ObservableField<>();
//        observableForecastDataToday = new ObservableField<>();
//        initRecycler();
//    }
//
//    public void onDestroy(){
//        if (disposableSingleObserver != null && !disposableSingleObserver.isDisposed()) {
//            disposableSingleObserver.dispose();
//        }
//        context = null;
//    }
//
//    public void onObtainLocation(Location location){
//        ServiceWeather service = new ServiceWeather(context, Constants.WEATHER_URI, Constants.WEATHER_API_KEY);
//        disposableSingleObserver = service.getListData(location, 6)
//                .subscribeOn(provider.io())
//                .observeOn(provider.mainThread())
//                .subscribeWith(new DisposableSingleObserver<WeatherResponse>() {
//                    @Override
//                    public void onSuccess(WeatherResponse response) {
//                        observableWeatherResponse.set(response);
//                        if (response != null && response.getList() != null && response.getList().length > 0) {
//                            observableForecastDataToday.set(new DataBindingForecastData(response.getList()[0]));
//                        }
//                        ArrayList<ForecastData> list = new ArrayList<>();
//                        list.addAll(Arrays.asList(response.getList()));
//                        list.remove(0);
//                        weatherAdapter.updateAdapter(list);
//                        observableVisibility.set(View.VISIBLE);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if (context != null) {
//                            Toast.makeText(context, context.getString(R.string.error_weather_response), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//    }
//
//    private void initRecycler() {
//        weatherAdapter = new WeatherAdapter(new LinkedList<ForecastData>());
//        configResView.setLayoutManager(new LinearLayoutManager(context));
//        configResView.setAdapter(weatherAdapter);
//    }

}
