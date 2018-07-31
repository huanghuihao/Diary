package com.huanghh.diary.di.module;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.mvp.contract.WeeInputContract;
import com.huanghh.diary.mvp.presenter.WeeInputPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class WeeInputModule {
    WeeInputPresenter mPresenter;

    public WeeInputModule(WeeInputContract.View view, DaoSession daoSession) {
        mPresenter = new WeeInputPresenter(view, daoSession);
    }

    @Singleton
    @Provides
    WeeInputPresenter provideWeeInputPresenter() {
        return mPresenter;
    }
}
