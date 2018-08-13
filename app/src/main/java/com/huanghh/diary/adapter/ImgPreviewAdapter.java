package com.huanghh.diary.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanghh.diary.R;

import java.util.List;

public class ImgPreviewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context mContext;

    public ImgPreviewAdapter(int layoutResId, @Nullable List<String> data, Context mContext) {
        super(layoutResId, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Glide.with(mContext).load(item).into(((ImageView) helper.getView(R.id.img_item)));
    }
}
