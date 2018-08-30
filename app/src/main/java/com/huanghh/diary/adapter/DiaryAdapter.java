package com.huanghh.diary.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huanghh.diary.R;
import com.huanghh.diary.mvp.model.Diary;

import java.util.List;

public class DiaryAdapter extends BaseQuickAdapter<Diary, BaseViewHolder> {
    public DiaryAdapter(int layoutResId, @Nullable List<Diary> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Diary item) {
        helper.setText(R.id.tv_title_diary, item.getTitle())
                .setText(R.id.tv_content_diary, item.getContent())
                .setText(R.id.tv_date_diary, item.getDate())
                .setText(R.id.tv_location_diary, item.getLocation())
                .setText(R.id.tv_weather_diary, item.getWeather());
        String[] pics = item.getPics().split(",");
        String picPath = null;
        if (pics.length > 0) {
            picPath = pics[0];
        }

        if (!TextUtils.isEmpty(picPath)) {
            Glide.with(mContext).load(picPath).into((ImageView) helper.getView(R.id.img_pic_diary));
        } else {
            helper.setImageResource(R.id.img_pic_diary, R.mipmap.ic_launcher);
        }
    }
}
