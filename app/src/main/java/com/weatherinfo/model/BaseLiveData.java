package com.weatherinfo.model;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;

import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class BaseLiveData<ResponseType, LifeDataInstance extends LiveDataResponse<ResponseType>> {

    private MutableLiveData<LifeDataInstance> mResponseData;
    private PublishSubject<String> mSubject;

    public BaseLiveData() {
        this.mResponseData = new MutableLiveData<>();
        mSubject = PublishSubject.create();
    }

    public BaseLiveData(MutableLiveData<LifeDataInstance> mResponseData) {
        this.mResponseData = mResponseData;
        mSubject = PublishSubject.create();
    }

    public BaseLiveData(PublishSubject<String> mSubject) {
        this.mSubject = mSubject;
        this.mResponseData = new MutableLiveData<>();
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

    public void observe(LifecycleOwner owner, OnEventListener listener) {
        mResponseData.observe(owner, listener::onResult);
        Disposable subscribe = mSubject.subscribe(listener::onErrorMsg);
    }


    private void checkError(LifeDataInstance value) {
        if (value.getError() != null) {
            mSubject.onNext(value.getError());
        }
    }


    public interface OnEventListener<ResponseType> {
        void onResult(LiveDataResponse<ResponseType> response);

        void onErrorMsg(String errorMsg);
    }

}
