package com.huanghh.diary.mvp.view.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.huanghh.diary.R;
import com.huanghh.diary.base.BaseActivity;
import com.huanghh.diary.di.component.DaggerSettingLockComponent;
import com.huanghh.diary.di.module.SettingLockModule;
import com.huanghh.diary.mvp.contract.SettingLockContract;
import com.huanghh.diary.mvp.presenter.SettingLockPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

public class SettingLockActivity extends BaseActivity<SettingLockPresenter> implements SettingLockContract.View, PatternLockViewListener {
    @BindView(R.id.tv_hint_settingLock)
    TextView mTv_hint;
    @BindView(R.id.pLockView_settingLock)
    PatternLockView mLockView;
    @BindString(R.string.msg_set_lock_first)
    String hint_first;
    @BindString(R.string.msg_set_lock_second)
    String hint_second;
    @BindString(R.string.msg_set_lock_old)
    String hint_old;
    @BindString(R.string.msg_set_lock_change)
    String hint_change;
    @BindString(R.string.msg_set_lock_old_error)
    String hint_old_error;
    boolean mIsHasLock;
    String mPatternLockStr;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_setting_lock;
    }

    @Override
    protected void init() {
        initTitle();
        mLockView.addPatternLockListener(this);
    }

    @Override
    protected void inject() {
        DaggerSettingLockComponent.builder().settingLockModule(new SettingLockModule(this)).build().inject(this);
    }

    /**
     * 初始化title标题
     */
    private void initTitle() {
        leftIsVisibility(View.VISIBLE);
        rightIsVisibility(View.INVISIBLE);
        setTitle("设置手势密码");
    }

    @Override
    public void isHasLock(boolean isHasLock) {
        mIsHasLock = isHasLock;
        if (isHasLock) {
            mTv_hint.setText(hint_old);
            mPatternLockStr = mPresenter.getLockStr();
        } else {
            mTv_hint.setText(hint_first);
            mPatternLockStr = "";
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
        String temp = PatternLockUtils.patternToString(mLockView, pattern);

        checkPatternLock(pattern, temp);
    }

    @Override
    public void onCleared() {

    }

    private String mLockTemp;

    /**
     * 根据是否存在手势密码，增加校验检查逻辑
     * true : 先确认原手势是否正确，正确添加新密码方法
     * false: 正确添加新密码方法
     *
     * @param pattern 手势结果list dot
     * @param temp    手势结果转String 临时变量
     */
    private void checkPatternLock(List<PatternLockView.Dot> pattern, String temp) {
        if (mIsHasLock) {
            //有手势密码场景
            if (mPatternLockStr.equals(temp)) {
                mTv_hint.setText(hint_first);
                addNewPattern(temp);
            } else {
                mTv_hint.setText(hint_old_error);
            }
        } else {
            addNewPattern(temp);
        }
        mLockView.clearPattern();
    }

    /**
     * 添加新密码方法
     *
     * @param temp 手势结果转String
     */
    private void addNewPattern(String temp) {
        //没有手势密码场景
        if (TextUtils.isEmpty(mLockTemp)) {
            //首次获取手势
            mLockTemp = temp;
            mTv_hint.setText(hint_second);
        } else {
            //二次获取手势
            if (mLockTemp.equals(temp)) {
                mPresenter.setPatternLock(temp);
                showToast("添加密码成功");
            } else {
                mTv_hint.setText(hint_change);
            }
        }
    }

}
