package com.huanghh.diary.di.module;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.mvp.contract.DiaryContract;
import com.huanghh.diary.mvp.presenter.DiaryPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DiaryModule {
    private DiaryPresenter mPresenter;

    public DiaryModule(DiaryContract.View view, DaoSession dao) {
        this.mPresenter = new DiaryPresenter(view, dao);
    }

    @Singleton
    @Provides
    DiaryPresenter provideDiaryPresenter() {
        return mPresenter;
    }
}
