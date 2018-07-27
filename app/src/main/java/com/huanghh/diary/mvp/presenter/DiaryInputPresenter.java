package com.huanghh.diary.mvp.presenter;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.mvp.contract.DiaryInputContract;

public class DiaryInputPresenter extends BasePresenterImpl<DiaryInputContract.View> implements DiaryInputContract.Presenter {
    private DaoSession mDao;

    public DiaryInputPresenter(DiaryInputContract.View view, DaoSession daoSession) {
        mView = view;
        this.mDao = daoSession;
    }

    @Override
    public void start() {

    }
}
