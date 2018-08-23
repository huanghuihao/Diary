package com.huanghh.diary.di.module;

import com.huanghh.diary.mvp.contract.SettingLockContract;
import com.huanghh.diary.mvp.presenter.SettingLockPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingLockModule {
    private SettingLockPresenter mPresenter;

    public SettingLockModule(SettingLockContract.View view) {
        mPresenter = new SettingLockPresenter(view);
    }

    @Singleton
    @Provides
    SettingLockPresenter providesSettingPassPresenter() {
        return mPresenter;
    }
}
