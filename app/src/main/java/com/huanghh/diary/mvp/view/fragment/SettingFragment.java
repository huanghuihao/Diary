package com.huanghh.diary.mvp.view.fragment;


import android.content.Intent;
import android.view.View;

import com.huanghh.diary.R;
import com.huanghh.diary.base.BaseFragment;
import com.huanghh.diary.di.component.DaggerSettingComponent;
import com.huanghh.diary.di.module.SettingModule;
import com.huanghh.diary.mvp.contract.SettingContract;
import com.huanghh.diary.mvp.presenter.SettingPresenter;
import com.huanghh.diary.mvp.view.activity.SettingLockActivity;

import butterknife.OnClick;

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

    @OnClick({R.id.rl_lock_setting, R.id.rl_lock_clean_setting, R.id.rl_feedback_setting, R.id.rl_about_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_lock_setting:
                startActivity(new Intent(mParentActivity, SettingLockActivity.class).putExtra("isClean", false));
                break;
            case R.id.rl_lock_clean_setting:
                mPresenter.cleanPattern();
                break;
            case R.id.rl_feedback_setting:
                break;
            case R.id.rl_about_setting:
                break;
        }
    }

    @Override
    public void cleanResult(boolean hasPattern) {
        if (hasPattern) {
            startActivity(new Intent(mParentActivity, SettingLockActivity.class).putExtra("isClean", true));
        } else {
            showToast("您没有手势密码！");
        }
    }
}
