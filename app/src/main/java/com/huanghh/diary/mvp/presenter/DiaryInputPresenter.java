package com.huanghh.diary.mvp.presenter;

import android.util.Log;

import com.blankj.utilcode.util.TimeUtils;
import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.interfaces.IJsonResultListener;
import com.huanghh.diary.interfaces.JsonResultResponse;
import com.huanghh.diary.mvp.contract.DiaryInputContract;
import com.huanghh.diary.mvp.model.Diary;
import com.huanghh.diary.mvp.model.WeatherResponse;
import com.huanghh.diary.retrofit.ApiMethods;

import java.util.List;

public class DiaryInputPresenter extends BasePresenterImpl<DiaryInputContract.View> implements DiaryInputContract.Presenter {
    private DaoSession mDao;

    public DiaryInputPresenter(DiaryInputContract.View view, DaoSession daoSession) {
        mView = view;
        this.mDao = daoSession;
    }

    @Override
    public void start() {

    }

    @Override
    public boolean checkInput(String title, String content) {

        if (title.length() == 0) {
            mView.showToast("请输入日记标题!");
            return false;
        }
        if (content.length() == 0) {
            mView.showToast("请输入日记内容!");
            return false;
        }
        return true;
    }

    @Override
    public String checkApiError(String apiValue) {
        if (apiValue.contains("获取中") || apiValue.contains("失败")) return "无";
        return apiValue;
    }

    @Override
    public String getPicStr(List<String> pics) {
        if (pics.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (String pic : pics) {
                if (!pic.equals("empty")) {
                    sb.append(pic);
                    sb.append(",");
                }
            }
            String pic = sb.toString();
            if (pic.length() == 0) return "";
            return pic.substring(0, pic.length() - 1);
        }
        return "";
    }

    @Override
    public void save(Diary diary, String title, String content, String weather, String location,
                     List<String> pics, boolean isPublic, int localType) {
        if (checkInput(title, content)) {
            diary.setTitle(title);
            diary.setContent(content);
            diary.setDate(TimeUtils.getNowString());
            diary.setWeather(checkApiError(weather));
            diary.setLocation(checkApiError(location));
            diary.setIsPublic(isPublic);
            diary.setPics(getPicStr(pics));
            diary.setLocalType(localType);
            mDao.insert(diary);

            mView.saveFinish();
        }
    }

    @Override
    public void getWeatherWithLocation(String location) {
        Log.e("请求天气信息", location);
        ApiMethods.getNowWeather(location, new JsonResultResponse<WeatherResponse>(new IJsonResultListener<WeatherResponse>() {
            @Override
            public void start() {

            }

            @Override
            public void onComplete(WeatherResponse weatherWeatherBaseResponse) {
                mView.getWeatherResult(weatherWeatherBaseResponse.getHeWeather6().get(0).getNow().getCond_txt());
            }

            @Override
            public void finish() {

            }

            @Override
            public void onError(Throwable e) {
                mView.getWeatherError(e);
            }
        }));
    }
}
