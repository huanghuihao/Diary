package com.huanghh.diary.mvp.presenter;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.mvp.contract.PasswordContract;

public class PasswordPresenter extends BasePresenterImpl<PasswordContract.View> {
    private DaoSession mDao;

    public PasswordPresenter(PasswordContract.View view, DaoSession dao) {
        mView = view;
        mDao = dao;
    }

    @Override
    public void start() {

    }
}
