package com.huanghh.diary.di.module;

import com.huanghh.diary.mvp.contract.SettingContract;
import com.huanghh.diary.mvp.presenter.SettingPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingModule {
    private SettingPresenter mPresenter;

    public SettingModule(SettingContract.View view) {
        mPresenter = new SettingPresenter();
    }

    @Singleton
    @Provides
    SettingPresenter provideSettingPresenter() {
        return mPresenter;
    }
}
