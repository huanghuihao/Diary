package com.huanghh.diary.mvp.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.huanghh.diary.R;
import com.huanghh.diary.adapter.HomePagerAdapter;
import com.huanghh.diary.base.BaseActivity;
import com.huanghh.diary.di.component.DaggerHomeComponent;
import com.huanghh.diary.di.module.HomeModule;
import com.huanghh.diary.mvp.contract.HomeContract;
import com.huanghh.diary.mvp.presenter.HomePresenter;
import com.huanghh.diary.mvp.view.fragment.DiaryFragment;
import com.huanghh.diary.mvp.view.fragment.SettingFragment;
import com.huanghh.diary.mvp.view.fragment.WeeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View, BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView mBottomView;
    @BindView(R.id.vP_content)
    ViewPager mViewPager;
    private List<Fragment> mListFragment = new ArrayList<>();
    private MenuItem menuItem;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    protected void init() {
        //设置默认缓存页面，不然会调用fragment的onCreate导致不复用
        mViewPager.setOffscreenPageLimit(3);
        setTitle("写日记");
        leftIsVisibility(View.GONE);
        setRight(2);
        mListFragment.add(new DiaryFragment());
        mListFragment.add(new WeeFragment());
        mListFragment.add(new SettingFragment());
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager(), mListFragment);
        mViewPager.setAdapter(adapter);
        mBottomView.setOnNavigationItemSelectedListener(this);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void inject() {
        DaggerHomeComponent.builder().homeModule(new HomeModule(this)).build().inject(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        this.menuItem = menuItem;
        switch (menuItem.getItemId()) {
            case R.id.tab_diary:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tab_wee:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.tab_setting:
                mViewPager.setCurrentItem(2);
                break;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        if (menuItem != null) {
            menuItem.setChecked(false);
        } else {
            mBottomView.getMenu().getItem(0).setChecked(false);
        }
        menuItem = mBottomView.getMenu().getItem(i);
        menuItem.setChecked(true);

        switch (i) {
            case 0:
                setTitle("写日记");
                setRight(2);
                break;
            case 1:
                setTitle("写点滴");
                setRight(2);
                break;
            case 2:
                setTitle("设置");
                setRight(0);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @OnClick(R.id.img_right)
    public void onClick() {
        int current = mViewPager.getCurrentItem();
        switch (current) {
            case 0:
                startActivity(new Intent(HomeActivity.this, DiaryInputActivity.class));
                break;
            case 1:
                startActivity(new Intent(HomeActivity.this, WeeInputActivity.class));
                break;
        }
    }
}
