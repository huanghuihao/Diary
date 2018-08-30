package com.huanghh.diary.mvp.contract;

import com.huanghh.diary.mvp.model.Wee;
import com.huanghh.diary.mvp.presenter.BasePresenter;
import com.huanghh.diary.mvp.view.BaseView;

import java.util.List;

public interface WeeContract {
    interface View extends BaseView {
        int setDefaultPage();
    }

    interface Presenter extends BasePresenter {
        List<Wee> getRefreshData();

        List<Wee> getLocalData();

        List<Wee> getLoadMoreData();
    }
}
