package com.weatherinfo.model.baselivedata;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;

import com.weatherinfo.model.LiveDataResponse;

public class BaseLiveData<ResponseType, LifeDataInstance extends LiveDataResponse<ResponseType>> {

    private MutableLiveData<LifeDataInstance> mResponseData;
    private ErrorMessageObservable mErrorMessageObservable;

    public BaseLiveData() {
        this.mResponseData = new MutableLiveData<>();
        mErrorMessageObservable = new ErrorMessageObservable();
    }

    public BaseLiveData(MutableLiveData<LifeDataInstance> mResponseData) {
        this.mResponseData = mResponseData;
        mErrorMessageObservable = new ErrorMessageObservable();
    }

    public BaseLiveData(ErrorMessageObservable mErrorMessageObservable) {
        this.mErrorMessageObservable = mErrorMessageObservable;
        this.mResponseData = new MutableLiveData<>();
    }

    public BaseLiveData(MutableLiveData<LifeDataInstance> mResponseData, ErrorMessageObservable mErrorMessageObservable) {
        this.mResponseData = mResponseData;
        this.mErrorMessageObservable = mErrorMessageObservable;
    }

    public void post(LifeDataInstance value) {
        checkError(value);
        mResponseData.postValue(value);
    }

    public void setValue(LifeDataInstance value) {
        checkError(value);
        mResponseData.setValue(value);
    }

    public MutableLiveData<LifeDataInstance> getResponseData() {
        return mResponseData;
    }

    @SuppressLint("CheckResult")
    public void observe(LifecycleOwner owner, OnEventListener listener) {
        mResponseData.observe(owner, listener::onResult);
        mErrorMessageObservable.addObserver((o, arg) -> listener.onErrorMsg((String) arg));
    }

    private void checkError(LifeDataInstance value) {
        if (value.getError() != null) {
            mErrorMessageObservable.updateObservable(value.getError());
        }
    }


    public interface OnEventListener<ResponseType> {
        void onResult(LiveDataResponse<ResponseType> response);

        void onErrorMsg(String errorMsg);
    }

}
