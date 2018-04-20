package com.weichuang.ecommerce.tenant.entity;

/**
 * <p>ClassName: CompanyEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:公司实体 </p>
 * <p>author: liuzhanchao</p>
 * <p>2017年12月6日 下午10:12:17</p>
 */
public class ParentAgentBaseEntity {

    // 公司id
    private int parentAgentId;

    // 公司名称
    private String parentAgentName;

    // 后台用于控制选中状态
    private int checkStatus;

    public int getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

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

}
