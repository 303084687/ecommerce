package com.weichuang.ecommerce.tenant.service;

import com.weichuang.commons.Response;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.tenant.entity.InviteNewEntity;
import com.weichuang.ecommerce.tenant.entity.UserAllEntity;
import com.weichuang.ecommerce.tenant.entity.UserCountEntity;
import com.weichuang.ecommerce.tenant.entity.UserEntity;
import com.weichuang.ecommerce.tenant.entity.response.*;

import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * <p>ClassName: IUserService.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户数据服务接口 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年09月19日 下午20:12:13</p>
 */
public interface IUserService {
    /**
     * <p>Description: 通过第三方登陆方式注册用户，普通用户注册，用户要关联到相业的员工及健身房</p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>gender 性别</p>
     * <p>iconUrl 第三方头像</p>
     * <p>agentId 所有代理的ID,目前是健身房的id</p>
     * <p>salesId 健身房员工的id</p>
     * <p>mobile 注册时绑定的手机号，作为用户名，必填</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>date 2017/9/19 19:00 </p>
     * <p>return </p>
     */
    public UserDetailResponse addUserByThird(String openId, String nickName, int gender, String iconUrl, int salesId, String mobile, int registerType) throws ServiceException, Exception;
    /**
     * <p>Description: 通过第三方登陆方式注册用户，普通用户注册，用户要关联到相业的员工及健身房</p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>gender 性别</p>
     * <p>iconUrl 第三方头像</p>
     * <p>agentId 所有代理的ID,目前是健身房的id</p>
     * <p>salesId 健身房员工的id</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>date 2017/9/19 19:00 </p>
     * <p>return </p>
     */
    public UserDetailResponse addUserByThird(String openId, String nickName, int gender, String iconUrl, int salesId, int registerType) throws ServiceException, Exception;

    /**
     * <p>Description:微信授权扫码注册用户</p>
     * <p>param openId:第三方OpenID</p>
     * <p>param nickName:第三方昵称</p>
     * <p>param gender:姓别</p>
     * <p>param iconUrl:第三方头像</p>
     *<p>param realname:姓名</p>
     *<p>param mobile:手机号</p>
     *<p>param password:手机号</p>
     * <p>param codekey:二维码id</p>
     * <p>param codeType:1 门店 2 公司</p>
     * <p>param registerType:第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/21 15:36</p>
     * <p>return:</p>
     */
    public UserDetailResponse addUserByThirdBarcode(String openId, String nickName, int gender, String iconUrl, String realname, String mobile, String password, String codekey, int codeType, int registerType) throws ServiceException, Exception;
    /**
     * <p>Description:通过openid更新用户信息</p>
     * <p>param openId:第三方OpenID</p>
     * <p>param nickName:第三方昵称</p>
     * <p>param gender:姓别</p>
     * <p>param iconUrl:第三方头像</p>
     *<p>param realname:姓名</p>
     *<p>param mobile:手机号</p>
     *<p>param opassword:原密码</p>
     *<p>param password:密码</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/21 15:36</p>
     * <p>return:</p>
     */
    public UserDetailResponse updateUserByUnionId(String unionId,String appOpenId,String codeKey, String nickName, int gender, String iconUrl, String realname, String mobile,String opassword, String password,int registerType) throws ServiceException, Exception;

    /**
     * <p>Description: 通过第三方登陆方式注册用户，普通用户注册推荐普通用户（需要记录推荐人的id）</p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>gender 性别</p>
     * <p>iconUrl 第三方头像</p>
     * <p>referrerId 推荐人用户id</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>date 2017/10/17 19:00 </p>
     * <p>return UserDetailResponse</p>
     */
    public UserDetailResponse addUserReferrerByThird(String openId, String nickName, int gender, String iconUrl, int referrerId, int registerType) throws ServiceException, Exception;

