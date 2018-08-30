package com.huanghh.diary.mvp.view.activity;

import android.content.Intent;
import android.os.SystemClock;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.huanghh.diary.R;
import com.huanghh.diary.base.BaseActivity;
import com.huanghh.diary.base.DiaryApp;
import com.huanghh.diary.di.component.DaggerPasswordComponent;
import com.huanghh.diary.di.module.PasswordModule;
import com.huanghh.diary.interfaces.IDialogClick;
import com.huanghh.diary.mvp.contract.LockContract;
import com.huanghh.diary.mvp.presenter.LockPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class LockActivity extends BaseActivity<LockPresenter> implements LockContract.View, PatternLockViewListener, IDialogClick {
    @BindView(R.id.lv_passWord)
    PatternLockView mLv_pass;
    @BindView(R.id.tv_message)
    TextView mTv_hint;
    @BindString(R.string.lock_error)
    String hintError;
    private String lockStr;

    /**
     * logo点击5次取消调patternLock密码
     */
    final static int COUNTS = 5;//点击次数
    final static long DURATION = 3 * 1000;//规定有效时间
    long[] mHits = new long[COUNTS];

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

    /**
     * 点击5次logo清除密码
     */
    @OnClick(R.id.img_icon_logo)
    public void onViewClicked() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
            mPresenter.cleanPatternLock();
        }
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onProgress(List<PatternLockView.Dot> progressPattern) {

    }

    @Override
    public void onComplete(List<PatternLockView.Dot> pattern) {
        if (lockStr.equals(PatternLockUtils.patternToString(mLv_pass, pattern))) {
            toNextActivity();
        } else {
            mTv_hint.setText(hintError);
        }
        mLv_pass.clearPattern();
    }

    @Override
    public void onCleared() {

    }

    private void toNextActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        this.finish();
    }

    @Override
    public void cleanHint() {
        showConfirmDialog("温馨提示", "已经为您清除手势密码，如需添加请在设置中添加！", this);
    }

    @Override
    public void confirmClickCallback() {
        toNextActivity();
    }
}
