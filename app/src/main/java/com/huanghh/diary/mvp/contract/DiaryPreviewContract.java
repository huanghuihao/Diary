package com.huanghh.diary.mvp.contract;

import com.huanghh.diary.mvp.model.DiaryItem;
import com.huanghh.diary.mvp.presenter.BasePresenter;
import com.huanghh.diary.mvp.view.BaseView;

public interface DiaryPreviewContract {
    interface View extends BaseView {
        void setData(DiaryItem diary);
    }

    interface Presenter extends BasePresenter {
        void selectDetails();
    }
}