    /**
     * <p>Description: 通过第三方登陆方式注册用户，健身房员工注册，员工要和健身房及角色关联</p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>gender 性别</p>
     * <p>iconUrl 第三方头像</p>
     * <p>agentId 所有代理的ID,目前是健身房的id</p>
     * <p>roleId 所属角色id</p>
     * <p>realName 真实姓名</p>
     * <p>mobile 注册时绑定的手机号，作为用户名，必填</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>date 2017/9/19 19:00 </p>
     * <p>return </p>
     */
    public UserDetailResponse addSalesByThird(String openId, String nickName, int gender, String iconUrl, int agentId, int roleId, String realName, String mobile, int registerType) throws ServiceException, Exception;

    /**
     * <p>Description: 通过第三方登陆方式注册用户，健身房员工注册，员工要和健身房及角色关联。
     * 第一次关注微信公众号时，暂时先不需绑定手机或真实姓名，再次进入商城时需要绑定真实姓名和手机号
     * </p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>gender 性别</p>
     * <p>iconUrl 第三方头像</p>
     * <p>agentId 所有代理的ID,目前是健身房的id</p>
     * <p>roleId 所属角色id</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>date 2017/10/09 14:00 </p>
     * <p>return </p>
     */
    public UserDetailResponse addSalesByThird(String openId, String nickName, int gender, String iconUrl, int agentId, int roleId, int registerType) throws ServiceException, Exception;

    /**
     * <p>Description: 通过第三方登陆方式注册用户，添加健身房的管理员工，目前只能在父级的代理，添加管理人员且只有一人
     * </p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>gender 性别</p>
     * <p>iconUrl 第三方头像</p>
     * <p>agentId 所有代理的ID,目前是健身房的id</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>date 2017/10/20 11:00 </p>
     * <p>return </p>
     */
    public UserDetailResponse addManagerByThird(String openId, String nickName, int gender, String iconUrl, int agentId, int registerType) throws ServiceException, Exception;

    /**
     * <p>Description: 通过第三方登陆方式注册用户，普通用户注册。不于业务人员或健身房产生关系
     * 第一次关注微信公众号时，暂时先不需绑定手机或真实姓名，再次进入商城时需要绑定真实姓名和手机号
     * </p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>gender 性别 0女， 1男</p>
     * <p>iconUrl 第三方头像</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>date 2017/10/09 14:00 </p>
     * <p>return UserResponse</p>
     */
    public UserDetailResponse addUserWithoutSalesByThird(String openId, String nickName,
                                                         int gender, String iconUrl, int registerType)
            throws ServiceException, Exception;

    public UserDetailResponse addUserWithoutSalesByApp(String openId, String nickName,
                                                         int gender, String iconUrl, int registerType,String unionId)
            throws ServiceException, Exception;
    /**
     * <p>Description: 根据第三方OpenId及登陆类型查询用户信息</p>
     * <p>openId 第三方OpenId</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	</p>
     * <p>date 2017/9/21 19:00 </p>
     * <p>return UserResponse</p>
     */
    public UserResponse getUserByOpenId(String openId, int registerType) throws ServiceException, Exception;
    /**
     *<p>Description:通过unionid查询用户信息</p>
     *<p>param unionId:全局id</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/1 13:53</p>
     *<p>return:UserDetailResponse</p>
     */
    public UserDetailResponse  getUserByUnionId(String unionId,int registerType) throws ServiceException,Exception;
    /**
     * <p>Description: 根据手机号码查询用户信息</p>
     * <p>mobile 用户手机号</p>
     * <p>date 2017/10/18 10:00 </p>
     * <p>return UserResponse</p>
     */
    public UserResponse getUserByMobile(String mobile) throws ServiceException, Exception;
    /**
     * <p>Description: 根据mobile查询用户信息</p>
     * <p>mobile 用户手机</p>
     * <p>date 2017/11/22 16:53 </p>
     * <p>return UserResponse</p>
     *
     * @throws ServiceException
     */
    public UserDetailResponse getUserDetailByMobile(String  userId) throws Exception;

    /**
     * <p>Description: 根据用户名查询用户信息</p>
     * <p>userName 用户名</p>
     * <p>date 2017/9/21 16:53 </p>
     * <p>return UserResponse</p>
     */
    public UserResponse getUserByUserName(String userName) throws ServiceException, Exception;

