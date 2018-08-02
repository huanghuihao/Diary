package com.huanghh.diary.mvp.presenter;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.dao.WeeItemDao;
import com.huanghh.diary.mvp.contract.WeeContract;
import com.huanghh.diary.mvp.model.WeeItem;

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
    public List<WeeItem> getRefreshData() {
        if (!isViewAttached()) {
            return null;
        }
        page = defaultPage;
        return getLocalData();
    }

    @Override
    public List<WeeItem> getLocalData() {
        return mDao.getWeeItemDao().queryBuilder().orderDesc(WeeItemDao.Properties.Id).offset(page * 10).limit(10).list();
    }

    @Override
    public List<WeeItem> getLoadMoreData() {
        page++;
        return getLocalData();
    }
}
