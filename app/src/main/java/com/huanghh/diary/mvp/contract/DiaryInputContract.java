package com.huanghh.diary.mvp.contract;

import com.huanghh.diary.mvp.model.DiaryItem;
import com.huanghh.diary.mvp.presenter.BasePresenter;
import com.huanghh.diary.mvp.view.BaseView;

public interface DiaryInputContract {
    interface View extends BaseView {
        void saveFinish();
    }

    interface Presenter extends BasePresenter {
        void saveToLocal(DiaryItem diary, int type);
    }
}
