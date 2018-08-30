package com.huanghh.diary.mvp.presenter;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.mvp.contract.WeeInputContract;
import com.huanghh.diary.mvp.model.Wee;

public class WeeInputPresenter extends BasePresenterImpl<WeeInputContract.View> implements WeeInputContract.Presenter {
    private DaoSession mDao;

    public WeeInputPresenter(WeeInputContract.View view, DaoSession daoSession) {
        mView = view;
        this.mDao = daoSession;
    }

    @Override
    public void start() {

    }

    @Override
    public void saveToLocal(Wee wee, int type) {
        wee.setLocalType(type);
        switch (type) {
            case 0:
                //本地正常存储
                mDao.insert(wee);
                break;
            case -1:
                //本地暂存
                break;
        }

        mView.saveFinish();
    }
}
