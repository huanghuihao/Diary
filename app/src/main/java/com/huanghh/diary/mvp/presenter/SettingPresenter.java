package com.huanghh.diary.mvp.presenter;

import com.huanghh.diary.base.DiaryApp;
import com.huanghh.diary.mvp.contract.SettingContract;

public class SettingPresenter extends BasePresenterImpl<SettingContract.View> implements SettingContract.Presenter {
    public SettingPresenter(SettingContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
    }

    @Override
    public void cleanPattern() {
        mView.cleanResult(DiaryApp.mSharedPre.getBoolean("isLock"));
    }
}
