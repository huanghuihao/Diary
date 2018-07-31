package com.huanghh.diary.mvp.presenter;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.mvp.contract.WeeInputContract;

public class WeeInputPresenter extends BasePresenterImpl<WeeInputContract.View> implements WeeInputContract.Presenter {
    private DaoSession mDao;

    public WeeInputPresenter(WeeInputContract.View view, DaoSession daoSession) {
        mView = view;
        mDao = daoSession;
    }

    @Override
    public void start() {

    }
}
