package com.huanghh.diary.mvp.view.activity;

import com.huanghh.diary.R;
import com.huanghh.diary.base.BaseActivity;
import com.huanghh.diary.di.component.DaggerDiaryInputComponent;
import com.huanghh.diary.di.module.DiaryInputModule;
import com.huanghh.diary.mvp.contract.DiaryInputContract;
import com.huanghh.diary.mvp.presenter.DiaryInputPresenter;

public class DiaryInputActivity extends BaseActivity<DiaryInputPresenter> implements DiaryInputContract.View {
    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_diary_input;
    }

    @Override
    protected void init() {
    }

    @Override
    protected void inject() {
        DaggerDiaryInputComponent.builder().diaryInputModule(new DiaryInputModule(this, mDao));
    }
}
