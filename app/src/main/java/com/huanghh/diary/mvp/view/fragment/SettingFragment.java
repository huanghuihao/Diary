package com.huanghh.diary.mvp.view.fragment;


import com.huanghh.diary.R;
import com.huanghh.diary.base.BaseFragment;
import com.huanghh.diary.di.component.DaggerSettingComponent;
import com.huanghh.diary.di.module.SettingModule;
import com.huanghh.diary.mvp.contract.SettingContract;
import com.huanghh.diary.mvp.presenter.SettingPresenter;

public class SettingFragment extends BaseFragment<SettingPresenter> implements SettingContract.View {

    public SettingFragment() {
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void inject() {
        DaggerSettingComponent.builder().settingModule(new SettingModule(this)).build().inject(this);
    }
}
