package com.huanghh.diary.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.mvp.presenter.BasePresenter;
import com.huanghh.diary.mvp.view.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    Unbinder mBfBinder;
    protected Activity mParentActivity;
    protected LayoutInflater mLayoutInflater;
    @Inject
    protected P mPresenter;
    ProgressDialog mLoadingDialog;
    //数据库
    protected DaoSession mDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(setContentLayoutRes(), container, false);
        mBfBinder = ButterKnife.bind(this, contentView);
        mParentActivity = getActivity();
        mLayoutInflater = inflater;
        mDao = DiaryApp.getDaoSession();
        init();

        inject();
        mLoadingDialog = new ProgressDialog(mParentActivity);
        mLoadingDialog.setCancelable(false);
        return contentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter!=null){
            mPresenter.detachView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter = null;
        mBfBinder.unbind();
    }

    protected abstract int setContentLayoutRes();

    protected abstract void init();

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
        Toast.makeText(mParentActivity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        showToast("其它错误!");
    }
}
