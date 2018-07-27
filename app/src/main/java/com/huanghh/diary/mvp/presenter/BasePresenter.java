package com.huanghh.diary.mvp.presenter;

public interface BasePresenter {
    /**
     *presenter在activity中是否需要http初始化请求服务器
     */
    void start();

    /**
     * view = null 在baseActivity中onStop()生命周期释放内存
     */
    void detachView();

    /**
     * 判断view是否销毁
     *
     * @return view当前是否存在
     */
    boolean isViewAttached();
}
