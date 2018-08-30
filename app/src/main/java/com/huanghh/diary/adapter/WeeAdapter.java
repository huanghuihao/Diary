package com.huanghh.diary.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanghh.diary.R;
import com.huanghh.diary.mvp.model.Wee;

import java.util.List;

public class WeeAdapter extends BaseQuickAdapter<Wee, BaseViewHolder> {
    public WeeAdapter(int layoutResId, @Nullable List<Wee> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Wee item) {
        helper.setText(R.id.tv_content_wee, item.getContent())
                .setText(R.id.tv_date_wee, item.getDate())
                .setText(R.id.tv_location_wee, item.getLocation());
    }
}
