package com.huanghh.diary.di.component;

import com.huanghh.diary.di.module.WeeInputModule;
import com.huanghh.diary.mvp.view.activity.WeeInputActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = WeeInputModule.class)
public interface WeeInputComponent {
    void inject(WeeInputActivity activity);
}
