package com.huanghh.diary.mvp.contract;

import com.huanghh.diary.mvp.model.Diary;
import com.huanghh.diary.mvp.presenter.BasePresenter;
import com.huanghh.diary.mvp.view.BaseView;

import java.util.List;

public interface DiaryContract {

    //view需要得到数据list，填充到适配器中
    interface View extends BaseView {
        int setDefaultPage();
    }

    //presenter分页，需要入参页码
    interface Presenter extends BasePresenter {
        List<Diary> getRefreshData();

        List<Diary> getLocalData();

        List<Diary> getLoadMoreData();
    }
}
