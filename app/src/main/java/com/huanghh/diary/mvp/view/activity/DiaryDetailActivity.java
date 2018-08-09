package com.huanghh.diary.mvp.view.activity;

import com.huanghh.diary.R;
import com.huanghh.diary.base.BaseActivity;
import com.huanghh.diary.mvp.contract.DiaryDetailContract;
import com.huanghh.diary.mvp.presenter.DiaryDetailPresenter;

public class DiaryDetailActivity extends BaseActivity<DiaryDetailPresenter> implements DiaryDetailContract.View {

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_diary_detail;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void inject() {

    }
}
