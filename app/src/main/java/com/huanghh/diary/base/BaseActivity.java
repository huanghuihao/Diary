package com.huanghh.diary.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huanghh.diary.R;
import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.helper.JsonParser;
import com.huanghh.diary.mvp.presenter.BasePresenter;
import com.huanghh.diary.mvp.view.BaseView;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

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
    @BindView(R.id.tv_left)
    TextView mTvLeft;
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

    /**
     * 讯飞语音
     */
    private SpeechRecognizer mIat;
    private RecognizerDialog mIatDialog;
    private HashMap<String, String> mIatResults = new LinkedHashMap<>();
    private EditText mIatEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentLayoutRes());
        ButterKnife.bind(this);
        if (isLoadEventBus()) EventBus.getDefault().register(this);
        if (isLoadSpeech()) initSpeech();
        mDao = DiaryApp.getDaoSession();
        init();

        inject();
        mLoadingDialog = new ProgressDialog(this);
        mLoadingDialog.setCancelable(false);
    }

    @OnClick({R.id.tv_left, R.id.tv_right, R.id.img_right})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
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
        if (isLoadEventBus()) EventBus.getDefault().unregister(this);
        if (mPresenter != null) mPresenter.detachView();
        mPresenter = null;
    }

    protected abstract int setContentLayoutRes();

    protected abstract void init();

    protected void setTitle(String title) {
        mTvTitle.setText(title);
    }

    protected void leftIsVisibility(int isVisibility) {
        mTvLeft.setVisibility(isVisibility);
    }

    protected void rightIsVisibility(int isVisibility) {
        mTvRight.setVisibility(isVisibility);
    }

    protected void setLeftText(String left) {
        mTvLeft.setText(left);
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

    protected boolean isLoadSpeech() {
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

    protected void showIatResult(EditText editText) {
        mIatEditText = editText;
        mIatDialog.show();
    }

    /**
     * 参数设置
     */
    public void initSpeech() {
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(this, mInitListener);
        // 显示听写对话框
        mIatDialog.setListener(mRecognizerDialogListener);
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
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                showToast("初始化失败，错误码：" + code);
            }
        }
    };

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            if (error.getErrorCode() == 14002) {
                showToast(error.getPlainDescription(true) + "\n请确认是否已开通翻译功能");
            } else {
                showToast(error.getPlainDescription(true));
            }
        }
    };

    private void printResult(RecognizerResult results) {
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

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        mIatEditText.setText(resultBuffer.toString());
        mIatEditText.setSelection(mIatEditText.length());
    }

}
