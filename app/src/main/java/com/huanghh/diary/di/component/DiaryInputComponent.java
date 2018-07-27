package com.huanghh.diary.di.component;

import com.huanghh.diary.di.module.DiaryInputModule;
import com.huanghh.diary.mvp.view.activity.DiaryInputActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = DiaryInputModule.class)
public interface DiaryInputComponent {
    void inject(DiaryInputActivity activity);
}
