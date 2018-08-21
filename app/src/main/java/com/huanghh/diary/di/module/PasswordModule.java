package com.huanghh.diary.di.module;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.mvp.contract.PasswordContract;
import com.huanghh.diary.mvp.presenter.PasswordPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PasswordModule {
    private PasswordPresenter mPresenter;

    public PasswordModule(PasswordContract.View view, DaoSession dao) {
        mPresenter = new PasswordPresenter(view, dao);
    }

    @Singleton
    @Provides
    PasswordPresenter providePasswordPresenter() {
        return mPresenter;
    }
}
