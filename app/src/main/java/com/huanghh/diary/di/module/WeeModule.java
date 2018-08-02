package com.huanghh.diary.di.module;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.mvp.contract.WeeContract;
import com.huanghh.diary.mvp.presenter.WeePresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class WeeModule {
    private WeePresenter mPresenter;

    public WeeModule(WeeContract.View view, DaoSession dao) {
        mPresenter = new WeePresenter(view, dao);
    }

    @Singleton
    @Provides
    WeePresenter provideWeePresenter() {
        return mPresenter;
    }
}
