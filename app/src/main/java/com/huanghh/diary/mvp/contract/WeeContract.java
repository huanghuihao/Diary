package com.huanghh.diary.mvp.contract;

import com.huanghh.diary.mvp.model.WeeItem;
import com.huanghh.diary.mvp.presenter.BasePresenter;
import com.huanghh.diary.mvp.view.BaseView;

import java.util.List;

public interface WeeContract {
    interface View extends BaseView {
        int setDefaultPage();
    }

    interface Presenter extends BasePresenter {
        List<WeeItem> getRefreshData();

        List<WeeItem> getLocalData();

        List<WeeItem> getLoadMoreData();
    }
}
