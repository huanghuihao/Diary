package com.huanghh.diary.mvp.presenter;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.dao.WeeDao;
import com.huanghh.diary.mvp.contract.WeeContract;
import com.huanghh.diary.mvp.model.Wee;

import java.util.List;

public class WeePresenter extends BasePresenterImpl<WeeContract.View> implements WeeContract.Presenter {
    private int page, defaultPage;
    //数据库
    private DaoSession mDao;

    public WeePresenter(WeeContract.View view, DaoSession dao) {
        mView = view;
        this.mDao = dao;
        defaultPage = mView.setDefaultPage();
    }

    @Override
    public void start() {
        getRefreshData();
    }

    @Override
    public List<Wee> getRefreshData() {
        if (!isViewAttached()) {
            return null;
        }
        page = defaultPage;
        return getLocalData();
    }

    @Override
    public List<Wee> getLocalData() {
        return mDao.getWeeDao().queryBuilder().orderDesc(WeeDao.Properties.Id).offset(page * 10).limit(10).list();
    }

    @Override
    public List<Wee> getLoadMoreData() {
        page++;
        return getLocalData();
    }
}
