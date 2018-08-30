package com.huanghh.diary.mvp.presenter;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.dao.DiaryDao;
import com.huanghh.diary.mvp.contract.DiaryContract;
import com.huanghh.diary.mvp.model.Diary;

import java.util.List;

public class DiaryPresenter extends BasePresenterImpl<DiaryContract.View> implements DiaryContract.Presenter {
    private int page, defaultPage;
    //数据库
    private DaoSession mDao;

    public DiaryPresenter(DiaryContract.View view, DaoSession dao) {
        mView = view;
        this.mDao = dao;
        defaultPage = mView.setDefaultPage();
    }

    @Override
    public void start() {
        getRefreshData();
    }

    @Override
    public List<Diary> getRefreshData() {
        if (!isViewAttached()) {
            return null;
        }
        page = defaultPage;
        return getLocalData();
    }

    @Override
    public List<Diary> getLocalData() {
        return mDao.getDiaryDao().queryBuilder().orderDesc(DiaryDao.Properties.Id).offset(page * 10).limit(10).list();
    }

    @Override
    public List<Diary> getLoadMoreData() {
        page++;
        return getLocalData();
    }
}
