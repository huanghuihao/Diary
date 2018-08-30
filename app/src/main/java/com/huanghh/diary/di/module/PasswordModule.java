package com.huanghh.diary.di.module;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.mvp.contract.LockContract;
import com.huanghh.diary.mvp.presenter.LockPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PasswordModule {
    private LockPresenter mPresenter;

    public PasswordModule(LockContract.View view, DaoSession dao) {
        mPresenter = new LockPresenter(view, dao);
    }

    @Singleton
    @Provides
    LockPresenter providePasswordPresenter() {
        return mPresenter;
    }
}
