package com.huanghh.diary.mvp.presenter;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.mvp.contract.DiaryInputContract;
import com.huanghh.diary.mvp.model.DiaryItem;

public class DiaryInputPresenter extends BasePresenterImpl<DiaryInputContract.View> implements DiaryInputContract.Presenter {
    private DaoSession mDao;

    public DiaryInputPresenter(DiaryInputContract.View view, DaoSession daoSession) {
        mView = view;
        this.mDao = daoSession;
    }

    @Override
    public void start() {

    }

    @Override
    public void saveToLocal(DiaryItem diary, int type) {
        diary.setLocalType(type);
        switch (type) {
            case 0:
                //本地正常存储
                mDao.insert(diary);
                break;
            case -1:
                //本地暂存
                break;
        }

        mView.saveFinish();
    }
}
