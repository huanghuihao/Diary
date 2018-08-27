package com.huanghh.diary.di.component;

import com.huanghh.diary.di.module.PasswordModule;
import com.huanghh.diary.mvp.view.activity.LockActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = PasswordModule.class)
public interface PasswordComponent {
    void inject(LockActivity activity);
}