    /**
     * <p>Description: 根据userId查询用户信息</p>
     * <p>userId 用户Id</p>
     * <p>date 2017/10/11 16:53 </p>
     * <p>return UserResponse</p>
     *
     * @throws ServiceException
     */
    public UserDetailResponse getUserDetailByUserId(int userId) throws ServiceException;
    /**
     * <p>Description: 根据userId查询用户信息</p>
     * <p>userId 用户Id</p>
     * <p>date 2017/12/27 16:53 </p>
     * <p>return UserResponse</p>
     *
     * @throws ServiceException
     */
    public ManagerDetailResponse getManagerDetailByUserId(int userId) throws ServiceException,Exception;
    /**
     * <p>Description: 根据用户名查询用户信息</p>
     * <p>userName 用户名</p>
     * <p>date 2017/9/21 16:53 </p>
     * <p>return UserDetailResponse</p>
     */
    public UserDetailResponse getUserDetailByUserName(String userName) throws ServiceException, Exception;


    public UserDetailListResponse getAllSales() throws ServiceException, Exception;

    /**
     * <p>Description: 根据第三方注册openId查询用户信息</p>
     * <p>openId 第三方注册用户openId</p>
     * <p>date 2017/10/13 16:53 </p>
     * <p>return UserDetailResponse</p>
     * <p>throws ServiceException</p>
     */
    public UserDetailResponse getUserDetailByOpenId(String openId, int registerType) throws ServiceException;

    public int updateUserThird(String openId,String unionId,String appOpenId)throws ServiceException, Exception;
    /**
     * <p>Description: 根据用户名更新用户最后登陆时间</p>
     * <p>userName 用户名</p>
     * <p>date 2017/9/21 13:00 </p>
     * <p>return boolean</p>
     */
    public boolean updateUserLoginTime(String userName) throws ServiceException, Exception;

    /**
     * <p>Description: 根据openId,绑定手机号，绑定真实姓名</p>
     * <p>userName 用户名</p>
     * <p>date 2017/9/21 13:00 </p>
     * <p>return boolean</p>
     */
    public UserDetailResponse updateUserMobileByOpenId(String openId, String mobile, String realName, int registerType) throws ServiceException, Exception;

    /**
     * <p>Description: 根据手机号,更新用户密码</p>
     * <p>mobile 用户手机号</p>
     * <p>password 用户密码，base64后的字符串</p>
     * <p>date 2017/10/19 16:53 </p>
     * <p>return boolean</p>
     */
    public boolean updateUserPasswordByMobile(String mobile, String password) throws ServiceException, Exception;

    /**
     * <p>Description: 根据用户id更新管理人员的关系信息,更新角色及所在代理商</p>
     * <p>userId 用户名id</p>
     * <p>agentId 代理商id</p>
     * <p>date 2017/10/17 10:00 </p>
     * <p>return boolean</p>
     */
    public boolean updateManagerAgentRoleByUserId(int userId, int agentId) throws ServiceException, Exception;

    /**
     * <p>Description: 根据用户id更新业务人员的关系信息,更新角色及所在代理商</p>
     * <p>userId 用户名id</p>
     * <p>agentId 代理商id</p>
     * <p>roleId 业务人员角色id</p>
     * <p>date 2017/10/17 10:00 </p>
     * <p>return boolean</p>
     */
    public boolean updateSalesAgentRoleByUserId(int userId, int agentId, int roleId) throws ServiceException, Exception;

    /**
     * <p>Description: 根据用户id用户的关系信息,更新角色、业务人员及所在代理商</p>
     * <p>userId 用户名</p>
     * <p>salesId 业务人员id</p>
     * <p>date 2017/10/17 10:00 </p>
     * <p>return boolean</p>
     */
    public boolean updateUserAgentRoleByUserId(int userId, int salesId) throws ServiceException, Exception;

    /**
     * <p>Description: 根据用户id用户的关系信息,更新角色、推荐人信息、业务人员及所在代理商</p>
     * <p>userId 用户名</p>
     * <p>referrerId 推荐人id</p>
     * <p>date 2017/10/20 10:00 </p>
     * <p>return boolean</p>
     */
    public boolean updateReferrerAgentRoleByUserId(int userId, int referrerId) throws ServiceException, Exception;

