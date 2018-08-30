package com.huanghh.diary.mvp.presenter;

import com.huanghh.diary.base.DiaryApp;
import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.mvp.contract.LockContract;

public class LockPresenter extends BasePresenterImpl<LockContract.View> implements LockContract.Presenter {
    private DaoSession mDao;

    public LockPresenter(LockContract.View view, DaoSession dao) {
        mView = view;
        mDao = dao;
    }

    @Override
    public void start() {

    }

    @Override
    public void cleanPatternLock() {
        DiaryApp.mSharedPre.putBoolean("isLock", false);
        DiaryApp.mSharedPre.putString("lockStr", "");
        mView.cleanHint();
    }
}
