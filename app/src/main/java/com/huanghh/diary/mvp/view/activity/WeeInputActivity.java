package com.huanghh.diary.mvp.view.activity;

import com.huanghh.diary.R;
import com.huanghh.diary.base.BaseActivity;
import com.huanghh.diary.mvp.contract.WeeInputContract;
import com.huanghh.diary.mvp.presenter.WeeInputPresenter;

public class WeeInputActivity extends BaseActivity<WeeInputPresenter> implements WeeInputContract.View {


    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_wee_input;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void inject() {

    }
}
