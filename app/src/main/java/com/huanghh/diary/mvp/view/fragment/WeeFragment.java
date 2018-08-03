package com.huanghh.diary.mvp.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huanghh.diary.R;
import com.huanghh.diary.adapter.WeeAdapter;
import com.huanghh.diary.base.BaseFragment;
import com.huanghh.diary.di.component.DaggerWeeComponent;
import com.huanghh.diary.di.module.WeeModule;
import com.huanghh.diary.mvp.contract.WeeContract;
import com.huanghh.diary.mvp.model.WeeItem;
import com.huanghh.diary.mvp.presenter.WeePresenter;
import com.huanghh.diary.mvp.view.activity.WeeInputActivity;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

public class WeeFragment extends BaseFragment<WeePresenter> implements WeeContract.View, OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnItemClickListener, View.OnClickListener {
    @BindView(R.id.rl_wee)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_wee)
    RecyclerView mRecyclerView;
    View mEmptyView;
    WeeAdapter mAdapter;
    List<WeeItem> mData = new ArrayList<>();
    private TextView mTv_empty;
    @BindString(R.string.empty_text_wee)
    String emptyStr;

    public WeeFragment() {
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.fragment_wee;
    }

    @SuppressLint("InflateParams")
    @Override
    protected void init() {
        //初始化recyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mParentActivity, LinearLayoutManager.VERTICAL, false));
        //初始化adapter并绑定recyclerView
        mAdapter = new WeeAdapter(R.layout.layout_wee_item, mData);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setRefreshHeader(new MaterialHeader(mParentActivity));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(mParentActivity));
        mRefreshLayout.setEnableAutoLoadMore(false);
        //设置监听事件
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
        //触发自动刷新
        mRefreshLayout.autoRefresh();
        //设置空视图
        mAdapter.bindToRecyclerView(mRecyclerView);
        mEmptyView = mLayoutInflater.inflate(R.layout.layout_diary_empty, null, false);
        mTv_empty = mEmptyView.findViewById(R.id.tv_description_empty);
        mAdapter.setEmptyView(mEmptyView);
        mEmptyView.setOnClickListener(this);
    }

    @Override
    protected void inject() {
        DaggerWeeComponent.builder().weeModule(new WeeModule(this, mDao)).build().inject(this);
    }

    @Override
    protected boolean isLoadEventBus() {
        return true;
    }

    @Override
    public int setDefaultPage() {
        return 0;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mData.clear();
        mData.addAll(mPresenter.getRefreshData());
        if (mData.size() == 0) mTv_empty.setText(emptyStr);
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh(200);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mData.addAll(mPresenter.getLoadMoreData());
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.finishLoadMore(200);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(mParentActivity, WeeInputActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventRefresh(String event) {
        if (event.equals("weeRefresh")) mRefreshLayout.autoRefresh();
    }
}
