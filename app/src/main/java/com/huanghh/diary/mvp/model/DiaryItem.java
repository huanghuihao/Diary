package com.huanghh.diary.mvp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class DiaryItem {
    @Id(autoincrement = true)
    private long id;
    private String title;
    private String content;
    private String date;
    private String location;
    private String weather;
    private int localType;
    @Generated(hash = 1165873936)
    public DiaryItem(long id, String title, String content, String date,
            String location, String weather, int localType) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.location = location;
        this.weather = weather;
        this.localType = localType;
    }
    @Generated(hash = 325077529)
    public DiaryItem() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
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
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getWeather() {
        return this.weather;
    }
    public void setWeather(String weather) {
        this.weather = weather;
    }
    public int getLocalType() {
        return this.localType;
    }
    public void setLocalType(int localType) {
        this.localType = localType;
    }
}
