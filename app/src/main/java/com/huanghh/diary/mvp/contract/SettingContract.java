package com.huanghh.diary.mvp.contract;

import com.huanghh.diary.mvp.presenter.BasePresenter;
import com.huanghh.diary.mvp.view.BaseView;

public interface SettingContract {
    interface View extends BaseView {
        void cleanResult(boolean hasPattern);
    }

    interface Presenter extends BasePresenter {
        void cleanPattern();
    }
}
