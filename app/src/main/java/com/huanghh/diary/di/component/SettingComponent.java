package com.huanghh.diary.di.component;

import com.huanghh.diary.di.module.SettingModule;
import com.huanghh.diary.mvp.view.fragment.SettingFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = SettingModule.class)
public interface SettingComponent {
    void inject(SettingFragment fragment);
}
