package com.huanghh.diary.mvp.contract;

import com.huanghh.diary.mvp.model.DiaryItem;
import com.huanghh.diary.mvp.presenter.BasePresenter;
import com.huanghh.diary.mvp.view.BaseView;

public interface DiaryDetailContract {
    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter {
        DiaryItem selectDetails();
    }
}
