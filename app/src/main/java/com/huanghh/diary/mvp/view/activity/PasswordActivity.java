package com.huanghh.diary.mvp.view.activity;

import android.content.Intent;
import android.util.Log;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.huanghh.diary.R;
import com.huanghh.diary.base.BaseActivity;
import com.huanghh.diary.base.DiaryApp;
import com.huanghh.diary.di.component.DaggerPasswordComponent;
import com.huanghh.diary.di.module.PasswordModule;
import com.huanghh.diary.mvp.contract.PasswordContract;
import com.huanghh.diary.mvp.presenter.PasswordPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PasswordActivity extends BaseActivity<PasswordPresenter> implements PasswordContract.View, PatternLockViewListener {
    @BindView(R.id.lv_passWord)
    PatternLockView mLv_pass;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_password;
    }

    @Override
    protected void init() {
        if (!DiaryApp.mSharedPre.getBoolean("isLock")) {
            startActivity(new Intent(this, HomeActivity.class));
            this.finish();
        }
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
        StringBuilder sb = new StringBuilder();
        for (PatternLockView.Dot dot : progressPattern) {
            String temp = dot.toString();
            sb.append(temp);
        }

        String result;
        if (sb.length() > 0) {
            result = sb.toString().substring(0, sb.length() - 1);
        } else {
            result = "";
        }
        Log.e("onProgress", result);
    }

    @Override
    public void onComplete(List<PatternLockView.Dot> pattern) {
        StringBuilder sb = new StringBuilder();
        for (PatternLockView.Dot dot : pattern) {
            String temp = dot.getColumn() + "," + dot.getRow();
            sb.append(temp);
        }

        String result;
        if (sb.length() > 0) {
            result = sb.toString().substring(0, sb.length() - 1);
        } else {
            result = "";
        }
        Log.e("onComplete", result);
    }

    @Override
    public void onCleared() {
        Log.e("onCreate", "1");
    }
}
