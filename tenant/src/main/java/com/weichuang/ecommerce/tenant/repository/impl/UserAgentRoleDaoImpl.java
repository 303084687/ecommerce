package com.weichuang.ecommerce.tenant.repository.impl;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.tenant.constants.NameSpaceConstant;
import com.weichuang.ecommerce.tenant.entity.UserAgentRoleEntity;
import com.weichuang.ecommerce.tenant.entity.UserCountEntity;
import com.weichuang.ecommerce.tenant.repository.IUserAgentRoleDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>ClassName: IOrderDao.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单数据访层 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年09月19日 下午20:12:13</p>
 */

@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class UserAgentRoleDaoImpl implements IUserAgentRoleDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * <p>Description: 增加用户角色</p>
     * <p>param entity 用户角色实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/21 20:00 </p>
     * <p>return </p>
     */
    @Override
    public int addUserAgentRole(UserAgentRoleEntity entity) {
        return sqlSessionTemplate.insert(NameSpaceConstant.USER_AGENT_ROLE + ".addUserAgentRole", entity);
    }

    /**
     * <p>Description: 根据用户名Id查询用户角色信息</p>
     * <p>userName 用户名</p>
     * <p>date 2017/10/17 10:00 </p>
     * <p>return UserAgentRoleEntity</p>
     */
    @Override
    public UserAgentRoleEntity getUserAgentRoleByUserId(int userId){
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER_AGENT_ROLE + ".getUserAgentRoleByUserId", userId);
    }

    /**
     * <p>Description: 根据代理商id及角色查询用户角色信息</p>
     * <p>agentId 代理商id</p>
     * <p>roleId 角色id</p>
     * <p>date 2017/10/20 10:00 </p>
     * <p>return List<UserAgentRoleEntity></p>
     */
    @Override
    public List<UserAgentRoleEntity> getUserAgentRoleByAgentIdAndRoleId(int agentId, int roleId){
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("agentId", agentId);
        param.put("roleId", roleId);
        return sqlSessionTemplate.selectList(NameSpaceConstant.USER_AGENT_ROLE + ".getUserAgentRoleByAgentIdAndRoleId", param);
    }

    /**
     * <p>Description: 根据用户id更新业务人员的关系信息,更新角色及所在代理商</p>
     * <p>userId 用户名</p>
     * <p>agentId 代理商id</p>
     * <p>agentName 代理商名称</p>
     * <p>roleId 业务人员角色id</p>
     * <p>roleName 业务人员角色名称</p>
     * <p>date 2017/10/20 10:00 </p>
     * <p>return int</p>
     */
    public int updateSalesAgentRoleByUserId(int userId, int agentId, String agentName, int roleId, String roleName){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("agentId", agentId);
        map.put("agentName", agentName);
        map.put("roleId", roleId);
        map.put("roleName", roleName);
        return sqlSessionTemplate.update(NameSpaceConstant.USER_AGENT_ROLE + ".updateSalesAgentRoleByUserId", map);
    }

    /**
     * <p>Description: 根据用户id用户的关系信息,更新角色、业务人员及所在代理商</p>
     * <p>userId 用户名</p>
     * <p>agentId 代理商id</p>
     * <p>agentName 代理商名称</p>
     * <p>roleId 用户角色id</p>
     * <p>roleName 用户角色名称</p>
     * <p>salesId 业务人员id</p>
     * <p>salesName 业务人员名称</p>
     * <p>date 2017/10/20 10:00 </p>
     * <p>return int</p>
     */
    public int updateUserAgentRoleByUserId(int userId, int agentId, String agentName, int roleId, String roleName, int salesId, String salesName){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("agentId", agentId);
        map.put("agentName", agentName);
        map.put("roleId", roleId);
        map.put("roleName", roleName);
        map.put("salesId", salesId);
        map.put("salesName", salesName);
        return sqlSessionTemplate.update(NameSpaceConstant.USER_AGENT_ROLE + ".updateUserAgentRoleByUserId", map);
    }

    /**
     * <p>Description: 根据用户id用户的关系信息,更新角色、推荐人信息、业务人员及所在代理商</p>
     * <p>userId 用户名</p>
     * <p>agentId 代理商id</p>
     * <p>agentName 代理商名称</p>
     * <p>roleId 用户角色id</p>
     * <p>roleName 用户角色名称</p>
     * <p>salesId 业务人员id</p>
     * <p>salesName 业务人员名称</p>
     * <p>date 2017/10/20 10:00 </p>
     * <p>return int</p>
     */
    public int updateReferrerAgentRoleByUserId(int userId, int agentId, String agentName, int roleId, String roleName, int salesId, String salesName, int referrerId, String referrerName){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("agentId", agentId);
        map.put("agentName", agentName);
        map.put("roleId", roleId);
        map.put("roleName", roleName);
        map.put("salesId", salesId);
        map.put("salesName", salesName);
        map.put("referrerId", referrerId);
        map.put("referrerName", referrerName);
        return sqlSessionTemplate.update(NameSpaceConstant.USER_AGENT_ROLE + ".updateReferrerAgentRoleByUserId", map);
    }
    /**
     *<p>Description:通过id查询发送邀请数量</p>
     *<p>param type:1 店员  2 门店</p>
     *<p>param userId:店员或门店id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/22 15:13</p>
     *<p>return:</p>
     */
    public UserCountEntity selectUserCountByUserId(int type, int userId){
        Map param=new HashMap();
        param.put("type",type);
        param.put("userId",userId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER_AGENT_ROLE + ".selectUserCountByUserId", param);
    }
    /**
     *<p>Description:通过用户id修改收款人id</p>
     *<p>param userId:用户id</p>
     *<p>param receiverId:收款人id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/28 9:35</p>
     */
    public int updateReceiverIdByUserId(int userId,int receiverId){

        Map param=new HashMap();
        param.put("userId",userId);
        param.put("receiveraccountId",receiverId);
        return sqlSessionTemplate.update(NameSpaceConstant.USER_AGENT_ROLE + ".updateReceiverIdByUserId", param);

    }

    /**
     *<p>Description:通过agentId修改agentName</p>
     *<p>param agentId:Id</p>
     *<p>param agentName:名称</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2018/1/4 13:35</p>
     */
    @Override
    public int updateAgentNameByAgentId(int agentId,String agentName){
        Map param=new HashMap();
        param.put("agentId",agentId);
        param.put("agentName",agentName);
        return sqlSessionTemplate.update(NameSpaceConstant.USER_AGENT_ROLE + ".updateAgentNameByAgentId", param);
    }
}