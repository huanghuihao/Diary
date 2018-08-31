package com.huanghh.diary.interfaces;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class JsonResultResponse<T> implements Observer<T> {
    private IJsonResultListener<T> listener;

    public JsonResultResponse(IJsonResultListener<T> resultListener) {
        listener = resultListener;
    }

    @Override
    public void onSubscribe(Disposable d) {
        listener.start();
    }

    @Override
    public void onNext(T t) {
        listener.onComplete(t);
    }

    @Override
    public void onError(Throwable e) {
        listener.onError(e);
        listener.finish();
    }

    @Override
    public void onComplete() {
        listener.finish();
    }
}
