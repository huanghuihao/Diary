package com.huanghh.diary.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static final String BASE_URL = "http://www.wanandroid.com";

    private static ApiService apiService;

    //得到单例apiService
    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (Api.class) {
                if (apiService == null) {
                    new Api();
                }
            }
        }
        return apiService;
    }

    //构造方法创建apiService
    private Api() {
        //打印okHttp请求日志信息
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(loggingInterceptor);

        //实例化retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }
}
