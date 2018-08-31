package com.huanghh.diary.mvp.presenter;

import com.huanghh.diary.base.DiaryApp;
import com.huanghh.diary.mvp.contract.SettingLockContract;

public class SettingLockPresenter extends BasePresenterImpl<SettingLockContract.View> implements SettingLockContract.Presenter {

    public SettingLockPresenter(SettingLockContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        getIsHasLock();
    }

    @Override
    public void getIsHasLock() {
        if (DiaryApp.mSharedPre.getBoolean("isLock")) {
            mView.isHasLock(true);
        } else {
            mView.isHasLock(false);
        }
    }

    @Override
    public String getLockStr() {
        return DiaryApp.mSharedPre.getString("lockStr");
    }

    @Override
    public void setPatternLock(String pattern) {
        DiaryApp.mSharedPre.putBoolean("isLock", true);
        DiaryApp.mSharedPre.putString("lockStr", pattern);
    }

    @Override
    public void cleanPattern() {
        DiaryApp.mSharedPre.putBoolean("isLock", false);
        DiaryApp.mSharedPre.putString("lockStr", "");
    }
}
