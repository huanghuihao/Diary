package com.huanghh.diary.interfaces;

public interface IJsonResultListener<T> {
    void start();
    void onComplete(T t);
    void finish();
    void onError(Throwable e);
}
