package com.weichuang.ecommerce.website.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * author :han 2018-01-18 15:45
 */
public class ContentEntity implements Serializable {
    private static final long serialVersionUID = 12223L;

    private int id;
    private String title;
    private String content;
    private int status;
    private int sort;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
