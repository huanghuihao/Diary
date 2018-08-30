package com.huanghh.diary.mvp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Wee {
    @Id(autoincrement = true)
    private Long id;
    /**
     * 内容、时间、位置、是否公开、存储状态(-1-本地暂存;0-本地完成;1-服务器;2-提交服务器失败;)
     */
    private String content;
    private String date;
    private String location;
    private boolean isPublic;
    private int localType;

    @Generated(hash = 988211675)
    public Wee(Long id, String content, String date, String location,
               boolean isPublic, int localType) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.location = location;
        this.isPublic = isPublic;
        this.localType = localType;
    }

    @Generated(hash = 2020539553)
    public Wee() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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
