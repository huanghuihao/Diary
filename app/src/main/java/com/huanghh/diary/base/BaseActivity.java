package com.huanghh.diary.base;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.huanghh.diary.R;
import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.interfaces.IDialogClick;
import com.huanghh.diary.utils.JsonParser;
import com.huanghh.diary.interfaces.ILocation;
import com.huanghh.diary.interfaces.ISpeech;
import com.huanghh.diary.mvp.presenter.BasePresenter;
import com.huanghh.diary.mvp.view.BaseView;
import com.huanghh.diary.widget.CancelDialog;
import com.huanghh.diary.widget.ConfirmDialog;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    @BindView(R.id.img_left)
    ImageView mImgLeft;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.img_right)
    ImageView mImgRight;
    @Inject
    protected P mPresenter;
    //数据库
    protected DaoSession mDao;
    ProgressDialog mLoadingDialog;

    private RecognizerDialog mIatDialog;
    private HashMap<String, String> mIatResults = new LinkedHashMap<>();
    private AMapLocationClient mLocationClient;
    /**
     * 权限
     */
    RxPermissions rxPermissions = new RxPermissions(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentLayoutRes());
        ButterKnife.bind(this);
        if (isLoadEventBus()) EventBus.getDefault().register(this);
        mDao = DiaryApp.getDaoSession();
        init();

        inject();
        mLoadingDialog = new ProgressDialog(this);
        mLoadingDialog.setCancelable(false);
    }

    @OnClick({R.id.img_left, R.id.tv_right, R.id.img_right})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.img_left:
                leftClick();
                break;
            case R.id.tv_right:
                rightClick();
                break;
            case R.id.img_right:
                rightClick();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) mPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //eventBus解绑
        if (isLoadEventBus()) EventBus.getDefault().unregister(this);
        //presenter解绑
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        //定位销毁
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
    }

    protected abstract int setContentLayoutRes();

    protected abstract void init();

    protected void setTitle(String title) {
        mTvTitle.setText(title);
    }

    protected void leftIsVisibility(int isVisibility) {
        mImgLeft.setVisibility(isVisibility);
    }

    protected void rightIsVisibility(int isVisibility) {
        mTvRight.setVisibility(isVisibility);
    }

    protected void setRightText(String right) {
        mTvRight.setText(right);
    }

    protected void leftClick() {
        this.finish();
    }

    protected void setRight(int vis) {
        switch (vis) {
            case 0:
                mTvRight.setVisibility(View.GONE);
                mImgRight.setVisibility(View.GONE);
                break;
            case 1:
                mTvRight.setVisibility(View.VISIBLE);
                mImgRight.setVisibility(View.GONE);
                break;
            case 2:
                mTvRight.setVisibility(View.GONE);
                mImgRight.setVisibility(View.VISIBLE);
                break;
        }
    }

    protected void rightClick() {

    }

    protected abstract void inject();

    protected boolean isLoadEventBus() {
        return false;
    }

    @Override
    public boolean isShowLoading() {
        return true;
    }

    @Override
    public void showLoading() {
        if (isShowLoading()) mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        mLoadingDialog.hide();
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        showToast("其它错误!");
    }

    private CancelDialog mCancelDialog;
    private ConfirmDialog mConfirmDialog;

    protected void showCancelDialog(String title, String content, IDialogClick iDialogClick) {
        if (mCancelDialog == null) {
            mCancelDialog = new CancelDialog(this, title, content, iDialogClick);
        }
        mCancelDialog.show();
    }

    protected void showConfirmDialog(String title, String content, IDialogClick iDialogClick) {
        if (mConfirmDialog == null) {
            mConfirmDialog = new ConfirmDialog(this, title, content, iDialogClick);
        }
        mConfirmDialog.show();
    }

    protected void permissionsGranted(int perCode) {
    }

    protected void permissionsRejected(int perCode) {
    }

    @SuppressLint("CheckResult")
    protected void checkPermissions(int perCode, String... permissions) {
        rxPermissions
                .request(permissions)
                .subscribe(granted -> {
                    if (granted) {
                        permissionsGranted(perCode);
                    } else {
                        permissionsRejected(perCode);
                    }
                });
    }

    private InitListener mInitListener = code -> {
        if (code != ErrorCode.SUCCESS) {
            showToast("初始化失败，错误码：" + code);
        }
    };

    /**
     * 讯飞语音_配置初始化
     */
    public void initSpeechSetting() {
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        //参数设置
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        // 设置听写引擎
        String mEngineType = SpeechConstant.TYPE_CLOUD;
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
        // 设置语言:"zh_cn"中文
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置语言区域:普通话
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
    }

    /**
     * 调用语音读写
     *
     * @param speech 传入语音识别结果接口实现
     */
    protected void speech(ISpeech speech) {
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(this, mInitListener);
        // 显示听写对话框
        mIatDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                printResult(recognizerResult, speech);
            }

            @Override
            public void onError(SpeechError speechError) {
                if (speechError.getErrorCode() == 14002) {
                    showToast(speechError.getPlainDescription(true) + "\n请确认是否已开通翻译功能");
                } else {
                    showToast(speechError.getPlainDescription(true));
                }
            }
        });
        mIatDialog.show();
    }

    /**
     * 讯飞语音_语音解析
     */
    private void printResult(RecognizerResult results, ISpeech speech) {
        String text = JsonParser.parseIatResult(results.getResultString());
        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mIatResults.put(sn, text);
        StringBuilder resultBuffer = new StringBuilder();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        speech.speechCallback(resultBuffer.toString());
    }

    /**
     * 高德定位设置
     */
    protected AMapLocationClientOption initLocationOption() {
        //初始化AMapLocationClientOption对象
        AMapLocationClientOption locationOption = new AMapLocationClientOption();
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        locationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        locationOption.setOnceLocationLatest(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        locationOption.setInterval(1000);
        //设置是否返回地址信息（默认返回地址信息）
        locationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        locationOption.setMockEnable(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        locationOption.setHttpTimeOut(20000);
        //关闭缓存机制
        locationOption.setLocationCacheEnable(false);
        return locationOption;
    }

    /**
     * 高德定位初始化并启动，通过接口回调获取定位信息
     */
    protected void loadLocation(ILocation location) {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(initLocationOption());
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        if (!TextUtils.isEmpty(aMapLocation.getStreet() + aMapLocation.getAoiName())) {
                            location.locationCallback(aMapLocation.getStreet() + " · " + aMapLocation.getAoiName(), aMapLocation.getLongitude() + "," + aMapLocation.getLatitude());
                        } else {
                            location.locationCallback("获取位置信息失败", "");
                        }
                    } else {
                        Log.e("location", aMapLocation.getErrorCode() + "");
                        location.locationCallback("获取位置信息失败", "");
                    }
                }
            }
        });
        //启动定位
        mLocationClient.startLocation();
    }
}
