package com.weichuang.ecommerce.tenant.repository.impl;


import com.weichuang.ecommerce.EncryptUtil;
import com.weichuang.ecommerce.tenant.entity.*;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.mock.MockAction;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.tenant.constants.NameSpaceConstant;
import com.weichuang.ecommerce.tenant.repository.IUserDao;

import javax.xml.stream.events.Namespace;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>ClassName: UserDaoImpl.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户数据访层 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年09月19日 下午19:49:13</p>
 */

@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class UserDaoImpl implements IUserDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * <p>Description: 增加用户信息</p>
     * <p>param entity 用户实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/19 19:00 </p>
     * <p>return </p>
     */
    @Override
    public int addUser(UserEntity entity) {
        return sqlSessionTemplate.insert(NameSpaceConstant.USER + ".addUser", entity);
    }

    /**
     * <p>Description: 根据用户名查询用户信息</p>
     * <p>userName 用户名</p>
     * <p>date 2017/9/20 19:00 </p>
     * <p>return UserEntity</p>
     */
    @Override
    public UserEntity getUserByUserName(String userName) {
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER + ".getUserByUserName", userName);
    }

    /**
     * <p>Description: 根据用户Id查询用户信息</p>
     * <p>userId 用户Id</p>
     * <p>date 2017/9/22 16:50 </p>
     * <p>return UserEntity</p>
     */
    @Override
    public UserEntity getUserById(int userId) {
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER + ".getUserById", userId);
    }

    /**
     * <p>Description: 根据用户名查询用户详细信息</p>
     * <p>userName 用户名</p>
     * <p>date 2017/9/22 11:49 </p>
     * <p>return UserDetailEntity</p>
     */
    @Override
    public UserDetailEntity getUserDetailByUserName(String userName) {
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER + ".getUserDetailByUserName", userName);
    }
    /**
     *<p>Description:</p>
     *<p>param unionId:</p>
     *<p>param registerType:</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/1 13:59</p>
     *<p>return:UserDetailEntity</p>
     */
    public UserDetailEntity getUserByUnionId(String unionId,Integer registerType){
        Map param=new HashMap();
        param.put("unionId",unionId);
        param.put("registerType",registerType);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER + ".getUserByUnionId", param);
    }
    /**
     *<p>Description:根据角色查询用户信息</p>
     *<p>param roleId:角色id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/29 13:23</p>
     *<p>return: UserDetailEntity</p>
     */
    public List<UserDetailEntity> getUserDetailByRoleId(int roleId){
        return sqlSessionTemplate.selectList(NameSpaceConstant.USER + ".getUserDetailByRoleId", roleId);
    }
    /**
     *<p>Description:根据角色时间查询用户信息</p>
     *<p>param timeType  1： 小于等于30天 2 ：大于30天</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/29 13:23</p>
     *<p>return: UserDetailEntity</p>
     */
    public List<UserDetailEntity>  getUserDetailByRoleIdOrDate(int roleId,Integer timeType){
        Map param=new HashMap();
        param.put("roleId",roleId);
        param.put("timeType",timeType);
        return sqlSessionTemplate.selectList(NameSpaceConstant.USER + ".getUserDetailByRoleIdOrDate", param);
    }
    /**
     * <p>Description: 根据OpenId查询用户详细信息</p>
     * <p>openId 第三方登陆的openId</p>
     * <p>date 2017/10/13 15:18 </p>
     * <p>return UserDetailEntity</p>
     */
    public UserDetailEntity getUserDetailByOpenId(String openId, int registerType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("openId", openId);
        map.put("registerType", registerType);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER + ".getUserDetailByOpenId", map);
    }

    /**
     * <p>Description: 根据手机号查询用户详细信息</p>
     * <p>mobile 用户注册手机号</p>
     * <p>date 2017/10/27 10:18 </p>
     * <p>return UserDetailEntity</p>
     */
    public UserDetailEntity getUserDetailByMobile(String mobile) {
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER + ".getUserDetailByMobile", mobile);
    }

    /**
     * <p>Description: 根据第三方OpenId及登陆类型查询用户信息</p>
     * <p>openId 第三方OpenId</p>
     * <p>registerType 登陆类型</p>
     * <p>date 2017/9/21 19:00 </p>
     * <p>return UserEntity</p>
     */
    @Override
    public UserEntity getUserByOpenId(String openId, int registerType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("openId", openId);
        map.put("registerType", registerType);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER + ".getUserByOpenId", map);
    }

    /**
     * <p>Description: 根据手机号查询用户信息</p>
     * <p>mobile mobile手机号</p>
     * <p>date 2017/10/18 10:00 </p>
     * <p>return UserEntity</p>
     */
    public UserEntity getUserByMobile(String mobile) {
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER + ".getUserByMobile", mobile);
    }

    /**
     * <p>Description: 根据用户名更新用户密码</p>
     * <p>userName 用户名</p>
     * <p>password 新密码</p>
     * <p>date 2017/9/20 13:00 </p>
     * <p>return int</p>
     */
    @Override
    public int updatePasswordByUserName(String userName, String password) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userName", userName);
        map.put("password", password);
        return sqlSessionTemplate.update(NameSpaceConstant.USER + ".updatePasswordByUserName", map);
    }

    /**
     * <p>Description: 根据用户名更新用户密码</p>
     * <p>userName 用户名</p>
     * <p>password 新密码</p>
     * <p>date 2017/9/20 13:00 </p>
     * <p>return int</p>
     */
    @Override
    public int updatePasswordByUserId(int userId, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("password", password);
        return sqlSessionTemplate.update(NameSpaceConstant.USER + ".updatePasswordByUserId", map);
    }

    /**
     * <p>Description: 根据用户名更新用户最后登陆时间</p>
     * <p>userName 用户名</p>
     * <p>date 2017/9/21 13:56 </p>
     * <p>return int</p>
     */
    @Override
    public int updateUserLoginTime(String userName) {
        return sqlSessionTemplate.update(NameSpaceConstant.USER + ".updateUserLoginTime", userName);
    }

    /**
     * 绑定手机号，真实姓名
     */
    @Override
    public int updateUserMobileByOpenId(String openId, String mobile, String realName, int registerType) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("openId", openId);
        map.put("mobile", mobile);
        map.put("realName", realName);
        map.put("registerType", registerType + "");
        return sqlSessionTemplate.update(NameSpaceConstant.USER + ".updateUserMobileByOpenId", map);
    }


    /**
     * <p>Description: 根据userId查询用户详细信息</p>
     * <p>userId 用户名</p>
     * <p>date 2017/10/11 16:49 </p>
     * <p>return UserDetailEntity</p>
     */
    @Override
    public UserDetailEntity getUserDetailByUserId(int userId) {
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER + ".getUserDetailByUserId", userId);
    }
    /**
     * <p>Description: 根据userId查询管理者用户详细信息</p>
     * <p>userId 用户Id</p>
     * <p>date 2017/12/27 17:49 </p>
     * <p>return ManagerDetailEntity</p>
     */
    public ManagerDetailEntity getManagerDetailById(int userId){
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER + ".getManagerDetailById", userId);
    }
    /**
     * <p>Description: 根据手机号,更新用户密码</p>
     * <p>mobile 用户手机号</p>
     * <p>password 用户密码，加密后字符串</p>
     * <p>date 2017/10/19 16:53 </p>
     * <p>return int</p>
     */
    public int updateUserPasswordByMobile(String mobile, String password) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", mobile);
        map.put("password", password);
        return sqlSessionTemplate.update(NameSpaceConstant.USER + ".updateUserPasswordByMobile", map);
    }

    /**
     * <p>Description:后台全部用户查询</p>
     * <p>param startTime:开始时间</p>
     * <p>param endTime:结束时间</p>
     * <p>param logStatus:状态</p>
     * <p>param companyId:公司id</p>
     * <p>param agentId:门店id</p>
     * <p>param searchInput:搜索输入</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/2 14:46</p>
     * <p>return:</p>
     * <p>throws: </p>
     */
    public List<UserAllEntity> getAllUser(String startTime, String endTime, Integer logStatus, Integer companyId, Integer agentId, String searchInput) {
        UserAllEntity allEntity = new UserAllEntity();
        if (StringUtils.isNotEmpty(startTime))
            allEntity.setStartTime(startTime);
        if (StringUtils.isNotEmpty(endTime))
            allEntity.setEndTime(endTime);
        if (logStatus != null)
            allEntity.setLoginStatus(logStatus);
        if (companyId != null)
            allEntity.setCompanyId(companyId);
        if (agentId != null)
            allEntity.setAgentId(agentId);
        if (StringUtils.isNotEmpty(searchInput))
            allEntity.setSearchInput(searchInput);
        return sqlSessionTemplate.selectList(NameSpaceConstant.USER + ".getAllUserList", allEntity);
    }

    /**
     * <p>Description:</p>
     * <p>param agentId:代理商id</p>
     * <p>param roleId:角色id</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/3 11:27</p>
     * <p>return:用户信息</p>
     * <p>throws: </p>
     */
    public List<UserEntity> getUserByAgentIdOrRoleId(Integer agentId, Integer roleId) {
        Map paramMap = new HashMap();
        if (agentId != null && agentId > 0) {
            paramMap.put("agentId", agentId);
        }
        if (roleId != null && roleId > 0) {
            paramMap.put("roleId", roleId);
        }
        return sqlSessionTemplate.selectList(NameSpaceConstant.USER + ".getUserByAgentIdOrRoleId", paramMap);

    }

    /**
     * <p>Description:更新用户状态</p>
     * <p>param status:状态</p>
     * <p>param id:编号</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/3 14:53</p>
     * <p>return:</p>
     */
    public int updateUserStatus(Integer status, Integer id) {

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("loginStatus", status);
        map.put("id", id);
        return sqlSessionTemplate.update(NameSpaceConstant.USER + ".updateUserStatus", map);
    }

    /**
     *<p>Description:通过用户编号查用户</p>
      *<p>param id:用户编号</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/4 18:18</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public UserAllEntity getAllUserOne(Integer id) {
        UserAllEntity allEntity = new UserAllEntity();
        allEntity.setId(id);
        List<UserAllEntity> list=  sqlSessionTemplate.selectList(NameSpaceConstant.USER + ".getAllUserList", allEntity);
        if(list!=null &&!list.isEmpty())
        {
            return list.get(0);
        }
        else {
            return null;
        }
    }
    /**
     *<p>Description:修改用户备注</p>
     *<p>param userid:用户id</p>
     *<p>param descripe:备注</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/7 11:25</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public int updateUserDescripe(int userid,String descripe) {

        UserEntity entity=new UserEntity();
        entity.setId(userid);
        entity.setDescripe(descripe);
        return sqlSessionTemplate.update(NameSpaceConstant.USER + ".updateUserDescripe", entity);
    }

    /**
     *<p>Description:通过手机号查询用户邀新信息</p>
     *<p>param mobile:</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/19 14:27</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public InviteNewEntity getInviteNewByMobile(String mobile) {
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER + ".selectInviteNewByMobile",mobile);
    }

    /**
     *绑定手机号
     * @param mobile 手机号码
     * @param id 用户id
     * @return int
     */
    public int updateUserMobileById(String mobile,int id){
        UserEntity entity=new UserEntity();
        entity.setId(id);
        entity.setMobile(mobile);
        return sqlSessionTemplate.update(NameSpaceConstant.USER + ".updateUserMobileById", entity);
    }
    /**
     *<p>Description:通过openid更新用户信息</p>
     *<p>param openId:微信的openId</p>
     *<p>param nickName:</p>
     *<p>param realName:</p>
     *<p>param gender:</p>
     *<p>param iconUrl:</p>
     *<p>param password:</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/22 14:14</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public int updateUserDetailByOpenId(int userid,String openId,String mobile,String nickName,String realName,int gender,String iconUrl,String password){
        UserEntity entity=new UserEntity();
        entity.setUserName(openId);
        entity.setGender(gender);
        entity.setIconUrl(iconUrl);
        entity.setMobile(mobile);
        entity.setRealName(realName);
        entity.setPassword(password);
        EncryptUtil encryptUtil=new EncryptUtil();
        Long userId = new Long(userid);
        try{
            if(StringUtils.isNotEmpty(nickName)){
                entity.setNickName(EncryptUtil.getBase64(nickName));
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return sqlSessionTemplate.update(NameSpaceConstant.USER + ".updateUserDetailByOpenId", entity);
    }
    /**
     *<p>Description:查询用户</p>
     *<p>param loginStatus:用户状态</p>
     *<p>param saleId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/22 17:46</p>
     */
    public List<UserNewDetailEntity> getUserBySearch(Integer loginStatus,Integer saleId){
        Map param=new HashMap();
        param.put("loginStatus",loginStatus);
        param.put("saleId",saleId);
        return sqlSessionTemplate.selectList(NameSpaceConstant.USER + ".getUserBySearch",param);
    }


    public List<UserDetailEntity> getUserDetailByCompanyIds(int type,List<Integer> ids){
        Map param=new HashMap();
        param.put("type",type);
        param.put("ids",ids);
        return sqlSessionTemplate.selectList(NameSpaceConstant.USER + ".getUserDetailByCompanyIds",param);
    }
}
