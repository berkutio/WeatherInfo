package com.weatherinfo.model.baselivedata;

import java.util.Observable;

public class ErrorMessageObservable extends Observable {

    public void updateObservable(String msg){
        setChanged();
        notifyObservers(msg);
    }

}
