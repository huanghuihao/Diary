package com.huanghh.diary.di.component;

import com.huanghh.diary.di.module.HomeModule;
import com.huanghh.diary.mvp.view.activity.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = HomeModule.class)
public interface HomeComponent {
    void inject(HomeActivity activity);
}
