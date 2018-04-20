package com.weichuang.ecommerce.tenant.entity;

import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: SalesEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户详情实体 </p>
 * <p>author: liuzhanchao</p>
 * <p>2017年11月8日 上午11:11:17</p>
 */
public class SalesEntity extends UserDetailEntity {

    // 备注信息
    private String descripe;

    // 所属公司id

    private int parentAgentId;

    // 所属公司名称
    private String parentAgentName;

    // 所推广客户

    private int userCount;

    public int getParentAgentId() {
        return parentAgentId;
    }

    public void setParentAgentId(int parentAgentId) {
        this.parentAgentId = parentAgentId;
    }

    public String getParentAgentName() {
        return parentAgentName;
    }

    public void setParentAgentName(String parentAgentName) {
        this.parentAgentName = parentAgentName;
    }

    public String getDescripe() {
        return descripe;
    }

    public void setDescripe(String descripe) {
        this.descripe = descripe;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

}