package com.huanghh.diary.mvp.view.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.huanghh.diary.R;
import com.huanghh.diary.base.BaseActivity;
import com.huanghh.diary.di.component.DaggerDiaryInputComponent;
import com.huanghh.diary.di.module.DiaryInputModule;
import com.huanghh.diary.mvp.contract.DiaryInputContract;
import com.huanghh.diary.mvp.model.DiaryItem;
import com.huanghh.diary.mvp.presenter.DiaryInputPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class DiaryInputActivity extends BaseActivity<DiaryInputPresenter> implements DiaryInputContract.View {
    @BindView(R.id.et_title_diary_input)
    EditText mEt_title;
    @BindView(R.id.et_content_diary_input)
    EditText mEt_content;
    @BindView(R.id.tv_weather_value_diary_input)
    TextView mTv_weather;
    @BindView(R.id.tv_location_value_diary_input)
    TextView mTv_location;
    @BindView(R.id.tv_isPublic_value_diary_input)
    TextView mTv_isPublic;
    @BindString(R.string.right_text)
    String right_text;
    DiaryItem mDiary;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_diary_input;
    }

    @Override
    protected void init() {
        leftIsVisibility(View.VISIBLE);
        rightIsVisibility(View.VISIBLE);
        setRightText(right_text);
        setTitle("写日记");
        getIntentData();


    }

    @Override
    protected void inject() {
        DaggerDiaryInputComponent.builder().diaryInputModule(new DiaryInputModule(this, mDao)).build().inject(this);
    }

    @Override
    protected boolean isLoadSpeech() {
        return true;
    }

    @OnClick({R.id.img_voice_diary_input})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_voice_diary_input:
                showIatResult(mEt_content);
                break;
        }
    }

    @Override
    protected void rightClick() {
        saveToLocal();
        EventBus.getDefault().post("diaryRefresh");
    }

    /**
     * 对传入暂存对象进行解析
     */
    private void getIntentData() {
        mDiary = (DiaryItem) getIntent().getSerializableExtra("diary");
        if (mDiary == null) mDiary = new DiaryItem();
    }

    private void saveToLocal() {
        if (checkInput()) mPresenter.saveToLocal(mDiary, 0);
    }

    private boolean checkInput() {
        String title = mEt_title.getText().toString().trim();
        String content = mEt_content.getText().toString().trim();
        if (title.length() == 0) {
            showToast("请输入日记标题!");
            return false;
        }

        if (content.length() == 0) {
            showToast("请输入日记内容!");
            return false;
        }

        String weather = mTv_weather.getText().toString().trim();
        String location = mTv_location.getText().toString().trim();
        String isPublic = mTv_isPublic.getText().toString().trim();
        boolean isPublicB = false;
        if (isPublic.equals("是")) isPublicB = true;

        mDiary.setTitle(title);
        mDiary.setContent(content);
        mDiary.setDate(TimeUtils.getNowString());
        mDiary.setWeather(weather);
        mDiary.setLocation(location);
        mDiary.setIsPublic(isPublicB);
        return true;
    }

    @Override
    public void saveFinish() {
        this.finish();
    }
}
