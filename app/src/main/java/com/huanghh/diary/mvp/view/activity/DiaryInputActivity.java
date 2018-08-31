package com.huanghh.diary.mvp.view.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huanghh.diary.R;
import com.huanghh.diary.adapter.DiaryImagesAdapter;
import com.huanghh.diary.base.BaseActivity;
import com.huanghh.diary.di.component.DaggerDiaryInputComponent;
import com.huanghh.diary.di.module.DiaryInputModule;
import com.huanghh.diary.interfaces.ILocation;
import com.huanghh.diary.interfaces.ISpeech;
import com.huanghh.diary.mvp.contract.DiaryInputContract;
import com.huanghh.diary.mvp.model.Diary;
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
    List<String> mPics = new ArrayList<>();
    @BindString(R.string.right_text)
    String right_text;
    private static final int PERMISSION_INIT = 0x001;
    private static final int PERMISSION_PHOTO = 0x002;
    private static final int PERMISSION_SPEECH = 0x003;

    private static final int REQUEST_CODE_CHOOSE = 0x00001;
    Diary mDiary;
    private int mPhotoSize = 9;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_diary_input;
    }

    @Override
    protected void init() {
        initPermissions();
        initTitle();
        initPicView();
        initSpeech();
        getIntentData();
    }

    private void initPermissions() {
        checkPermissions(PERMISSION_INIT, Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION);
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
        mImgAdapter = new DiaryImagesAdapter(R.layout.layout_img_item, mPics, this);
        mImgAdapter.setOnItemClickListener(this);
        mRvPics.setLayoutManager(new GridLayoutManager(this, 4));
        mRvPics.setAdapter(mImgAdapter);
    }

    /**
     * 初始化定位模块，需要在权限(成功、失败回调后都调定位，结果可以直接更新到view)申请后触发定位
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
        mDiary = (Diary) getIntent().getSerializableExtra("diary");
        if (mDiary == null) mDiary = new Diary();
        mPics.add("empty");
    }

    @Override
    protected void inject() {
        DaggerDiaryInputComponent.builder().diaryInputModule(new DiaryInputModule(this, mDao))
                .build().inject(this);
    }

    @OnClick({R.id.img_voice_diary_input})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_voice_diary_input:
                checkPermissions(PERMISSION_SPEECH, Manifest.permission.RECORD_AUDIO);
                break;
        }
    }

    @Override
    protected void rightClick() {
        save();
        EventBus.getDefault().post("diaryRefresh");
    }

    /**
     * 右上角点击触发本地保存
     */
    private void save() {
        String title = getViewValue(mEt_title);
        String content = getViewValue(mEt_content);
        String weather = getViewValue(mTv_weather);
        String location = getViewValue(mTv_location);
        String isPublic = getViewValue(mTv_isPublic);
        boolean isPublicB = false;
        if (isPublic.equals("是")) isPublicB = true;
        mPresenter.save(mDiary, title, content, weather, location, mPics, isPublicB, 0);
    }

    private String getViewValue(View view) {
        if (view instanceof TextView) return ((TextView) view).getText().toString().trim();
        return "";
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
        if (mPics.get(position).equals("empty")) {
            checkPermissions(PERMISSION_PHOTO, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {

        }
    }

    /**
     * rxPermissions权限获取成功
     */
    @Override
    protected void permissionsGranted(int perCode) {
        switch (perCode) {
            case PERMISSION_INIT:
                initLocation();
                break;
            case PERMISSION_PHOTO:
                chosePhoto();
                break;
            case PERMISSION_SPEECH:
                speech(this);
                break;
        }
    }

    /**
     * rxPermissions权限获取失败
     */
    @Override
    protected void permissionsRejected(int perCode) {
        if (perCode == PERMISSION_INIT) {
            initLocation();
        }
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
    public void locationCallback(String location, String latLon) {
        mTv_location.setText(location);
        mPresenter.getWeatherWithLocation(latLon);
    }

    @Override
    public void getWeatherResult(String weather) {
        mTv_weather.setText(weather);
    }

    @Override
    public void getWeatherError(Throwable e) {
        mTv_weather.setText("获取天气失败");
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
