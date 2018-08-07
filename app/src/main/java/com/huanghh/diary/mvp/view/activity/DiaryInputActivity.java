package com.huanghh.diary.mvp.view.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huanghh.diary.R;
import com.huanghh.diary.adapter.DiaryImagesAdapter;
import com.huanghh.diary.base.BaseActivity;
import com.huanghh.diary.di.component.DaggerDiaryInputComponent;
import com.huanghh.diary.di.module.DiaryInputModule;
import com.huanghh.diary.interfaces.ILocation;
import com.huanghh.diary.interfaces.ISpeech;
import com.huanghh.diary.mvp.contract.DiaryInputContract;
import com.huanghh.diary.mvp.model.DiaryItem;
import com.huanghh.diary.mvp.presenter.DiaryInputPresenter;
import com.huanghh.diary.widget.GifSizeFilter;
import com.huanghh.diary.widget.Glide4Engine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class DiaryInputActivity extends BaseActivity<DiaryInputPresenter> implements DiaryInputContract.View, BaseQuickAdapter.OnItemClickListener, ILocation, ISpeech {
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
    @BindView(R.id.rv_imgs_diary_input)
    RecyclerView mRvPics;
    DiaryImagesAdapter mImgAdapter;
    List<String> mListImgs = new ArrayList<>();
    @BindString(R.string.right_text)
    String right_text;
    public static int REQUEST_CODE_CHOOSE = 0x00001;
    DiaryItem mDiary;
    private int mPhotoSize = 9;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_diary_input;
    }

    @Override
    protected void init() {
        initTitle();
        initPicView();
        initLocation();
        initSpeech();
        getIntentData();
    }

    /**
     * 初始化title标题
     */
    private void initTitle() {
        leftIsVisibility(View.VISIBLE);
        rightIsVisibility(View.VISIBLE);
        setRightText(right_text);
        setTitle("写日记");
    }

    /**
     * 初始化图片选择控件
     */
    private void initPicView() {
        mImgAdapter = new DiaryImagesAdapter(R.layout.layout_img_item, mListImgs, this);
        mImgAdapter.setOnItemClickListener(this);
        mRvPics.setLayoutManager(new GridLayoutManager(this, 4));
        mRvPics.setAdapter(mImgAdapter);
    }

    /**
     * 初始化定位模块
     */
    private void initLocation() {
        initLocationOption();
        loadLocation(this);
    }

    /**
     * 初始化语音模块
     */
    private void initSpeech() {
        initSpeechSetting();
    }

    /**
     * 对传入暂存对象进行解析
     */
    private void getIntentData() {
        mDiary = (DiaryItem) getIntent().getSerializableExtra("diary");
        if (mDiary == null) mDiary = new DiaryItem();
        mListImgs.add("empty");
    }

    @Override
    protected void inject() {
        DaggerDiaryInputComponent.builder().diaryInputModule(new DiaryInputModule(this, mDao)).build().inject(this);
    }

    @OnClick({R.id.img_voice_diary_input})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_voice_diary_input:
                //语音录入
                speech(this);
                break;
        }
    }

    @Override
    protected void rightClick() {
        saveToLocal();
        EventBus.getDefault().post("diaryRefresh");
    }

    /**
     * 右上角点击触发本地保存
     */
    private void saveToLocal() {
        if (checkInput()) mPresenter.saveToLocal(mDiary, 0);
    }

    /**
     * 输入表单校验
     *
     * @return 校验是否成功，如果成功封装本地存储对象
     */
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

    /**
     * 点击右上角保存完毕回调
     */
    @Override
    public void saveFinish() {
        this.finish();
    }

    /**
     * 调用图片选择器Matisse
     */
    private void chosePhoto() {
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .theme(R.style.Matisse_Zhihu)
                .countable(false)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .maxSelectable(mPhotoSize)
                .originalEnable(true)
                .maxOriginalSize(10)
                .imageEngine(new Glide4Engine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    /**
     * 图片选择器预览recyclerView点击事件
     *
     * @param position 数据源中点击的item的position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mListImgs.get(position).equals("empty")) {
            checkPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {

        }
    }

    /**
     * rxPermissions权限获取成功
     */
    @Override
    protected void permissionsGranted() {
        super.permissionsGranted();
        chosePhoto();
    }

    /**
     * rxPermissions权限获取失败
     */
    @Override
    protected void permissionsRejected() {
        super.permissionsRejected();
    }

    /**
     * 接收知乎图片选择器matisse回调结果
     *
     * @param requestCode 请求图片选择预览时的 code
     * @param data        返回选择图片集合意图
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mPhotoSize = mImgAdapter.refreshPicData(Matisse.obtainPathResult(data));
        }
    }

    /**
     * 定位信息回调
     *
     * @param location 返回定位信息 街道+aoiName
     */
    @Override
    public void locationCallback(String location) {
        mTv_location.setText(location);
    }

    /**
     * 语音识别回调
     *
     * @param speechStr 语音识别解析后的str
     */
    @Override
    public void speechCallback(String speechStr) {
        mEt_content.setText(speechStr);
        mEt_content.setSelection(speechStr.length());
    }
}
