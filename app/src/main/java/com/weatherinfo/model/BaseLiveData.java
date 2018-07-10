package com.weatherinfo.model;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Transformations;

public class BaseLiveData<ResponseType, LifeDataInstance extends LiveDataResponse<ResponseType>> {

    private MediatorLiveData<LifeDataInstance> mResponseData;
    private LiveData<String> mErrorMessageData;

    public BaseLiveData() {
        this.mResponseData = new MediatorLiveData<>();
        mErrorMessageData = Transformations.switchMap(mResponseData, input -> {
            MediatorLiveData<String> errorMsg = new MediatorLiveData<>();
            if (input.getError() != null) {
                errorMsg.postValue(input.getError());
            }
            return errorMsg;
        });
    }

    public BaseLiveData(MediatorLiveData<LifeDataInstance> mResponseData, LiveData<String> mErrorMessageData) {
        this.mResponseData = mResponseData;
        this.mErrorMessageData = mErrorMessageData;
    }

    public void post(LifeDataInstance value){
        mResponseData.postValue(value);
    }

    public void setValue(LifeDataInstance value){
        mResponseData.setValue(value);
    }

    public MediatorLiveData<LifeDataInstance> getResponseData() {
        return mResponseData;
    }

    public LiveData getErrorData(){
        return mErrorMessageData;
    }

    public void observe(LifecycleOwner owner, OnEventListener listener){
        mResponseData.observe(owner, listener::onResult);
        mErrorMessageData.observe(owner, listener::onError);
    }

    public interface OnEventListener<ResponseType> {
        void onResult(LiveDataResponse<ResponseType> response);
        void onError(String errorMsg);
    }

}
