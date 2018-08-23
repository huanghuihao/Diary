package com.huanghh.diary.mvp.contract;

import com.huanghh.diary.mvp.presenter.BasePresenter;
import com.huanghh.diary.mvp.view.BaseView;

public interface SettingLockContract {
    interface View extends BaseView {
        void isHasLock(boolean isHasLock);
    }

    interface Presenter extends BasePresenter {
        void getIsHasLock();

        String getLockStr();

        void setPatternLock(String pattern);
    }
}
