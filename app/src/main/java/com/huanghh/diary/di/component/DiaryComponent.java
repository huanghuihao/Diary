package com.huanghh.diary.di.component;

import com.huanghh.diary.di.module.DiaryModule;
import com.huanghh.diary.mvp.view.fragment.DiaryFragment;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;

@Singleton
@Component(modules = DiaryModule.class)
public interface DiaryComponent {
    void inject(DiaryFragment fragment);
}
