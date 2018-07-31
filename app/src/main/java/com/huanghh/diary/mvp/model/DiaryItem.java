package com.huanghh.diary.mvp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class DiaryItem {
    @Id(autoincrement = true)
    private Long id;
    /**
     * 标题、内容、时间、天气、位置、是否公开、存储状态(-1-本地暂存;0-本地完成;1-服务器;2-提交服务器失败;)
     */
    private String title, content, date, weather, location;
    private boolean isPublic;
    private int localType;

    @Generated(hash = 257720192)
    public DiaryItem(Long id, String title, String content, String date,
                     String weather, String location, boolean isPublic, int localType) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.weather = weather;
        this.location = location;
        this.isPublic = isPublic;
        this.localType = localType;
    }

    @Generated(hash = 325077529)
    public DiaryItem() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather() {
        return this.weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean getIsPublic() {
        return this.isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public int getLocalType() {
        return this.localType;
    }

    public void setLocalType(int localType) {
        this.localType = localType;
    }

}
