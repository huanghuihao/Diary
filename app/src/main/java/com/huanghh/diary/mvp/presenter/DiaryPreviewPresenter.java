package com.huanghh.diary.mvp.presenter;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.mvp.contract.DiaryPreviewContract;
import com.huanghh.diary.mvp.model.DiaryItem;

public class DiaryPreviewPresenter extends BasePresenterImpl<DiaryPreviewContract.View> implements DiaryPreviewContract.Presenter {
    DaoSession mDao;
    long id;

    public DiaryPreviewPresenter(DiaryPreviewContract.View view, DaoSession daoSession, long id) {
        mView = view;
        mDao = daoSession;
        this.id = id;
    }

    @Override
    public void start() {
        selectDetails();
    }

    @Override
    public void selectDetails() {
        mView.setData(mDao.load(DiaryItem.class, id));
    }
}
