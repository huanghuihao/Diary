package com.huanghh.diary.mvp.contract;

import com.huanghh.diary.mvp.model.Diary;
import com.huanghh.diary.mvp.presenter.BasePresenter;
import com.huanghh.diary.mvp.view.BaseView;

public interface DiaryPreviewContract {
    interface View extends BaseView {
        void setData(Diary diary);
    }

    interface Presenter extends BasePresenter {
        void selectDetails();
    }
}
