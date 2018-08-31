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
import com.huanghh.diary.interfaces.IDialogClick;
import com.huanghh.diary.mvp.contract.SettingLockContract;
import com.huanghh.diary.mvp.presenter.SettingLockPresenter;
import com.huanghh.diary.widget.ConfirmDialog;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

public class SettingLockActivity extends BaseActivity<SettingLockPresenter> implements SettingLockContract.View, PatternLockViewListener, IDialogClick {
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
    private boolean isClean = false;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_setting_lock;
    }

    @Override
    protected void init() {
        initTitle();
        mLockView.addPatternLockListener(this);
        getIntentData();
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

    private void getIntentData() {
        isClean = getIntent().getBooleanExtra("isClean", false);
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
    public void onComplete(List<PatternLockView.Dot> pattern) {
        String temp = PatternLockUtils.patternToString(mLockView, pattern);
        checkPatternLock(pattern, temp);
    }

    private String mLockTemp;

    /**
     * PatternLockView监听器
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
                if (isClean) {
                    mPresenter.cleanPattern();
                    showConfirmDialog("温馨提示", "手势密码已经清除！", this);
                } else {
                    mTv_hint.setText(hint_first);
                    mIsHasLock = false;
                }
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
                showConfirmDialog("温馨提示", "由于本应用是单机版本，暂无服务器，" +
                        "如遇忘记手势密码情况，请在输入手势密码界面点击logo：5次可取消手势密码！", this);
            } else {
                mTv_hint.setText(hint_change);
            }
        }
    }

    /**
     * dialog 点击确定 接口回调
     */
    @Override
    public void confirmClickCallback() {
        this.finish();
    }

    /**
     * PatternLockView 监听器
     */
    @Override
    public void onCleared() {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onProgress(List<PatternLockView.Dot> progressPattern) {

    }
}
