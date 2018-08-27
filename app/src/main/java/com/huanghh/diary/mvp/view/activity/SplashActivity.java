package com.huanghh.diary.mvp.view.activity;

import android.content.Intent;

import com.huanghh.diary.R;
import com.huanghh.diary.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        startActivity(new Intent(this, LockActivity.class));
        this.finish();
    }

    @Override
    protected void inject() {

    }
}
