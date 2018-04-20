package com.weichuang.ecommerce.appVersion.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>ClassName: AppVersionEntity.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:app版本管理 </p>
 * <p>author wanggongliang</p>
 * <p>2017年11月22日 下午1:48:36</p>
 */
public class AppVersionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;// 主键

    private Integer platForm;// 区分是安卓还是ios 1安卓2ios

    private Integer type;// 1老板助手2店员助手

    private String versionCode;// 版本号

    private String versionName;// 版本名称

    private String download;// 下载地址

    private Integer size; // 安装包大小

    private String contents;// 更新说明

    private Integer forceUpdate;// 强制更新（1=是 2=否）

    private Date createTime;// 发布时间

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlatForm() {
        return platForm;
    }

    public void setPlatForm(Integer platForm) {
        this.platForm = platForm;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Integer getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Integer forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
