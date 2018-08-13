package com.huanghh.diary.di.module;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.mvp.contract.DiaryPreviewContract;
import com.huanghh.diary.mvp.presenter.DiaryPreviewPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DiaryPreviewModule {
    private DiaryPreviewPresenter mPresenter;

    public DiaryPreviewModule(DiaryPreviewContract.View view, DaoSession daoSession, long id) {
        this.mPresenter = new DiaryPreviewPresenter(view, daoSession, id);
    }

    @Singleton
    @Provides
    DiaryPreviewPresenter provideDiaryPreviewPresenter() {
        return mPresenter;
    }
}
