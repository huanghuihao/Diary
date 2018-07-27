package com.huanghh.diary.di.module;

import com.huanghh.diary.mvp.contract.HomeContract;
import com.huanghh.diary.mvp.presenter.HomePresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {
    private HomePresenter mPresenter;

    public HomeModule(HomeContract.View view) {
        mPresenter = new HomePresenter();
    }

    @Singleton
    @Provides
    HomePresenter provideHomePresenter() {
        return mPresenter;
    }
}
