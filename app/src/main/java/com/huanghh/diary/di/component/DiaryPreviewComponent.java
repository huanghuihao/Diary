package com.huanghh.diary.di.component;

import com.huanghh.diary.di.module.DiaryPreviewModule;
import com.huanghh.diary.mvp.view.activity.DiaryPreviewActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = DiaryPreviewModule.class)
public interface DiaryPreviewComponent {
    void inject(DiaryPreviewActivity activity);
}
