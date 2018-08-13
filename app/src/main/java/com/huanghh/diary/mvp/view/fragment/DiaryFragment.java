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
import com.huanghh.diary.adapter.DiaryAdapter;
import com.huanghh.diary.base.BaseFragment;
import com.huanghh.diary.di.component.DaggerDiaryComponent;
import com.huanghh.diary.di.module.DiaryModule;
import com.huanghh.diary.mvp.contract.DiaryContract;
import com.huanghh.diary.mvp.model.DiaryItem;
import com.huanghh.diary.mvp.presenter.DiaryPresenter;
import com.huanghh.diary.mvp.view.activity.DiaryInputActivity;
import com.huanghh.diary.mvp.view.activity.DiaryPreviewActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

public class DiaryFragment extends BaseFragment<DiaryPresenter> implements DiaryContract.View, OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnItemClickListener, View.OnClickListener {
    @BindView(R.id.rv_diary)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_diary)
    SmartRefreshLayout mRefreshLayout;
    View mEmptyView;
    DiaryAdapter mAdapter;
    List<DiaryItem> mData = new ArrayList<>();
    private TextView mTv_empty;
    @BindString(R.string.empty_text_diary)
    String emptyStr;

    public DiaryFragment() {
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.fragment_diary;
    }

    @SuppressLint("InflateParams")
    @Override
    protected void init() {
        //初始化recyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mParentActivity, LinearLayoutManager.VERTICAL, false));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(mParentActivity, DividerItemDecoration.VERTICAL));
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
        mTv_empty = mEmptyView.findViewById(R.id.tv_description_empty);
        mAdapter.setEmptyView(mEmptyView);
        mEmptyView.setOnClickListener(this);
    }

    @Override
    protected void inject() {
        DaggerDiaryComponent.builder().diaryModule(new DiaryModule(this, mDao)).build().inject(this);
    }

    @Override
    protected boolean isLoadEventBus() {
        return true;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mData.clear();
        mAdapter.addData(mPresenter.getRefreshData());
        if (mData.size() == 0) mTv_empty.setText(emptyStr);
        mRefreshLayout.finishRefresh(200);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mAdapter.addData(mPresenter.getLoadMoreData());
        mRefreshLayout.finishLoadMore(200);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(mParentActivity, DiaryPreviewActivity.class);
        intent.putExtra("id", mData.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(mParentActivity, DiaryInputActivity.class));
    }

    @Override
    public int setDefaultPage() {
        return 0;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(String event) {
        if (event.equals("diaryRefresh")) mRefreshLayout.autoRefresh();
    }
}
