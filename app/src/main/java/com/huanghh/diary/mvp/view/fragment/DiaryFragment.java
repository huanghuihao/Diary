package com.huanghh.diary.mvp.view.fragment;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huanghh.diary.R;
import com.huanghh.diary.adapter.DiaryAdapter;
import com.huanghh.diary.base.BaseFragment;
import com.huanghh.diary.di.component.DaggerDiaryComponent;
import com.huanghh.diary.di.module.DiaryModule;
import com.huanghh.diary.mvp.contract.DiaryContract;
import com.huanghh.diary.mvp.model.DiaryItem;
import com.huanghh.diary.mvp.presenter.DiaryPresenter;
import com.huanghh.diary.mvp.view.activity.DiaryInputActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DiaryFragment extends BaseFragment<DiaryPresenter> implements DiaryContract.View, OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnItemClickListener, View.OnClickListener {
    @BindView(R.id.rv_diary)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_diary)
    SmartRefreshLayout mRefreshLayout;
    View mEmptyView;
    DiaryAdapter mAdapter;
    List<DiaryItem> mData = new ArrayList<>();

    public DiaryFragment() {
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.fragment_diary;
    }

    @Override
    protected void init() {
        //初始化recyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mParentActivity, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mParentActivity, DividerItemDecoration.VERTICAL));
        //初始化adapter并绑定recyclerView
        mAdapter = new DiaryAdapter(R.layout.layout_diary_item, mData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mRecyclerView.setAdapter(mAdapter);

        //设置监听事件
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
        //触发自动刷新
        mRefreshLayout.autoRefresh();
        //设置空视图
        mAdapter.bindToRecyclerView(mRecyclerView);
        mEmptyView = mLayoutInflater.inflate(R.layout.layout_diary_empty, null, false);
        mAdapter.setEmptyView(mEmptyView);
        mEmptyView.setOnClickListener(this);
    }

    @Override
    protected void inject() {
        DaggerDiaryComponent.builder().diaryModule(new DiaryModule(this, mDao)).build().inject(this);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mData.clear();
        mData.addAll(mPresenter.getRefreshData());
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh(200);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        showToast("上拉加载");
        mRefreshLayout.finishLoadMore(200);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        showToast("onItemClick" + position);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(mParentActivity, DiaryInputActivity.class));
    }

    @Override
    public void getLocalData(List<DiaryItem> list) {

    }

    @Override
    public int setDefaultPage() {
        return 0;
    }

}
