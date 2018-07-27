package com.huanghh.diary.mvp.view;

public interface BaseView {
    /**
     * 处理通用view组件回调处理，在view中进行实现
     */

    void showLoading();

    void hideLoading();

    void showToast(String msg);

    void showError();

    boolean isShowLoading();
}
