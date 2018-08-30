package com.huanghh.diary.mvp.contract;

import com.huanghh.diary.mvp.presenter.BasePresenter;
import com.huanghh.diary.mvp.view.BaseView;

public interface LockContract {
    interface View extends BaseView {
        void cleanHint();
    }

    interface Presenter extends BasePresenter {
        void cleanPatternLock();
    }
}
