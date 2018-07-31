package com.huanghh.diary.mvp.view.fragment;

import android.support.v7.widget.RecyclerView;

import com.huanghh.diary.R;
import com.huanghh.diary.base.BaseFragment;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;

public class WeeFragment extends BaseFragment {
    @BindView(R.id.rl_wee)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_wee)
    RecyclerView mRecyclerView;

    public WeeFragment() {
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.fragment_wee;
    }

    @Override
    protected void init() {
        mRefreshLayout.setRefreshHeader(new MaterialHeader(mParentActivity));
    }

    @Override
    protected void inject() {

    }
}