    /**
     * <p>Description: 根据用户通过手机号进行登陆</p>
     * <p>mobile 用户登陆的手机号</p>
     * <p>password 用户登陆的密码</p>
     * <p>date 2017/10/27 10:00 </p>
     * <p>return UserDetailResponse</p>
     */
    public UserDetailResponse userLoginByMobile(String mobile, String password) throws ServiceException, Exception;

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
     */
    public UserAllListResponse getUserAll(int pageNum, int pageSize, String startTime, String endTime, Integer logStatus, Integer companyId, Integer agentId, String searchInput) throws Exception;
    /**
     * <p>Description:后台全部用户查询导出</p>
     * <p>param startTime:开始时间</p>
     * <p>param endTime:结束时间</p>
     * <p>param logStatus:状态</p>
     * <p>param companyId:公司id</p>
     * <p>param agentId:门店id</p>
     * <p>param searchInput:搜索输入</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2018/1/19 14:46</p>
     */
    public UserAllListResponse getUserAllExport(String startTime, String endTime, Integer logStatus, Integer companyId, Integer agentId, String searchInput) throws Exception;
    /**
     * <p>Description:</p>
     * <p>param agentId:代理商id</p>
     * <p>param roleId:角色id</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/3 11:27</p>
     * <p>return:用户信息</p>
     * <p>throws: </p>
     */
    public UserListResponse getUserByAgentIdOrRoleId(Integer agentId, Integer roleId);
    /**
     * <p>Description:会员信息</p>
     * <p>param type: 1: 注册30天以内会员（含30天） 2：注册30天以上会员  3：所有销售</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/12/13 15:27</p>
     * <p>return:用户信息</p>
     * <p>throws: </p>
     */
    public UserDetailListResponse getCouponUserList(int type);
    /**
     * <p>Description:更新用户状态</p>
     * <p>param status:状态</p>
     * <p>param id:编号</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/3 14:53</p>
     * <p>return:</p>
     */
    public boolean updateUserStatus(Integer status, Integer id) throws Exception;

    /**
     * <p>Description:通过用户编号查用户</p>
     * <p>param id:用户编号</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/4 18:18</p>
     * <p>return:</p>
     * <p>throws: </p>
     */
    public UserAllListResponse getAllUserOne(Integer id);

    /**
     * <p>Description:修改用户备注信息</p>
     * <p>param entity:</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/7 11:20</p>
     * <p>return:</p>
     * <p>throws: </p>
     */
    public boolean updateUserDescripe(int userid, String descripe) throws Exception;

    /**
     * 绑定手机号
     *
     * @param mobile 手机号码
     * @param id     用户id
     * @return int
     */
    public int updateUserMobileById(String mobile, int id) throws Exception;

    /**
     * <p>Description:通过手机号查询用户邀新信息</p>
     * <p>param mobile:</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/19 14:27</p>
     * <p>return:</p>
     * <p>throws: </p>
     */
    public InviteNewResponse getInviteNewByMobile(String mobile) throws Exception;

    /**
     *<p>Description:通过id查询我的客户</p>
     *<p>param type:1 店员  2 门店</p>
     *<p>param userId:店员或门店id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/22 15:13</p>
     *<p>return:</p>
     */
    public MyCustomerResponse selectUserCountByUserId(int type, int userId) throws Exception;

    public int updateReceiverIdByUserId(int receiverid,int userid) throws ServiceException,Exception;

    public ManagerBindMessageResponse getBindMessage(int userId)throws ServiceException,Exception;

    public Response<String> updateInviteSaleJoin(int inviteId, int saleId, String codekey) throws ServiceException,Exception;
    public Response<UserNewDetailResponse> getUserBySearch(int pageNum,int pageSize,int saleId)throws ServiceException,Exception;;
    public UserDetailListResponse getUserDetailByCompanyIds(int type,String ids) throws ServiceException,Exception;
}
