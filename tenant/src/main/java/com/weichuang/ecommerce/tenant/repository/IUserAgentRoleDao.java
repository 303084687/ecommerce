package com.weichuang.ecommerce.tenant.repository;


import com.weichuang.ecommerce.tenant.entity.UserAgentRoleEntity;
import com.weichuang.ecommerce.tenant.entity.UserCountEntity;

import java.util.List;

/**
 * <p>ClassName: IUserAgentRoleDao.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户角色数据访层 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年09月21日 下午19:49:13</p>
 */
public interface IUserAgentRoleDao {
    /**
     * <p>Description: 增加用户角色</p>
     * <p>param entity 用户角色实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/21 20:00 </p>
     * <p>return </p>
     */
    public int addUserAgentRole(UserAgentRoleEntity entity);

    /**
     * <p>Description: 根据用户名Id查询用户角色信息</p>
     * <p>userName 用户名</p>
     * <p>date 2017/10/17 10:00 </p>
     * <p>return UserAgentRoleEntity</p>
     */
    public UserAgentRoleEntity getUserAgentRoleByUserId(int userId);

    /**
     * <p>Description: 根据代理商id及角色查询用户角色信息</p>
     * <p>agentId 代理商id</p>
     * <p>roleId 角色id</p>
     * <p>date 2017/10/20 10:00 </p>
     * <p>return List<UserAgentRoleEntity></p>
     */
    public List<UserAgentRoleEntity> getUserAgentRoleByAgentIdAndRoleId(int agentId, int roleId);

    /**
     * <p>Description: 根据用户id更新业务人员的关系信息,更新角色及所在代理商</p>
     * <p>userId 用户名</p>
     * <p>agentId 代理商id</p>
     * <p>agentName 代理商名称</p>
     * <p>roleId 业务人员角色id</p>
     * <p>roleName 业务人员角色名称</p>
     * <p>date 2017/10/17 10:00 </p>
     * <p>return int</p>
     */
    public int updateSalesAgentRoleByUserId(int userId, int agentId, String agentName, int roleId, String roleName);

    /**
     * <p>Description: 根据用户id用户的关系信息,更新角色、业务人员及所在代理商</p>
     * <p>userId 用户名</p>
     * <p>agentId 代理商id</p>
     * <p>agentName 代理商名称</p>
     * <p>roleId 用户角色id</p>
     * <p>roleName 用户角色名称</p>
     * <p>salesId 业务人员id</p>
     * <p>salesName 业务人员名称</p>
     * <p>date 2017/10/17 10:00 </p>
     * <p>return int</p>
     */
    public int updateUserAgentRoleByUserId(int userId, int agentId, String agentName, int roleId, String roleName, int salesId, String salesName);

    /**
     * <p>Description: 根据用户id用户的关系信息,更新角色、推荐人信息、业务人员及所在代理商</p>
     * <p>userId 用户名</p>
     * <p>agentId 代理商id</p>
     * <p>agentName 代理商名称</p>
     * <p>roleId 用户角色id</p>
     * <p>roleName 用户角色名称</p>
     * <p>salesId 业务人员id</p>
     * <p>salesName 业务人员名称</p>
     * <p>date 2017/10/17 10:00 </p>
     * <p>return int</p>
     */
    public int updateReferrerAgentRoleByUserId(int userId, int agentId, String agentName, int roleId, String roleName, int salesId, String salesName, int referrerId, String referrerName);
    public UserCountEntity selectUserCountByUserId(int type,int userId);
    /**
     *<p>Description:通过用户id修改收款人id</p>
     *<p>param userId:用户id</p>
     *<p>param receiverId:收款人id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/28 9:35</p>
     */
    public int updateReceiverIdByUserId(int userId,int receiverId);


    /**
     *<p>Description:通过agentId修改agentName</p>
     *<p>param agentId:Id</p>
     *<p>param agentName:名称</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2018/1/4 13:35</p>
     */

    public int updateAgentNameByAgentId(int agentId,String agentName);
}
