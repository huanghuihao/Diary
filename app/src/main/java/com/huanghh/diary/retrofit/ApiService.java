package com.huanghh.diary.retrofit;

import com.huanghh.diary.mvp.model.WeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    //和风天气key
    static final String weatherKey = "573178b285d54596b622b860c9867647";

    /**
     * 获取实时天气
     *
     * @param location 经纬度
     * @return 当天天气weather对象
     */
    @GET("/s6/weather/now?key=" + weatherKey)
    Observable<WeatherResponse> getNowWeather(@Query("location") String location);
}
