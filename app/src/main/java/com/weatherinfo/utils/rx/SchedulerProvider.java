package com.weatherinfo.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by user on 15.05.17.
 */

public interface SchedulerProvider {

    Scheduler mainThread();

    Scheduler computation();

    Scheduler io();

}
