package com.huanghh.diary.mvp.contract;

import com.huanghh.diary.mvp.model.Diary;
import com.huanghh.diary.mvp.presenter.BasePresenter;
import com.huanghh.diary.mvp.view.BaseView;

import java.util.List;

public interface DiaryInputContract {
    interface View extends BaseView {
        /**
         * 回调返回界面，并通知diaryFragment刷新数据源
         */
        void saveFinish();
    }

    interface Presenter extends BasePresenter {
        /**
         * 表单校验
         *
         * @param title   标题
         * @param content 内容
         * @return 是否存储
         */
        boolean checkInput(String title, String content);

        /**
         * 校验天气和定位api获取是否正常
         *
         * @param apiValue 天气、定位
         * @return 校验后的值
         */
        String checkApiError(String apiValue);

        /**
         * 根据list组装pic为String进行存储
         *
         * @param pics 图片集合
         * @return 图片路径, String存储
         */
        String getPicStr(List<String> pics);

        /**
         * presenter根据字段进行校验并保存至本地
         *
         * @param title     标题
         * @param content   内容
         * @param weather   天气
         * @param location  位置
         * @param pics      图片
         * @param isPublic  是否公开
         * @param localType 存储类型
         */
        void save(Diary diary,
                  String title,
                  String content,
                  String weather,
                  String location,
                  List<String> pics,
                  boolean isPublic,
                  int localType);
    }
}
