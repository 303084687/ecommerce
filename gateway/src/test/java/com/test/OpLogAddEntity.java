package com.test;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.zhidianwuxian.cn</p>
 * <p>Description: 记录操作日志实体类 </p>
 * <p>Author:jmzhang/张际明, 16/09/20</p>
 */
public class OpLogAddEntity implements Serializable {
    private static final long serialVersionUID = -1527818643523270678L;

    @NotNull(message = "{log.operation_log.put.oplogaddentity.userid.notnull.message}")
    private String userId;

    @NotNull(message = "{log.operation_log.put.oplogaddentity.username.notnull.message}")
    private String userName;

    @NotNull(message = "{log.operation_log.put.oplogaddentity.rolename.notnull.message}")
    private String roleName;

    @NotNull(message = "{log.operation_log.put.oplogaddentity.opdate.notnull.message}")
    private Date opDate;

    @NotNull(message = "{log.operation_log.put.oplogaddentity.ip.notnull.message}")
    private String ip;

    @NotNull(message = "{log.operation_log.put.oplogaddentity.module.notnull.message}")
    private String module;

    @NotNull(message = "{log.operation_log.put.oplogaddentity.operate.notnull.message}")
    private String operate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

}
