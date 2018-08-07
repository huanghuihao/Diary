package com.huanghh.diary.mvp.view.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.huanghh.diary.R;
import com.huanghh.diary.base.BaseActivity;
import com.huanghh.diary.di.component.DaggerWeeInputComponent;
import com.huanghh.diary.di.module.WeeInputModule;
import com.huanghh.diary.interfaces.ISpeech;
import com.huanghh.diary.mvp.contract.WeeInputContract;
import com.huanghh.diary.mvp.model.WeeItem;
import com.huanghh.diary.mvp.presenter.WeeInputPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class WeeInputActivity extends BaseActivity<WeeInputPresenter> implements WeeInputContract.View, ISpeech {
    @BindView(R.id.et_content_wee_input)
    EditText mEt_content;
    @BindView(R.id.tv_location_value_wee_input)
    TextView mTv_location;
    @BindView(R.id.tv_isPublic_value_wee_input)
    TextView mTv_isPublic;
    WeeItem mWee;
    @BindString(R.string.right_text)
    String right_text;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_wee_input;
    }

    @Override
    protected void init() {
        leftIsVisibility(View.VISIBLE);
        rightIsVisibility(View.VISIBLE);
        setRightText(right_text);
        setTitle("写点滴");
        getIntentData();
        initSpeechSetting();
    }

    @Override
    protected void inject() {
        DaggerWeeInputComponent.builder().weeInputModule(new WeeInputModule(this, mDao)).build().inject(this);
    }

    /**
     * 对传入暂存对象进行解析
     */
    private void getIntentData() {
        mWee = (WeeItem) getIntent().getSerializableExtra("wee");
        if (mWee == null) mWee = new WeeItem();
    }

    @Override
    protected void rightClick() {
        saveToLocal();
        EventBus.getDefault().post("weeRefresh");
    }

    @OnClick(R.id.img_voice_wee_input)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_voice_wee_input:
                //语音录入
                speech(this);
                break;
        }
    }


    @Override
    public void speechCallback(String speechStr) {
        mEt_content.setText(speechStr);
        mEt_content.setSelection(speechStr.length());
    }

    private void saveToLocal() {
        if (checkInput()) mPresenter.saveToLocal(mWee, 0);
    }

    private boolean checkInput() {
        String content = mEt_content.getText().toString().trim();

        if (content.length() == 0) {
            showToast("请输入点滴内容!");
            return false;
        }

        String location = mTv_location.getText().toString().trim();
        String isPublic = mTv_isPublic.getText().toString().trim();
        boolean isPublicB = false;
        if (isPublic.equals("是")) isPublicB = true;

        mWee.setContent(content);
        mWee.setDate(TimeUtils.getNowString());
        mWee.setLocation(location);
        mWee.setIsPublic(isPublicB);
        return true;
    }

    @Override
    public void saveFinish() {
        this.finish();
    }
}
