package com.weichuang.ecommerce.website.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>ClassName: WebsiteBannerEntity.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:网站banner实体类 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月7日 上午11:23:00</p>
 */
public class WebsiteBannerEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;// 主键

    private String title;// banner标题

    private String keywords;// 关键词，多个用,隔开

    private String imageUrl;// 图片地址

    private String linkUrl;// 外部链接地址

    private int browseNumber;// 浏览数

    private int type;// 分类 1轮播图

    private String channelIds;// 显示的渠道1web,2h5,3安卓4ios

    private String content;// 正文

    private int status;// 默认为1未审核，2为已审核

    private int sort;// 排序，越大放在最前面

    private Date pushTime;// 发布时间，不写默认为系统时间

    private Date createTime;// 创建时间

    private Date updateTime;// 修改时间

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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getChannelIds() {
        return channelIds;
    }

    public void setChannelIds(String channelIds) {
        this.channelIds = channelIds;
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

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public int getBrowseNumber() {
        return browseNumber;
    }

    public void setBrowseNumber(int browseNumber) {
        this.browseNumber = browseNumber;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
