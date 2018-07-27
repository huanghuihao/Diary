package com.huanghh.diary.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huanghh.diary.R;
import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.mvp.presenter.BasePresenter;
import com.huanghh.diary.mvp.view.BaseView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    @BindView(R.id.tv_left)
    TextView mTvLeft;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @Inject
    P mPresenter;
    //数据库
    protected DaoSession mDao;
    ProgressDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentLayoutRes());
        ButterKnife.bind(this);
        mDao = DiaryApp.getDaoSession();
        init();

        inject();
        mLoadingDialog = new ProgressDialog(this);
        mLoadingDialog.setCancelable(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) mPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) mPresenter.detachView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter = null;
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

    protected abstract void inject();

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
}
