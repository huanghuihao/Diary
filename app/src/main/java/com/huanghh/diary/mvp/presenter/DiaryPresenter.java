package com.huanghh.diary.mvp.presenter;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.mvp.contract.DiaryContract;
import com.huanghh.diary.mvp.model.DiaryItem;

import java.util.List;

public class DiaryPresenter extends BasePresenterImpl<DiaryContract.View> implements DiaryContract.Presenter {
    private int page;
    //数据库
    private DaoSession mDao;

    public DiaryPresenter(DiaryContract.View view, DaoSession dao) {
        mView = view;
        this.mDao = dao;
        page = mView.setDefaultPage();
    }

    @Override
    public void start() {
        getRefreshData();
    }

    @Override
    public List<DiaryItem> getRefreshData() {
        if (!isViewAttached()) {
            return null;
        }
        return getLocalData();
    }

    @Override
    public List<DiaryItem> getLocalData() {
        return mDao.loadAll(DiaryItem.class);
    }

    @Override
    public List<DiaryItem> getLoadMoreData() {
        page++;
        return getLocalData();
    }
}
