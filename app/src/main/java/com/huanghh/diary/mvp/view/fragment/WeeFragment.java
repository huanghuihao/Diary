package com.huanghh.diary.mvp.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyachi.stepview.VerticalStepView;
import com.huanghh.diary.R;
import com.huanghh.diary.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WeeFragment extends BaseFragment {
    @BindView(R.id.stepView_wee)
    VerticalStepView mStepView;
    Unbinder unbinder;

    public WeeFragment() {
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.fragment_wee;
    }

    @Override
    protected void init() {
        List<String> list0 = new ArrayList<>();
        list0.add("今天很生气！！！");
        list0.add("吧啦吧啦吧啦吧啦吧啦吧啦吧啦吧啦！！！");
        list0.add("好热！！");
        list0.add("好热！！");
        list0.add("好热！！");
        mStepView.setStepsViewIndicatorComplectingPosition(list0.size() - 2)//设置完成的步数
                .reverseDraw(true)//default is true
                .setStepViewTexts(list0)//总步骤
                .setLinePaddingProportion(0.85f)//设置indicator线与线间距的比例系数
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getActivity(), R.color.stepView_line))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getActivity(), R.color.stepView_line))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.stepView_line))//设置StepsView text完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.stepView_line))//设置StepsView text未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getActivity(), R.mipmap.wee_wee))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getActivity(), R.mipmap.wee_circle))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getActivity(), R.mipmap.wee_circle));//设置StepsViewIndicator AttentionIcon
    }

    @Override
    protected void inject() {

    }
}
