package com.huanghh.diary.mvp.view.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huanghh.diary.R;
import com.huanghh.diary.adapter.ImgPreviewAdapter;
import com.huanghh.diary.base.BaseActivity;
import com.huanghh.diary.di.component.DaggerDiaryPreviewComponent;
import com.huanghh.diary.di.module.DiaryPreviewModule;
import com.huanghh.diary.mvp.contract.DiaryPreviewContract;
import com.huanghh.diary.mvp.model.Diary;
import com.huanghh.diary.mvp.presenter.DiaryPreviewPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class DiaryPreviewActivity extends BaseActivity<DiaryPreviewPresenter> implements DiaryPreviewContract.View, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.tv_title_diary_preview)
    TextView mTv_title;
    @BindView(R.id.tv_content_diary_preview)
    TextView mTv_content;
    @BindView(R.id.tv_weather_diary_preview)
    TextView mTv_weather;
    @BindView(R.id.tv_location_diary_preview)
    TextView mTv_location;
    @BindView(R.id.tv_date_diary_preview)
    TextView mTv_date;
    @BindView(R.id.rv_pics_diary_preview)
    RecyclerView mRv_pics;
    private long diaryId;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_diary_preview;
    }

    @Override
    protected void init() {
        diaryId = getIntent().getLongExtra("id", 0);
        initTitle();
    }

    /**
     * 初始化title标题
     */
    private void initTitle() {
        leftIsVisibility(View.VISIBLE);
        setTitle("日记");
    }

    @Override
    protected void inject() {
        DaggerDiaryPreviewComponent.builder().diaryPreviewModule(new DiaryPreviewModule(this, mDao, diaryId)).build().inject(this);
    }

    @Override
    public void setData(Diary diary) {
        mTv_title.setText(diary.getTitle());
        mTv_content.setText(diary.getContent());
        mTv_weather.setText(diary.getWeather());
        mTv_location.setText(diary.getLocation());
        mTv_date.setText(diary.getDate());
        setPicsView(diary.getPics());

    }

    private void setPicsView(String picStr) {
        String[] pics = picStr.split(",");
        if (pics.length > 0) {
            List<String> picList = new ArrayList<>();
            Collections.addAll(picList, pics);
            ImgPreviewAdapter adapter = new ImgPreviewAdapter(R.layout.layout_img_item, picList, this);
            adapter.setOnItemClickListener(this);
            mRv_pics.setLayoutManager(new GridLayoutManager(this, 4));
            mRv_pics.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
