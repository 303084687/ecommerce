package com.weichuang.ecommerce.tenant.repository;

import com.weichuang.ecommerce.tenant.entity.*;

import java.util.List;

/**
 * <p>ClassName: IUserDao.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户数据访层 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年09月19日 下午19:49:13</p>
 */
public interface IUserDao {
    /**
     * <p>Description: 增加用户信息</p>
     * <p>param entity 用户实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/19 19:00 </p>
     * <p>return </p>
     */
    public int addUser(UserEntity entity);

    /**
     * <p>Description: 根据用户名查询用户信息</p>
     * <p>userName 用户名</p>
     * <p>date 2017/9/20 19:00 </p>
     * <p>return UserEntity</p>
     */
    public UserEntity getUserByUserName(String userName);

    /**
     * <p>Description: 根据用户Id查询用户信息</p>
     * <p>userId 用户Id</p>
     * <p>date 2017/9/22 16:50 </p>
     * <p>return UserEntity</p>
     */
    public UserEntity getUserById(int userId);

    /**
     * <p>Description: 根据第三方OpenId及登陆类型查询用户信息</p>
     * <p>openId 第三方OpenId</p>
     * <p>registerType 登陆类型</p>
     * <p>date 2017/9/21 19:00 </p>
     * <p>return UserEntity</p>
     */
    public UserEntity getUserByOpenId(String openId, int registerType);

    /**
     * <p>Description: 根据手机号查询用户信息</p>
     * <p>mobile mobile手机号</p>
     * <p>date 2017/10/18 10:00 </p>
     * <p>return UserEntity</p>
     */
    public UserEntity getUserByMobile(String mobile);

    /**
     * <p>Description: 根据用户名查询用户详细信息</p>
     * <p>userName 用户名</p>
     * <p>date 2017/9/22 11:49 </p>
     * <p>return UserDetailEntity</p>
     */
    public UserDetailEntity getUserDetailByUserName(String userName);

    public UserDetailEntity getUserByUnionId(String unionId,Integer registerType);
    /**
     *<p>Description:根据角色查询用户信息</p>
     *<p>param roleId:角色id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/29 13:23</p>
     *<p>return: UserDetailEntity</p>
     */
    public List<UserDetailEntity>  getUserDetailByRoleId(int roleId);
    /**
     *<p>Description:根据角色时间查询用户信息</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/29 13:23</p>
     *<p>return: UserDetailEntity</p>
     */
    public List<UserDetailEntity>  getUserDetailByRoleIdOrDate(int roleId,Integer timeType);
    /**
     * <p>Description: 根据userId查询用户详细信息</p>
     * <p>userId 用户Id</p>
     * <p>date 2017/10/11 16:49 </p>
     * <p>return UserDetailEntity</p>
     */
    public UserDetailEntity getUserDetailByUserId(int userId);
    /**
     * <p>Description: 根据userId查询管理者用户详细信息</p>
     * <p>userId 用户Id</p>
     * <p>date 2017/12/27 17:49 </p>
     * <p>return ManagerDetailEntity</p>
     */
    public ManagerDetailEntity getManagerDetailById(int userId);

    /**
     * <p>Description: 根据OpenId查询用户详细信息</p>
     * <p>openId 第三方登陆的openId</p>
     * <p>date 2017/10/13 15:18 </p>
     * <p>return UserDetailEntity</p>
     */
    public UserDetailEntity getUserDetailByOpenId(String openId, int registerType);

    /**
     * <p>Description: 根据手机号查询用户详细信息</p>
     * <p>mobile 用户注册手机号</p>
     * <p>date 2017/10/27 10:18 </p>
     * <p>return UserDetailEntity</p>
     */
    public UserDetailEntity getUserDetailByMobile(String mobile);

    /**
     * <p>Description: 根据用户名更新用户密码</p>
     * <p>userName 用户名</p>
     * <p>password 新密码</p>
     * <p>date 2017/9/21 13:00 </p>
     * <p>return int</p>
     */
    public int updatePasswordByUserName(String userName, String password);

    /**
     * <p>Description: 根据用户名更新用户密码</p>
     * <p>userName 用户名</p>
     * <p>password 新密码</p>
     * <p>date 2017/9/21 13:00 </p>
     * <p>return int</p>
     */
    public int updatePasswordByUserId(int userId, String password);

    /**
     * <p>Description: 根据用户名更新用户最后登陆时间</p>
     * <p>userName 用户名</p>
     * <p>date 2017/9/21 13:00 </p>
     * <p>return int</p>
     */
    public int updateUserLoginTime(String userName);

    /**
     * 绑定手机号，增加真实姓名
     * mobil  手机号
     * realName 真实姓名
     * date 2017/9/28
     */
    public int updateUserMobileByOpenId(String openId, String mobile, String realName, int registerType);

    /**
     *绑定手机号
     * @param mobile 手机号码
     * @param id 用户id
     * @return int
     */
    public int updateUserMobileById(String mobile,int id);
    /**
     * <p>Description: 根据手机号,更新用户密码</p>
     * <p>mobile 用户手机号</p>
     * <p>password 用户密码，加密后字符串</p>
     * <p>date 2017/10/19 16:53 </p>
     * <p>return int</p>
     */
    public int updateUserPasswordByMobile(String mobile, String password);
    /**
     *<p>Description:后台全部用户查询</p>
      *<p>param startTime:开始时间</p>
     *<p>param endTime:结束时间</p>
     *<p>param logStatus:状态</p>
     *<p>param companyId:公司id</p>
     *<p>param agentId:门店id</p>
     *<p>param searchInput:搜索输入</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/2 14:46</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public List<UserAllEntity> getAllUser(String startTime,String endTime,Integer logStatus,Integer companyId,Integer agentId,String searchInput);
    /**
     *<p>Description:</p>
     *<p>param agentId:代理商id</p>
     *<p>param roleId:角色id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/3 11:27</p>
     *<p>return:用户信息</p>
     *<p>throws: </p>
     */
    public List<UserEntity> getUserByAgentIdOrRoleId(Integer agentId,Integer roleId);
    /**
     *<p>Description:更新用户状态</p>
     *<p>param status:状态</p>
     *<p>param id:编号</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/3 14:53</p>
     *<p>return:</p>
     */
    public int updateUserStatus(Integer status,Integer id);
    /**
     *<p>Description:通过id查用户</p>
      *<p>param id:用户id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/4 18:08</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public UserAllEntity getAllUserOne(Integer id);
    /**
     *<p>Description:修改用户备注</p>
     *<p>param userid:用户id</p>
     *<p>param descripe:备注</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/7 11:25</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public int updateUserDescripe(int userid,String descripe);
    /**
     *<p>Description:通过手机号查询用户邀新信息</p>
     *<p>param mobile:</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/19 14:27</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public InviteNewEntity getInviteNewByMobile(String mobile);

    public int updateUserDetailByOpenId(int userid,String openId,String mobile,String nickName,String realName,int gender,String iconUrl,String password);
    public List<UserNewDetailEntity> getUserBySearch(Integer loginStatus,Integer saleId);

    public List<UserDetailEntity> getUserDetailByCompanyIds(int type,List<Integer> ids);
}