package com.huanghh.diary.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanghh.diary.R;

import java.util.List;

public class DiaryImagesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context mContext;

    public DiaryImagesAdapter(int layoutResId, @Nullable List<String> data, Context mContext) {
        super(layoutResId, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (item.equals("empty")) {
            helper.setImageResource(R.id.img_item, R.mipmap.add_img);
        } else {
            Glide.with(mContext).load(item).into(((ImageView) helper.getView(R.id.img_item)));
        }
    }

    public int refreshPicData(List<String> pics) {
        int temp;
        for (int i = 0; i < getData().size(); i++) {
            if (getData().get(i).equals("empty")) getData().remove(i);
        }
        addData(pics);
        int maxSize = 9;
        temp = maxSize - getData().size();
        if (this.getData().size() < maxSize) addData("empty");
        this.notifyDataSetChanged();

        return temp;
    }
}
