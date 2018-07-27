package com.huanghh.diary.di.module;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.mvp.contract.DiaryInputContract;
import com.huanghh.diary.mvp.presenter.DiaryInputPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DiaryInputModule {
    private DiaryInputPresenter mPresenter;

    public DiaryInputModule(DiaryInputContract.View view, DaoSession daoSession) {
        mPresenter = new DiaryInputPresenter(view, daoSession);
    }

    @Singleton
    @Provides
    public DiaryInputPresenter provideDiaryInputPresenter() {
        return mPresenter;
    }
}
