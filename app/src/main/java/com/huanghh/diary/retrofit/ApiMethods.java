package com.huanghh.diary.retrofit;


import com.huanghh.diary.mvp.model.WeatherResponse;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApiMethods {
    /**
     * 封装线程管理和订阅的过程
     */
    private static void ApiSubscribe(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void getNowWeather(String location,Observer<WeatherResponse> observer) {
        ApiSubscribe(Api.getApiService().getNowWeather(location), observer);
    }
}
