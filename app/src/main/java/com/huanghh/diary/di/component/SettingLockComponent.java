package com.huanghh.diary.di.component;

import com.huanghh.diary.di.module.SettingLockModule;
import com.huanghh.diary.mvp.view.activity.SettingLockActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = SettingLockModule.class)
public interface SettingLockComponent {
    void inject(SettingLockActivity activity);
}
