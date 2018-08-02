package com.huanghh.diary.di.component;

import com.huanghh.diary.di.module.WeeModule;
import com.huanghh.diary.mvp.view.fragment.WeeFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = WeeModule.class)
public interface WeeComponent {
    void inject(WeeFragment fragment);
}
