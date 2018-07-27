package com.huanghh.diary.mvp.view.activity;

import android.content.Intent;

import com.huanghh.diary.R;
import com.huanghh.diary.base.BaseActivity;

public class PasswordActivity extends BaseActivity {
    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_password;
    }

    @Override
    protected void init() {
        startActivity(new Intent(this, HomeActivity.class));
        this.finish();
    }

    @Override
    protected void inject() {

    }
}
