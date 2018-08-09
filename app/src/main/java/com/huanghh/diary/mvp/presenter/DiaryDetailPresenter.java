package com.huanghh.diary.mvp.presenter;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.mvp.contract.DiaryDetailContract;
import com.huanghh.diary.mvp.model.DiaryItem;

public class DiaryDetailPresenter extends BasePresenterImpl<DiaryDetailContract.View> implements DiaryDetailContract.Presenter {
    DaoSession mDao;
    long id;

    public DiaryDetailPresenter(DiaryDetailContract.View view, DaoSession daoSession, long id) {
        mView = view;
        mDao = daoSession;
        this.id = id;
    }

    @Override
    public void start() {
        selectDetails();
    }

    @Override
    public DiaryItem selectDetails() {
        return mDao.load(DiaryItem.class, id);
    }
}
