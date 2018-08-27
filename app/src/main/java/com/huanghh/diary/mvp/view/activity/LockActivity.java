package com.huanghh.diary.mvp.view.activity;

import android.content.Intent;
import android.util.Log;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.huanghh.diary.R;
import com.huanghh.diary.base.BaseActivity;
import com.huanghh.diary.base.DiaryApp;
import com.huanghh.diary.di.component.DaggerPasswordComponent;
import com.huanghh.diary.di.module.PasswordModule;
import com.huanghh.diary.mvp.contract.PasswordContract;
import com.huanghh.diary.mvp.presenter.PasswordPresenter;

import java.util.List;

import butterknife.BindView;

public class LockActivity extends BaseActivity<PasswordPresenter> implements PasswordContract.View, PatternLockViewListener {
    @BindView(R.id.lv_passWord)
    PatternLockView mLv_pass;
    private String lockStr;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_lock;
    }

    @Override
    protected void init() {
        if (!DiaryApp.mSharedPre.getBoolean("isLock")) {
            toNextActivity();
            return;
        }

        lockStr = DiaryApp.mSharedPre.getString("lockStr");
        mLv_pass.addPatternLockListener(this);
    }

    @Override
    protected void inject() {
        DaggerPasswordComponent.builder().passwordModule(new PasswordModule(this, mDao)).build().inject(this);
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onProgress(List<PatternLockView.Dot> progressPattern) {

    }

    @Override
    public void onComplete(List<PatternLockView.Dot> pattern) {
        if (lockStr.equals(PatternLockUtils.patternToString(mLv_pass, pattern))) toNextActivity();
    }

    @Override
    public void onCleared() {

    }

    private void toNextActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        this.finish();
    }

    // TODO: 2018/8/27  因为没有和服务器，所以这个界面如果忘记手势密码，需要添加icon的点击进入主界面
}
