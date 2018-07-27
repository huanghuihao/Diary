package com.huanghh.diary.mvp.presenter;


import com.huanghh.diary.mvp.view.BaseView;

public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter {
    V mView;

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }

    @Override
    public boolean isViewAttached() {
        return mView != null;
    }
}
