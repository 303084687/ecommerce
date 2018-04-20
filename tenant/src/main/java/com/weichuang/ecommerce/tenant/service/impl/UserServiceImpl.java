package com.weichuang.ecommerce.tenant.service.impl;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.PatternHelper;
import com.weichuang.commons.Response;
import com.weichuang.ecommerce.AESUtil;
import com.weichuang.ecommerce.RedisHelper;
import com.weichuang.ecommerce.tenant.constants.*;
import com.weichuang.ecommerce.tenant.entity.*;
import com.weichuang.ecommerce.tenant.entity.response.*;
import com.weichuang.ecommerce.tenant.feign.IBarCode;
import com.weichuang.ecommerce.tenant.repository.*;

import com.weichuang.ecommerce.tenant.resource.UserResource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weichuang.commons.Result;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.EncryptUtil;
import com.weichuang.ecommerce.tenant.service.IUserService;


/**
 * <p>ClassName: IUserService.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户数据服务 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年09月19日 下午20:12:13</p>
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IUserThirdDao userThirdDao;
    @Autowired
    private IUserAgentRoleDao userAgentRoleDao;
    @Autowired
    private IAgentDao agentDao;
    @Autowired
    private IUserRoleDao userRoleDao;
    @Autowired
    private EncryptUtil encryptUtil;
    @Autowired
    private IBarCode barCodeFeign;
    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private InviteSaleJoinDao saleJoinDao;

    /**
     * <p>Description: 通过第三方登陆方式注册用户</p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>iconUrl 第三方头像</p>
     * <p>mobile 注册时绑定的手机号，作为用户名</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>date 2017/9/19 19:00 </p>
     * <p>return </p>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetailResponse addUserByThird(String openId, String nickName, int gender, String iconUrl,
                                             int salesId, String mobile,
                                             int registerType) throws ServiceException, Exception {
        if (StringUtils.isEmpty(openId)) {
            throw new IllegalArgumentException("openId is null or empty");
        }
        if (!PatternHelper.isMobile(mobile)) {
            throw new IllegalArgumentException("mobile format error");
        }
        // 验证用户注册类型
        if (UserRegisterTypeEnum.getUserRegisterType(registerType) == null) {
            throw new IllegalArgumentException("registerType error");
        }
        // 验证性别
        if (GenderEnum.getGenderType(gender) == null) {
            throw new IllegalArgumentException("genderType error");
        }

        UserEntity userEntity = userDao.getUserByUserName(mobile);
        // 用户已经存在，不能注册
        if (userEntity != null) {
            throw new ServiceException(Result.USER_EXIST.getCode(), Result.USER_EXIST.getMessage());
        }
        UserDetailEntity salesEntity = userDao.getUserDetailByUserId(salesId);
        // 判断业务人员是否经存在，业务人员不能为空
        if (salesEntity == null) {
            throw new ServiceException(Result.USER_NOT_EXIST.getCode(), Result.USER_NOT_EXIST.getMessage());
        }

        // 添加用户基本信息
        UserEntity newUser = this.addUser(openId, mobile, nickName, gender, "", iconUrl);
        // 添加第三方授权记录
        this.addUserThird(newUser.getId(), openId, registerType);
        // 添加用户角色
        UserAgentRoleEntity userRole = this.addUserAgentRole(newUser, salesEntity);

        //UserResponse result = this.getUserResponse(newUser);
        UserDetailResponse result = this.getUserDetailResponse(newUser, userRole);
        return result;
    }


    /**
     * <p>Description: 通过第三方登陆方式注册用户</p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>gender 用户性别</p>
     * <p>iconUrl 第三方头像</p>
     * <p>mobile 注册时绑定的手机号，作为用户名</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>date 2017/9/19 19:00 </p>
     * <p>return </p>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetailResponse addUserByThird(String openId,
                                             String nickName,
                                             int gender,
                                             String iconUrl,
                                             int salesId,
                                             int registerType) throws ServiceException, Exception {
        if (StringUtils.isEmpty(openId)) {
            throw new IllegalArgumentException("openId is null or empty");
        }
        // 验证用户注册类型
        if (UserRegisterTypeEnum.getUserRegisterType(registerType) == null) {
            throw new IllegalArgumentException("registerType error");
        }
        // 验证性别
        if (GenderEnum.getGenderType(gender) == null) {
            throw new IllegalArgumentException("genderType error");
        }

        UserEntity userEntity = userDao.getUserByOpenId(openId, registerType);
        // 用户已经存在，不能注册
        if (userEntity != null) {
            throw new ServiceException(Result.USER_EXIST.getCode(), Result.USER_EXIST.getMessage());
        }
        UserDetailEntity salesEntity = userDao.getUserDetailByUserId(salesId);
        // 判断业务人员是否经存在，业务人员不能为空
        if (salesEntity == null) {
            throw new ServiceException(Result.USER_NOT_EXIST.getCode(), Result.USER_NOT_EXIST.getMessage());
        }

        // 添加用户基本信息
        UserEntity newUser = this.addUser(openId, "", nickName, gender, "", iconUrl);
        // 添加第三方授权记录
        this.addUserThird(newUser.getId(), openId, registerType);
        // 添加用户角色
        UserAgentRoleEntity userRole = this.addUserAgentRole(newUser, salesEntity);

        //UserResponse result = this.getUserResponse(newUser);
        UserDetailResponse result = this.getUserDetailResponse(newUser, userRole);
        return result;
    }

    /**
     * <p>Description:通过openid更新用户信息</p>
     * <p>param openId:第三方OpenID</p>
     * <p>param nickName:第三方昵称</p>
     * <p>param gender:姓别</p>
     * <p>param iconUrl:第三方头像</p>
     *<p>param realname:姓名</p>
     *<p>param mobile:手机号</p>
     *<p>param password:手机号</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/21 15:36</p>
     * <p>return:</p>
     */
    @Transactional(rollbackFor ={ Exception.class,ServiceException.class})
    public UserDetailResponse updateUserByUnionId(String unionId,String appOpenId, String codeKey,String nickName, int gender, String iconUrl, String realname,
                                                 String mobile,String opassword, String password,int registerType) throws ServiceException, Exception{
        if(StringUtils.isEmpty(unionId)){
            throw new ServiceException(3011,"unionId is null or empty!");
        }
        if(registerType!=1 && registerType!=2 &&registerType!=3){

            throw new ServiceException(3012,"registerType is error");
        }


        // 判断unionId是否注册过
        UserDetailEntity userEntity= userDao.getUserByUnionId(unionId,registerType);
        if (userEntity==null) {
            throw new ServiceException(3014,"user info is null by unionid");
        }

        if(StringUtils.isNotEmpty(mobile)){
            if(!PatternHelper.isMobile(mobile)){
                throw new ServiceException(3015,"mobile is format error");
            }
            UserEntity userEntity1 = userDao.getUserByUserName(mobile);
            // 用户已经存在或已经注册
            if (userEntity1 != null && !userEntity1.getUserName().equals(userEntity.getUserName())) {
                throw new ServiceException(3016,"手机号码已被注册!");
            }
        }
        if(StringUtils.isNotEmpty(password)){
            if(StringUtils.isNotEmpty(opassword)){
                Long id=new Long(userEntity.getId());
                log.info("前台输入原密码："+opassword+",userid="+userEntity.getId());
                String mpassword=this.encryptUtil.validateLoginPass(id,opassword);
                log.info("前台原密码加密后："+mpassword);
                log.info("数据库获取到的密码："+userEntity.getPassword());
                log.info("原密码输入是否正确："+mpassword.equals(userEntity.getPassword()));
                if(!mpassword.equals(userEntity.getPassword())){
                    throw new ServiceException(3018,"原密码输入不正确！");
                }
            }
            log.info("前台传的密码："+password+",userid="+userEntity.getId());
            password = encryptUtil.registerEncrypt(encryptUtil.getEncryptConfigKey(), Long.valueOf(userEntity.getId()), password);
            log.info("加密后的密码："+password);
        }
        if(StringUtils.isNotEmpty(codeKey)){
            int agentId=0;
            AgentEntity agent=agentDao.selectByCodekey(codeKey);
            if(agent==null ||agent.getId()==0){
                throw new ServiceException(Result.AGENT_NOT_EXIST.getCode(), Result.AGENT_NOT_EXIST.getMessage());
            }
            //代理商已冻结
            if(agent.getStatus()==2){
                throw new ServiceException(Result.AGENT_IS_DISABLED.getCode(), Result.AGENT_IS_DISABLED.getMessage());
            }
            agentId=agent.getId();
            //非门店类型
/*            if(agent.getParentId()<=0){
                throw new ServiceException(Result.AGENT_TYPE_ERROR1.getCode(), Result.AGENT_TYPE_ERROR1.getMessage());
            }*/
            int roleId=UserRoleEnum.EMPLOYEE.getIndex();
            // 验证代理商是否存在，代理商需存在库中
            AgentEntity agentEntity = agentDao.getAgentById(agentId);
            if (agentEntity == null) {
                throw new ServiceException(Result.AGENT_NOT_EXIST.getCode(), Result.AGENT_NOT_EXIST.getMessage());
            }
            //增加公司类型的判断
            if(agent.getParentId()==0){
                roleId=UserRoleEnum.MANAGER.getIndex();
            }
            // 验证用户角色，用户角色不能为空
            UserRoleEntity userRoleEntity = userRoleDao.getUserRoleById(roleId);
            if (userRoleEntity == null) {
                throw new ServiceException(Result.USER_ROLE_NOT_EXIST.getCode(), Result.USER_ROLE_NOT_EXIST.getMessage());
            }
            userAgentRoleDao.updateSalesAgentRoleByUserId(userEntity.getId(),agentEntity.getId(),agentEntity.getAgentName(),roleId,userRoleEntity.getRoleName());
            if(roleId!=UserRoleEnum.MANAGER.getIndex())
                updateUserAgentRoleByUserId(userEntity.getId(),userEntity.getId());
        }
        String openId=userEntity.getOpenId();
        if(StringUtils.isEmpty(mobile) && StringUtils.isEmpty(nickName) && StringUtils.isEmpty(realname) && StringUtils.isEmpty(iconUrl) && StringUtils.isEmpty(password)){
        }
        else{
            userDao.updateUserDetailByOpenId(userEntity.getId(),openId,mobile,nickName,realname,gender,iconUrl,password);
        }
        if(StringUtils.isNotEmpty(appOpenId)){
            userThirdDao.updateUserThird(openId,unionId,appOpenId);
        }
        userEntity= userDao.getUserByUnionId(unionId,registerType);
        UserDetailResponse response=new UserDetailResponse();
        if(userEntity!=null &&userEntity.getId()>0){
            BeanUtils.copyProperties(userEntity,response);
        }
        return response;
    }
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
    @Transactional(rollbackFor = Exception.class)
    public UserDetailResponse addUserByThirdBarcode(String openId, String nickName, int gender, String iconUrl, String realname, String mobile,
                                                    String password, String codekey, int codeType, int registerType) throws ServiceException, Exception{
        UserDetailResponse result=null;
        if (StringUtils.isEmpty(openId)) {
            throw new IllegalArgumentException("openId is null or empty");
        }
        if (StringUtils.isEmpty(mobile) ||!PatternHelper.isMobile(mobile)) {
            throw new IllegalArgumentException("mobile format error");
        }

        // 验证二维码类型
        if (BarCodeTypeEnum.getBarCodeType(codeType) == null) {
            throw new IllegalArgumentException("codeType is  error");
        }
        // 验证性别
        if (GenderEnum.getGenderType(gender) == null) {
            throw new IllegalArgumentException("genderType error");
        }
        if(StringUtils.isEmpty(codekey)) {
            throw new IllegalArgumentException("codekey is null error");
        }
        if(registerType!=1 && registerType!=2 &&registerType!=3){
            throw new IllegalArgumentException("registerType format error");
        }
        UserEntity userEntity = userDao.getUserByUserName(mobile);
        // 用户已经存在或已经注册
        if (userEntity != null) {
            throw new ServiceException(Result.USER_EXIST.getCode(), Result.USER_EXIST.getMessage());
        }
        int agentId=0;
        AgentEntity agent=agentDao.selectByCodekey(codekey);
        if(agent==null ||agent.getId()==0){
            throw new ServiceException(Result.AGENT_NOT_EXIST.getCode(), Result.AGENT_NOT_EXIST.getMessage());
        }
        //代理商已冻结
        if(agent.getStatus()==2){
            throw new ServiceException(Result.AGENT_IS_DISABLED.getCode(), Result.AGENT_IS_DISABLED.getMessage());
        }
        agentId=agent.getId();
        //门店类型
        if(BarCodeTypeEnum.getBarCodeType(codeType)==BarCodeTypeEnum.shop){
            if(agent.getParentId()<=0){
                throw new ServiceException(Result.AGENT_TYPE_ERROR1.getCode(), Result.AGENT_TYPE_ERROR1.getMessage());
            }
            int roleId=UserRoleEnum.EMPLOYEE.getIndex();
            // 验证代理商是否存在，代理商需存在库中
            AgentEntity agentEntity = agentDao.getAgentById(agentId);
            if (agentEntity == null) {
                throw new ServiceException(Result.AGENT_NOT_EXIST.getCode(), Result.AGENT_NOT_EXIST.getMessage());
            }
            // 验证用户角色，用户角色不能为空
            UserRoleEntity userRoleEntity = userRoleDao.getUserRoleById(roleId);
            if (userRoleEntity == null) {
                throw new ServiceException(Result.USER_ROLE_NOT_EXIST.getCode(), Result.USER_ROLE_NOT_EXIST.getMessage());
            }

            // 添加用户基本信息
            UserEntity newUser = this.addUser(openId,password,  mobile,  nickName,  gender,  realname,  iconUrl);
            // 添加第三方授权记录
            this.addUserThird(newUser.getId(), openId, registerType);
            // 添加用户角色
            UserAgentRoleEntity userRole = this.addSalesAgentRole(newUser, agentEntity, userRoleEntity);

            //UserResponse result = this.getUserResponse(newUser);
             result = this.getUserDetailResponse(newUser, userRole);
        }
        //公司类型
        else if(BarCodeTypeEnum.getBarCodeType(codeType)==BarCodeTypeEnum.company){
            if(agent.getParentId()!=0){
                throw new ServiceException(Result.AGENT_TYPE_ERROR2.getCode(), Result.AGENT_TYPE_ERROR2.getMessage());
            }
            int roleId=UserRoleEnum.MANAGER.getIndex();
            // 验证代理商是否存在，代理商需存在库中
            AgentEntity agentEntity = agentDao.getAgentById(agentId);
            if (agentEntity == null) {
                throw new ServiceException(Result.AGENT_NOT_EXIST.getCode(), Result.AGENT_NOT_EXIST.getMessage());
            }

            // 不能在最顶级的代理添加，员工的角色
            if (agentEntity.getParentId() != 0) {
                // 按角色及agentId 查询是否在管理帐户
                //userAgentRoleDao.getUserAgentRoleByAgentIdAndRoleId(agentEntity.getId(), UserRoleEnum.MANAGER.getIndex());
                throw new ServiceException(Result.AGENT_CAN_NOT_ADD_SALES.getCode(), Result.AGENT_CAN_NOT_ADD_SALES.getMessage());
            }

            List<UserAgentRoleEntity> userRoleEntityList = userAgentRoleDao.getUserAgentRoleByAgentIdAndRoleId(agentEntity.getId(), UserRoleEnum.MANAGER.getIndex());
            if (userRoleEntityList.size() >= 1) {
                throw new ServiceException(Result.AGENT_CAN_NOT_ADD_MULTI_MANAGER.getCode(), Result.AGENT_CAN_NOT_ADD_MULTI_MANAGER.getMessage());
            }
            // 获取管理员的角色
            UserRoleEntity userRoleEntity = userRoleDao.getUserRoleById(UserRoleEnum.MANAGER.getIndex());
            // 添加用户基本信息(先将openId 设置成为用户，后结待绑定手机号时再进行更新)
            UserEntity newUser = this.addUser(openId,password,  mobile,  nickName,  gender,  realname,  iconUrl);
            // 添加第三方授权记录
            this.addUserThird(newUser.getId(), openId, registerType);
            // 添加用户角色
            UserAgentRoleEntity userRole = this.addSalesAgentRole(newUser, agentEntity, userRoleEntity);
            result = this.getUserDetailResponse(newUser, userRole);
        }

        return result;
    }
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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetailResponse addUserReferrerByThird(String openId, String nickName, int gender, String iconUrl, int referrerId, int registerType) throws ServiceException, Exception {
        if (StringUtils.isEmpty(openId)) {
            throw new IllegalArgumentException("openId is null or empty");
        }
        // 验证用户注册类型
        if (UserRegisterTypeEnum.getUserRegisterType(registerType) == null) {
            throw new IllegalArgumentException("registerType error");
        }
        // 验证性别
        if (GenderEnum.getGenderType(gender) == null) {
            throw new IllegalArgumentException("genderType error");
        }
        UserDetailEntity referrerUser = userDao.getUserDetailByUserId(referrerId);
        // 推荐用记不存在
        if (referrerUser == null) {
            throw new ServiceException(Result.USER_NOT_EXIST.getCode(), Result.USER_EXIST.getMessage() + referrerId);
        }
        // 判断openId是否注册过
        UserEntity userEntity = userDao.getUserByOpenId(openId, registerType);
        // 用户已经存在或已经注册
        if (userEntity != null) {
            throw new ServiceException(Result.USER_EXIST.getCode(), Result.USER_EXIST.getMessage());
        }

        // 添加用户基本信息
        UserEntity newUser = this.addUser(openId, "", nickName, gender, "", iconUrl);
        // 添加第三方授权记录
        this.addUserThird(newUser.getId(), openId, registerType);
        // 添加用户角色(找到推荐人，将其属性值给新的用户)
        UserAgentRoleEntity userRole = this.addSalesAgentRole(newUser, referrerUser);

        //UserResponse result = this.getUserResponse(newUser);
        UserDetailResponse result = this.getUserDetailResponse(newUser, userRole);
        return result;
    }

    /**
     * <p>Description: 通过第三方登陆方式注册用户，健身房员工注册，员工要和健身房及角色关联</p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>gender 性别</p>
     * <p>iconUrl 第三方头像</p>
     * <p>agentId 所有代理的ID,目前是健身房的id</p>
     * <p>roleId 所属角色id</p>
     * <p>realName 真实姓名</p>
     * <p>mobile 注册时绑定的手机号，作为用户名</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>date 2017/9/19 19:00 </p>
     * <p>return </p>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetailResponse addSalesByThird(String openId, String nickName, int gender, String iconUrl,
                                              int agentId, int roleId, String realName,
                                              String mobile, int registerType) throws ServiceException, Exception {
        if (StringUtils.isEmpty(openId)) {
            throw new IllegalArgumentException("openId is null or empty");
        }
        if (!PatternHelper.isMobile(mobile)) {
            throw new IllegalArgumentException("mobile format error");
        }
        // 验证用户注册类型
        if (UserRegisterTypeEnum.getUserRegisterType(registerType) == null) {
            throw new IllegalArgumentException("registerType error");
        }
        // 验证性别
        if (GenderEnum.getGenderType(gender) == null) {
            throw new IllegalArgumentException("genderType error");
        }
        UserEntity userEntity = userDao.getUserByUserName(mobile);
        // 用户已经存在或已经注册
        if (userEntity != null) {
            throw new ServiceException(Result.USER_EXIST.getCode(), Result.USER_EXIST.getMessage());
        }
        // 验证代理商是否存在，代理商需存在库中
        AgentEntity agentEntity = agentDao.getAgentById(agentId);
        if (agentEntity == null) {
            throw new ServiceException(Result.AGENT_NOT_EXIST.getCode(), Result.AGENT_NOT_EXIST.getMessage());
        }
        // 验证用户角色，用户角色不能为空
        UserRoleEntity userRoleEntity = userRoleDao.getUserRoleById(roleId);
        if (userRoleEntity == null) {
            throw new ServiceException(Result.USER_ROLE_NOT_EXIST.getCode(), Result.USER_ROLE_NOT_EXIST.getMessage());
        }

        // 添加用户基本信息
        UserEntity newUser = this.addUser(openId, mobile, nickName, gender, realName, iconUrl);
        // 添加第三方授权记录
        this.addUserThird(newUser.getId(), openId, registerType);
        // 添加用户角色
        UserAgentRoleEntity userRole = this.addSalesAgentRole(newUser, agentEntity, userRoleEntity);

        //UserResponse result = this.getUserResponse(newUser);
        UserDetailResponse result = this.getUserDetailResponse(newUser, userRole);
        return result;
    }

    /**
     * <p>Description: 通过第三方登陆方式注册用户，健身房员工注册，员工要和健身房及角色关联。
     * 第一次关注微信公众号时，暂时先不需绑定手机或真实姓名，再次进入商城时需要绑定真实姓名和手机号
     * </p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>gender 用户性别</p>
     * <p>iconUrl 第三方头像</p>
     * <p>agentId 所有代理的ID,目前是健身房的id</p>
     * <p>roleId 所属角色id</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>date 2017/10/09 14:00 </p>
     * <p>return </p>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetailResponse addSalesByThird(String openId, String nickName, int gender, String iconUrl,
                                              int agentId, int roleId, int registerType) throws ServiceException, Exception {
        if (StringUtils.isEmpty(openId)) {
            throw new IllegalArgumentException("openId is null or empty");
        }
        // 验证用户注册类型
        if (UserRegisterTypeEnum.getUserRegisterType(registerType) == null) {
            throw new IllegalArgumentException("registerType error");
        }
        // 验证性别
        if (GenderEnum.getGenderType(gender) == null) {
            throw new IllegalArgumentException("genderType error");
        }

        UserEntity userEntity = userDao.getUserByOpenId(openId, registerType);
        // 用户已经存在或已经注册
        if (userEntity != null) {
            throw new ServiceException(Result.USER_EXIST.getCode(), Result.USER_EXIST.getMessage());
        }
        // 验证代理商是否存在，代理商需存在库中
        AgentEntity agentEntity = agentDao.getAgentById(agentId);
        if (agentEntity == null) {
            throw new ServiceException(Result.AGENT_NOT_EXIST.getCode(), Result.AGENT_NOT_EXIST.getMessage());
        }

        // 不能在最顶级的代理添加，员工的角色
        if (agentEntity.getParentId() == 0) {
            // 按角色及agentId 查询是否在管理帐户
            //userAgentRoleDao.getUserAgentRoleByAgentIdAndRoleId(agentEntity.getId(), UserRoleEnum.MANAGER.getIndex());
            throw new ServiceException(Result.AGENT_CAN_NOT_ADD_SALES.getCode(), Result.AGENT_CAN_NOT_ADD_SALES.getMessage());
        }

        // 验证用户角色，用户角色不能为空
        UserRoleEntity userRoleEntity = userRoleDao.getUserRoleById(roleId);
        if (userRoleEntity == null) {
            throw new ServiceException(Result.USER_ROLE_NOT_EXIST.getCode(), Result.USER_ROLE_NOT_EXIST.getMessage());
        }
        // 门店下只能添加，店员的相关角色
        if (userRoleEntity.getId() == UserRoleEnum.USER.getIndex() ||
                userRoleEntity.getId() == UserRoleEnum.MANAGER.getIndex()) {
            throw new ServiceException(Result.USER_ROLE_NOT_CORRECT.getCode(), Result.USER_ROLE_NOT_CORRECT.getMessage());
        }

        // 添加用户基本信息(先将openId 设置成为用户，后结待绑定手机号时再进行更新)
        UserEntity newUser = this.addUser(openId, "", nickName, gender, "", iconUrl);
        // 添加第三方授权记录
        this.addUserThird(newUser.getId(), openId, registerType);
        // 添加用户角色
        UserAgentRoleEntity userRole = this.addSalesAgentRole(newUser, agentEntity, userRoleEntity);

        //UserResponse result = this.getUserResponse(newUser);
        UserDetailResponse result = this.getUserDetailResponse(newUser, userRole);

        return result;
    }

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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetailResponse addManagerByThird(String openId, String nickName, int gender, String iconUrl, int agentId, int registerType) throws ServiceException, Exception {
        if (StringUtils.isEmpty(openId)) {
            throw new IllegalArgumentException("openId is null or empty");
        }
        // 验证用户注册类型
        if (UserRegisterTypeEnum.getUserRegisterType(registerType) == null) {
            throw new IllegalArgumentException("registerType error");
        }
        // 验证性别
        if (GenderEnum.getGenderType(gender) == null) {
            throw new IllegalArgumentException("genderType error");
        }

        UserEntity userEntity = userDao.getUserByOpenId(openId, registerType);
        // 用户已经存在或已经注册
        if (userEntity != null) {
            throw new ServiceException(Result.USER_EXIST.getCode(), Result.USER_EXIST.getMessage());
        }
        // 验证代理商是否存在，代理商需存在库中
        AgentEntity agentEntity = agentDao.getAgentById(agentId);
        if (agentEntity == null) {
            throw new ServiceException(Result.AGENT_NOT_EXIST.getCode(), Result.AGENT_NOT_EXIST.getMessage());
        }

        // 不能在最顶级的代理添加，员工的角色
        if (agentEntity.getParentId() != 0) {
            // 按角色及agentId 查询是否在管理帐户
            //userAgentRoleDao.getUserAgentRoleByAgentIdAndRoleId(agentEntity.getId(), UserRoleEnum.MANAGER.getIndex());
            throw new ServiceException(Result.AGENT_CAN_NOT_ADD_SALES.getCode(), Result.AGENT_CAN_NOT_ADD_SALES.getMessage());
        }

        List<UserAgentRoleEntity> userRoleEntityList = userAgentRoleDao.getUserAgentRoleByAgentIdAndRoleId(agentEntity.getId(), UserRoleEnum.MANAGER.getIndex());
        if (userRoleEntityList.size() >= 1) {
            throw new ServiceException(Result.AGENT_CAN_NOT_ADD_MULTI_MANAGER.getCode(), Result.AGENT_CAN_NOT_ADD_MULTI_MANAGER.getMessage());
        }
        // 获取管理员的角色
        UserRoleEntity userRoleEntity = userRoleDao.getUserRoleById(UserRoleEnum.MANAGER.getIndex());
        // 添加用户基本信息(先将openId 设置成为用户，后结待绑定手机号时再进行更新)
        UserEntity newUser = this.addUser(openId, "", nickName, gender, "", iconUrl);
        // 添加第三方授权记录
        this.addUserThird(newUser.getId(), openId, registerType);
        // 添加用户角色
        UserAgentRoleEntity userRole = this.addSalesAgentRole(newUser, agentEntity, userRoleEntity);

        //UserResponse result = this.getUserResponse(newUser);
        UserDetailResponse result = this.getUserDetailResponse(newUser, userRole);

        return result;
    }


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
    @Override
    public UserDetailResponse addUserWithoutSalesByThird(String openId, String nickName, int gender,
                                                         String iconUrl, int registerType) throws ServiceException, Exception {

        if (StringUtils.isEmpty(openId)) {
            throw new IllegalArgumentException("openId is null or empty");
        }
        // 验证用户注册类型
        if (UserRegisterTypeEnum.getUserRegisterType(registerType) == null) {
            throw new IllegalArgumentException("registerType error");
        }
        // 验证性别
        if (GenderEnum.getGenderType(gender) == null) {
            throw new IllegalArgumentException("genderType error");
        }

        UserEntity userEntity = userDao.getUserByOpenId(openId, registerType);
        // 用户已经存在或已经注册
        if (userEntity != null) {
            throw new ServiceException(Result.USER_EXIST.getCode(), Result.USER_EXIST.getMessage());
        }

        // 第一次关注，没有绑定手机号做为用户名，所以先将openId做为用户名
        UserEntity newUser = this.addUser(openId, "", nickName, gender, "", iconUrl);

        // 添加第三方授权记录
        this.addUserThird(newUser.getId(), openId, registerType);

        // 添加用户角色
        UserAgentRoleEntity userRole = this.addUserAgentRoleWithoutSales(newUser);

        //UserResponse result = this.getUserResponse(newUser);
        UserDetailResponse result = this.getUserDetailResponse(newUser, userRole);
        return result;
    }
    /**
     * <p>Description: 通过第三方登陆方式注册用户，普通用户注册。不于业务人员或健身房产生关系
     * 第一次关注微信公众号时，暂时先不需绑定手机或真实姓名，再次进入商城时需要绑定真实姓名和手机号
     * </p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>gender 性别 0女， 1男</p>
     * <p>iconUrl 第三方头像</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/1 15:51</p>
     * */
    public UserDetailResponse addUserWithoutSalesByApp(String openId,String nickName,
                                                       int gender, String iconUrl, int registerType,String unionId)throws ServiceException, Exception{

        if (StringUtils.isEmpty(openId)) {
            throw new IllegalArgumentException("openId is null or empty");
        }
        // 验证用户注册类型
        if (UserRegisterTypeEnum.getUserRegisterType(registerType) == null) {
            throw new IllegalArgumentException("registerType error");
        }
        // 验证性别
        if (GenderEnum.getGenderType(gender) == null) {
            throw new IllegalArgumentException("genderType error");
        }
        if(StringUtils.isEmpty(unionId)){
            throw new ServiceException(3001,"union is null or empty");
        }

        UserEntity userEntity = userDao.getUserByOpenId(openId, registerType);
        // 用户已经存在或已经注册
        if (userEntity != null) {
            throw new ServiceException(Result.USER_EXIST.getCode(), Result.USER_EXIST.getMessage());
        }

        // 第一次关注，没有绑定手机号做为用户名，所以先将openId做为用户名
        UserEntity newUser = this.addUser(openId, "", nickName, gender, "", iconUrl);

        // 添加第三方授权记录
        UserThirdEntity thirdEntity=this.addAppUser(newUser.getId(),openId,unionId,"",1);

        // 添加用户角色
        UserAgentRoleEntity userRole = this.addUserAgentRoleWithoutSales(newUser);

        //UserResponse result = this.getUserResponse(newUser);
        UserDetailResponse result = this.getUserDetailResponse(newUser, userRole);
        result.setUnionId(thirdEntity.getUnionId()==null?"":thirdEntity.getUnionId().toString());
        result.setAppOpenId(thirdEntity.getAppOpenId()==null?"":thirdEntity.getAppOpenId().toString());
        result.setOpenId(openId);
        return result;
    }
    // 构造用户详情返回结果
    private UserDetailResponse getUserDetailResponse(UserEntity user, UserAgentRoleEntity agentRoleEntity) {
        UserDetailResponse result = new UserDetailResponse();
        //由于涉及两个类型，需要独立赋值
        // 用户的
        result.setId(user.getId());
        result.setUserName(user.getUserName());
        result.setNickName(user.getNickName());
        result.setRealName(user.getRealName());
        result.setGender(user.getGender());
        result.setIconUrl(user.getIconUrl());
        result.setEmail(user.getEmail());
        result.setMobile(user.getMobile());
        result.setCreateTime(user.getCreateTime());
        result.setLoginTime(user.getLoginTime());
        // 角色相关
        result.setAgentId(agentRoleEntity.getAgentId());
        result.setAgentName(agentRoleEntity.getAgentName());
        result.setSalesId(agentRoleEntity.getSalesId());
        result.setSalesName(agentRoleEntity.getSalesName());
        result.setRoleId(agentRoleEntity.getRoleId());
        result.setRoleName(agentRoleEntity.getRoleName());
        result.setReferrerId(agentRoleEntity.getReferrerId());
        result.setRoleName(agentRoleEntity.getReferrerName());
        UserThirdEntity thirdEntity=userThirdDao.selectByUserId(user.getId());
        String appOpenId=thirdEntity.getAppOpenId()==null?"":thirdEntity.getAppOpenId().toString();
        String unionId=thirdEntity.getUnionId()==null?"":thirdEntity.getUnionId().toString();
        result.setAppOpenId(appOpenId);
        result.setUnionId(unionId);
        return result;
    }

    // 添加一个直接关注的用户，不合任何业务人员，代理商有关系
    private UserAgentRoleEntity addUserAgentRoleWithoutSales(UserEntity user) {
        UserAgentRoleEntity result = new UserAgentRoleEntity();
        result.setUserId(user.getId());
        result.setUserName(user.getUserName());
        result.setCreateTime(new Date());

        result.setRoleId(UserRoleEnum.USER.getIndex());
        result.setRoleName(UserRoleEnum.USER.getName());
        result.setCreateTime(new Date());
        userAgentRoleDao.addUserAgentRole(result);
        return result;
    }

    // 添加代理商员工角色(C推C用户需要记录推荐用户的信息)
    private UserAgentRoleEntity addSalesAgentRole(UserEntity user, UserDetailEntity referrerUser) {
        UserAgentRoleEntity result = new UserAgentRoleEntity();
        result.setUserId(user.getId());
        result.setUserName(user.getUserName());
        result.setCreateTime(new Date());
        // 代理信息
        result.setAgentId(referrerUser.getAgentId());
        result.setAgentName(referrerUser.getAgentName());
        //业务人员的信息
        result.setSalesId(referrerUser.getSalesId());
        result.setSalesName(referrerUser.getSalesName());
        // 普通用户角色
        result.setRoleId(UserRoleEnum.USER.getIndex());
        result.setRoleName(UserRoleEnum.USER.getName());
        // 推荐人信息
        result.setReferrerId(referrerUser.getId());
        result.setReferrerName(referrerUser.getUserName());

        result.setCreateTime(new Date());
        userAgentRoleDao.addUserAgentRole(result);
        return result;
    }

    // 添加代理商员工角色
    private UserAgentRoleEntity addManagerAgentRole(UserEntity user, AgentEntity agent) {
        UserAgentRoleEntity result = new UserAgentRoleEntity();
        result.setUserId(user.getId());
        result.setUserName(user.getUserName());
        result.setCreateTime(new Date());
        result.setAgentId(agent.getId());
        result.setAgentName(agent.getAgentName());
        result.setRoleId(UserRoleEnum.MANAGER.getIndex());
        result.setRoleName(UserRoleEnum.MANAGER.getName());
        result.setCreateTime(new Date());
        userAgentRoleDao.addUserAgentRole(result);
        return result;
    }

    // 添加代理商员工角色
    private UserAgentRoleEntity addSalesAgentRole(UserEntity user, AgentEntity agent, UserRoleEntity userRole) {
        UserAgentRoleEntity result = new UserAgentRoleEntity();
        result.setUserId(user.getId());
        result.setUserName(user.getUserName());
        result.setCreateTime(new Date());
        result.setAgentId(agent.getId());
        result.setAgentName(agent.getAgentName());
        result.setRoleId(userRole.getId());
        result.setRoleName(userRole.getRoleName());
        result.setCreateTime(new Date());
        userAgentRoleDao.addUserAgentRole(result);
        return result;
    }

    // 添加普通用户角色（需要以业务人员，代理商，相关联）
    private UserAgentRoleEntity addUserAgentRole(UserEntity user, UserDetailEntity sales) {
        UserAgentRoleEntity result = new UserAgentRoleEntity();
        //用户信息
        result.setUserId(user.getId());
        result.setUserName(user.getUserName());
        result.setCreateTime(new Date());
        // 与代理商关联
        result.setAgentId(sales.getAgentId());
        result.setAgentName(sales.getAgentName());
        // 从杖举值中获取，不用查库，但数据要保持同步
        result.setRoleId(UserRoleEnum.USER.getIndex());
        result.setRoleName(UserRoleEnum.USER.getName());
        // 与业务人员关联
        result.setSalesId(sales.getId());
        result.setSalesName(sales.getUserName());
        result.setCreateTime(new Date());
        userAgentRoleDao.addUserAgentRole(result);
        return result;
    }

    // 添加第三方授权记录
    private UserThirdEntity addUserThird(int userId, String openId, int registerType) {
        UserThirdEntity result = new UserThirdEntity();
        // 添加第三方用户授权记录
        result.setUserId(userId);
        result.setCreateTime(new Date());
        result.setOpenId(openId);
        result.setRegisterType(registerType);
        userThirdDao.addUserThird(result);
        return result;
    }
    //添加第三方app用户
    private UserThirdEntity addAppUser(Integer userId,String openId,String unionId,String appOpenId,int registerType){
        UserThirdEntity result = new UserThirdEntity();
        // 添加第三方app用户授权记录
        result.setUserId(userId);
        result.setCreateTime(new Date());
        result.setOpenId(openId);
        result.setUnionId(unionId);
        result.setAppOpenId(appOpenId);
        result.setRegisterType(registerType);
        userThirdDao.addUserThird(result);
        return result;
    }

    // 添加用户基本信息
    private UserEntity addUser(String userName, String mobile, String nickName, int gender, String realName, String iconUrl) throws Exception {
        UserEntity result = new UserEntity();
        // 手机号做为用户的用户名
        result.setUserName(userName);
        // 设置绑定的手机号
        result.setMobile(mobile);
        // 用户注册时间
        result.setCreateTime(new Date());
        // 最后登陆时间
        result.setLoginTime(new Date());
        // 设置第三方的昵称或头偈
        if (StringUtils.isNotEmpty(nickName)) {
            result.setNickName(EncryptUtil.getBase64(nickName));
        }
        result.setGender(gender);
        if (StringUtils.isNotEmpty(iconUrl)) {
            result.setIconUrl(iconUrl);
        }
        if (StringUtils.isNotEmpty(realName)) {
            result.setRealName(realName);
        }

        userDao.addUser(result);

        // 处理密码(密码暂时设定为666666)
        String defaultPassword = "666666";// 用户第三注册时的默认密码
        String encryptData = encryptUtil.getEncryptString(defaultPassword);
        String password = encryptUtil.registerEncrypt(encryptUtil.getEncryptConfigKey(), Long.valueOf(result.getId()), encryptData);
        userDao.updatePasswordByUserId(result.getId(), password);//更新用户密码

        return result;
    }
    // 添加用户基本信息
    private UserEntity addUser(String userName,String pass, String mobile, String nickName, int gender, String realName, String iconUrl) throws Exception {
        UserEntity result = new UserEntity();
        // 手机号做为用户的用户名
        result.setUserName(userName);
        // 设置绑定的手机号
        result.setMobile(mobile);
        // 用户注册时间
        result.setCreateTime(new Date());
        // 最后登陆时间
        result.setLoginTime(new Date());
        result.setGender(gender);
        // 设置第三方的昵称或头像
        if (StringUtils.isNotEmpty(nickName)) {
            result.setNickName(EncryptUtil.getBase64(nickName));
        }
        if (StringUtils.isNotEmpty(iconUrl)) {
            result.setIconUrl(iconUrl);
        }
        if (StringUtils.isNotEmpty(realName)) {
            result.setRealName(realName);
        }
        userDao.addUser(result);
        log.info("addUser: 未加密前password="+pass+",userid="+result.getId());
        String password = encryptUtil.registerEncrypt(encryptUtil.getEncryptConfigKey(), Long.valueOf(result.getId()), pass);
        log.info("addUser: 加密后password="+password);
        userDao.updatePasswordByUserId(result.getId(), password);//更新用户密码
        return result;
    }
    /**
     * <p>Description: 根据第三方OpenId及登陆类型查询用户信息</p>
     * <p>openId 第三方OpenId</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	</p>
     * <p>date 2017/9/21 19:00 </p>
     * <p>return UserEntity</p>
     */
    @Override
    public UserResponse getUserByOpenId(String openId, int registerType) throws ServiceException, Exception {

        // 验证openId(为空的情况)
        if (StringUtils.isEmpty(openId)) {
            throw new IllegalArgumentException("openId is null or empty");
        }
        // 验证用户注册类型
        if (UserRegisterTypeEnum.getUserRegisterType(registerType) == null) {
            throw new IllegalArgumentException("registerType error");
        }

        // 查询用户信息
        UserEntity userEntity = userDao.getUserByOpenId(openId, registerType);
        UserResponse result = null;
        if (userEntity == null) {
            return result;
        }
        result = getUserResponse(userEntity);

        return result;
    }
    /**
     *<p>Description:通过unionid查询用户信息</p>
     *<p>param unionId:全局id</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/1 13:53</p>
     *<p>return:UserDetailResponse</p>
     */
    public UserDetailResponse  getUserByUnionId(String unionId,int registerType) throws ServiceException,Exception{
        if(StringUtils.isEmpty(unionId)){
            throw new ServiceException(2001,"unionId is null or empty");
        }
        if(registerType<=0){
            throw new ServiceException(2002,"registerType is null or empty");
        }
        UserDetailEntity entity= userDao.getUserByUnionId(unionId,registerType);
        UserDetailResponse response=new UserDetailResponse();
        if(entity!=null &&entity.getId()>0){
            BeanUtils.copyProperties(entity,response);
            return response;
        }
        else{
            return null;
        }
    }

    /**
     * <p>Description: 根据手机号码查询用户信息</p>
     * <p>mobile 用户手机号</p>
     * <p>date 2017/10/18 10:00 </p>
     * <p>return UserResponse</p>
     */
    @Override
    public UserResponse getUserByMobile(String mobile) throws ServiceException, Exception {
        if (StringUtils.isEmpty(mobile)) {
            throw new IllegalArgumentException("openId is null or empty");
        }
        if (StringUtils.isEmpty(mobile)) {
            throw new IllegalArgumentException("mobile is null or empty");
        }
        if (!PatternHelper.isMobile(mobile)) {
            throw new IllegalArgumentException("mobile format error");
        }

        // 查询用户信息
        UserEntity userEntity = userDao.getUserByMobile(mobile);
        UserResponse result = null;
        if (userEntity == null) {
            return result;
        }
        result = getUserResponse(userEntity);

        return result;
    }

    /**
     * <p>Description: 根据用户名查询用户信息</p>
     * <p>userName 用户名</p>
     * <p>date 2017/9/21 16:53 </p>
     * <p>return UserResponse</p>
     */
    @Override
    public UserResponse getUserByUserName(String userName) throws ServiceException, Exception {
        // 验证userName
        if (StringUtils.isEmpty(userName)) {
            throw new IllegalArgumentException("userName is null or empty");
        }
        // 查询用户信息
        UserEntity userEntity = userDao.getUserByUserName(userName);
        UserResponse result = null;
        if (userEntity == null) {
            return result;
        }
        result = getUserResponse(userEntity);

        return result;
    }
    /**
     * <p>Description: 根据mobile查询用户信息</p>
     * <p>mobile 用户手机</p>
     * <p>date 2017/11/22 16:53 </p>
     * <p>return UserResponse</p>
     *
     * @throws ServiceException
     */
    public UserDetailResponse getUserDetailByMobile(String  mobile) throws Exception{
        if(StringUtils.isEmpty(mobile)||!PatternHelper.isMobile(mobile)){
            throw new IllegalArgumentException("mobile format error!");
        }
        // 查询用户信息
        UserEntity userEntity = userDao.getUserByMobile(mobile);
        if (userEntity==null) {
            return null;
        }
        UserAgentRoleEntity userAgentRoleEntity = userAgentRoleDao.getUserAgentRoleByUserId(userEntity.getId());
        if (userAgentRoleEntity == null) {
            throw new ServiceException(Result.USER_AGENT_ROLE_NOT_EXIST.getCode(), Result.USER_AGENT_ROLE_NOT_EXIST.getMessage());
        }

        UserDetailResponse result = this.getUserDetailResponse(userEntity, userAgentRoleEntity);
        return result;
    }

    /**
     * <p>Description: 获取用户返回实体</p>
     * <p>param entity 用户参数实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017年09月21日 下午14:39</p>
     * <p>return UserResponse</p>
     */
    private UserResponse getUserResponse(UserEntity entity) {
        UserResponse result = new UserResponse();
        BeanUtils.copyProperties(entity, result);
        return result;
    }

    /**
     * <p>Description: 获取用户详情返回实体</p>
     * <p>param entity 用户详情参数实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017年09月22日 下午13:39</p>
     * <p>return UserDetailResponse</p>
     */
    private UserDetailResponse getUserDetailResponse(UserDetailEntity entity) {
        UserDetailResponse result = new UserDetailResponse();
        BeanUtils.copyProperties(entity, result);
        return result;
    }

    /**
     * <p>Description: 根据用户名更新用户最后登陆时间</p>
     * <p>userName 用户名</p>
     * <p>date 2017/9/21 13:00 </p>
     * <p>return boolean</p>
     */
    @Override
    public boolean updateUserLoginTime(String userName) throws ServiceException, Exception {
        // 验证openId
        if (StringUtils.isEmpty(userName)) {
            throw new IllegalArgumentException("userName is null or empty");
        }
        int count = userDao.updateUserLoginTime(userName);
        return (count > 0);
    }
    /**
     *绑定手机号
     * @param mobile 手机号码
     * @param id 用户id
     * @return int
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateUserMobileById(String mobile,int id)throws Exception{
        if (!PatternHelper.isMobile(mobile)) {
            throw new Exception("mobile format error");
        }
        if(id<=0)
        {
            throw new Exception("id is error");
        }
        UserDetailEntity entity=userDao.getUserDetailByUserId(id);
        if(entity==null)
        {
            throw new Exception("id can't find");
        }
        // 查询用户信息
        UserEntity userEntity = userDao.getUserByMobile(mobile);
        if (userEntity != null) {
            if(userEntity.getId()!=id)
            throw new Exception("mobile have been used");
        }
        return userDao.updateUserMobileById(mobile,id);
    }
    /**
     *<p>Description:通过手机号查询用户邀新信息</p>
     *<p>param mobile:</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/19 14:27</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
   @Transactional(readOnly = true)
    public InviteNewResponse getInviteNewByMobile(String mobile) throws Exception {
       if (StringUtils.isEmpty(mobile)) {
           throw new Exception("mobile is null or empty");
       }
       if (!PatternHelper.isMobile(mobile)) {
           throw new Exception("mobile format error");
       }
        InviteNewEntity entity=userDao.getInviteNewByMobile(mobile);
       InviteNewResponse res=new InviteNewResponse();
       if(entity!=null){
           if(StringUtils.isNotEmpty(entity.getSaleName()))
           {
               entity.setSaleName(EncryptUtil.getFromBase64(entity.getSaleName()));
           }
           if(StringUtils.isNotEmpty(entity.getNickName()))
           {
               entity.setNickName(EncryptUtil.getFromBase64(entity.getNickName()));
           }
           BeanUtils.copyProperties(entity,res);
           return res;
       }
       else{
           return null;
       }

    }

    // 根据openId更新用户的手机号
    @Override
    public UserDetailResponse updateUserMobileByOpenId(String openId, String mobile,
                                                       String realName, int registerType) throws ServiceException, Exception {
        if (StringUtils.isEmpty(openId)) {
            throw new IllegalArgumentException("openId is null or empty");
        }
        if (StringUtils.isEmpty(mobile)) {
            throw new IllegalArgumentException("mobile is null or empty");
        }
        if (!PatternHelper.isMobile(mobile)) {
            throw new IllegalArgumentException("mobile format error");
        }
        if (StringUtils.isEmpty(realName)) {
            throw new IllegalArgumentException("realName is null or empty");
        }
        // 验证用户注册类型
        if (UserRegisterTypeEnum.getUserRegisterType(registerType) == null) {
            throw new IllegalArgumentException("registerType error");
        }

        // 验让手机是否绑定过
        UserDetailEntity user = userDao.getUserDetailByMobile(mobile);
        if (user != null) {
            throw new ServiceException(Result.USER_PHONE_EXIST.getCode(), Result.USER_PHONE_EXIST.getMessage() + mobile);
        }
        // 用户是否否存在
        user = userDao.getUserDetailByOpenId(openId, registerType);
        if (user == null) {
            throw new ServiceException(Result.USER_NOT_EXIST.getCode(), Result.USER_NOT_EXIST.getMessage() + mobile);
        }

        userDao.updateUserMobileByOpenId(openId, mobile, realName, registerType);
        // 设置更新的手机号，不用再取数据
        user.setMobile(mobile);
        UserDetailResponse result = this.getUserDetailResponse(user);
        return result;
    }

    /**
     * <p>Description: 根据用户名查询用户信息</p>
     * <p>userName 用户名</p>
     * <p>date 2017/9/21 16:53 </p>
     * <p>return UserDetailResponse</p>
     */
    public UserDetailResponse getUserDetailByUserName(String userName) throws ServiceException, Exception {
        // 验证userName
        if (StringUtils.isEmpty(userName)) {
            throw new IllegalArgumentException("userName is null or empty");
        }
        // 查询用户信息
        UserDetailEntity userEntity = userDao.getUserDetailByUserName(userName);
        UserDetailResponse result = null;
        if (userEntity == null) {
            return result;
        }
        result = getUserDetailResponse(userEntity);

        return result;
    }
    /**
     *<p>Description:查询所有销售</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/29 13:28</p>
     *<p>return:</p>
     */
    public UserDetailListResponse getAllSales() throws ServiceException, Exception{
        UserDetailListResponse response=new UserDetailListResponse();
        List<UserDetailEntity> list=userDao.getUserDetailByRoleId(UserRoleEnum.EMPLOYEE.getIndex());
        response.setList(list);
        return response;
    }
    /**
     *<p>Description:</p>
     * <p>param type: 1: 注册30天以内会员（含30天） 2：注册30天以上会员 3：所有销售</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/13 15:17</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public UserDetailListResponse getCouponUserList(int type){
        if(type!=CouponUserTypeEnum.SALES_ALL.getIndex() &&type!=CouponUserTypeEnum.USER_THIRTYDAY_GT.getIndex()  &&type!=CouponUserTypeEnum.USER_THIRTYDAY_IN.getIndex() ){
            throw new ServiceException(1001,"type's value is error");
        }
        UserDetailListResponse response=new UserDetailListResponse();
        if(type==CouponUserTypeEnum.USER_THIRTYDAY_IN.getIndex()){
            List<UserDetailEntity> list=userDao.getUserDetailByRoleIdOrDate(UserRoleEnum.USER.getIndex(),1);
            response.setList(list);
        }
        else if(type==CouponUserTypeEnum.USER_THIRTYDAY_GT.getIndex()){
            List<UserDetailEntity> list=userDao.getUserDetailByRoleIdOrDate(UserRoleEnum.USER.getIndex(),2);
            response.setList(list);
        }
        else if(type==CouponUserTypeEnum.SALES_ALL.getIndex()){
            List<UserDetailEntity> list=userDao.getUserDetailByRoleIdOrDate(UserRoleEnum.EMPLOYEE.getIndex(),null);
            response.setList(list);
        }
        return response;
    }

    /**
     * <p>Description: 根据userId查询用户信息</p>
     * <p>userId 用户Id</p>
     * <p>date 2017/10/11 16:53 </p>
     * <p>return UserResponse</p>
     */
    @Override
    public UserDetailResponse getUserDetailByUserId(int userId) throws ServiceException {
        // 验证userId
        if (userId <= 0) {
            throw new IllegalArgumentException("userId is illega");
        }
        // 查询用户信息
        UserDetailEntity userEntity = userDao.getUserDetailByUserId(userId);
        UserDetailResponse result = null;
        if (userEntity == null) {
            return result;
        }
        result = getUserDetailResponse(userEntity);

        return result;
    }
    /**
     * <p>Description: 根据userId查询用户信息</p>
     * <p>userId 用户Id</p>
     * <p>date 2017/12/27 16:53 </p>
     * <p>return UserResponse</p>
     *
     * @throws ServiceException
     */
    public ManagerDetailResponse getManagerDetailByUserId(int userId) throws ServiceException,Exception{
        ManagerDetailResponse response=null;
        // 验证userId
        if (userId <= 0) {
            throw new IllegalArgumentException("userId is illega");
        }
        // 查询用户信息
        ManagerDetailEntity userEntity = userDao.getManagerDetailById(userId);
        if(userEntity!=null){
            response=new ManagerDetailResponse();
            String nickName=userEntity.getNickName();
            if(StringUtils.isNotEmpty(nickName)){
                userEntity.setNickName(EncryptUtil.getFromBase64(nickName));
            }
            String receiverNickname=userEntity.getReceiverNickname();
            if(StringUtils.isNotEmpty(receiverNickname)){
                userEntity.setReceiverNickname(EncryptUtil.getFromBase64(receiverNickname));
            }
            BeanUtils.copyProperties(userEntity,response);
        }
        return response;
    }
    /**
     * <p>Description: 根据第三方注册openId查询用户信息</p>
     * <p>openId 第三方注册用户openId</p>
     * <p>date 2017/10/13 16:53 </p>
     * <p>return UserDetailResponse</p>
     * <p>throws ServiceException</p>
     */
    @Override
    public UserDetailResponse getUserDetailByOpenId(String openId, int registerType) throws ServiceException {
        if (StringUtils.isEmpty(openId)) {
            throw new IllegalArgumentException("openId is null or empty");
        }
        // 验证用户注册类型
        if (UserRegisterTypeEnum.getUserRegisterType(registerType) == null) {
            throw new IllegalArgumentException("registerType error");
        }
        // 查询用户信息
        UserDetailEntity userEntity = userDao.getUserDetailByOpenId(openId, registerType);
        UserDetailResponse result = null;
        if (userEntity == null) {
            return result;
        }
        result = getUserDetailResponse(userEntity);
        return result;
    }
    /**
     *<p>Description:更新third表</p>
     *<p>param unionId:全局id</p>
     *<p>param appOpenId:app的openid</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/1 16:23</p>
     *<p>return:</p>
     */
    @Transactional(rollbackFor ={ Exception.class,ServiceException.class})
    public int updateUserThird(String openId,String unionId,String appOpenId)throws ServiceException, Exception{
        if(StringUtils.isEmpty(openId)){
            throw new ServiceException(3001,"openId is null or empty");
        }
        if(StringUtils.isEmpty(unionId)){
            throw new ServiceException(3002,"union is null or empty");
        }
        int flag=userThirdDao.updateUserThird(openId,unionId,appOpenId);
        return flag;
    }
    /**
     * <p>Description: 根据手机号,更新用户密码</p>
     * <p>mobile 用户手机号</p>
     * <p>password 用户密码，base64后的字符串</p>
     * <p>date 2017/10/19 16:53 </p>
     * <p>return boolean</p>
     */
    @Override
    public boolean updateUserPasswordByMobile(String mobile, String password) throws ServiceException, Exception {
        if (StringUtils.isEmpty(mobile)) {
            throw new IllegalArgumentException("mobile is null or empty");
        }
        if (!PatternHelper.isMobile(mobile)) {
            throw new IllegalArgumentException("mobile format error");
        }
        if (StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("password is null or empty");
        }
        UserEntity user = userDao.getUserByMobile(mobile);
        if (user == null) {
            throw new ServiceException(Result.USER_PHONE_EXIST.getCode(), Result.USER_PHONE_EXIST.getMessage() + mobile);
        }
        // 密码加密
        String encryptPassword = encryptUtil.registerEncrypt(encryptUtil.getEncryptConfigKey(), Long.valueOf(user.getId()), password);
        int count = userDao.updateUserPasswordByMobile(mobile, encryptPassword);
        return (count > 0);
    }

    /**
     * <p>Description: 根据用户id更新管理人员的关系信息,更新角色及所在代理商</p>
     * <p>userId 用户名id</p>
     * <p>agentId 代理商id</p>
     * <p>date 2017/10/17 10:00 </p>
     * <p>return boolean</p>
     */
    public boolean updateManagerAgentRoleByUserId(int userId, int agentId) throws ServiceException, Exception {
        if (userId <= 0) {
            throw new IllegalArgumentException("userId is illegal");
        }
        if (agentId <= 0) {
            throw new IllegalArgumentException("agentId is illegal");
        }
        // 验证代理商是否存在，代理商需存在库中
        AgentEntity agentEntity = agentDao.getAgentById(agentId);
        if (agentEntity == null) {
            throw new ServiceException(Result.AGENT_NOT_EXIST.getCode(), Result.AGENT_NOT_EXIST.getMessage());
        }

        // 不能在最顶级的代理添加，员工的角色
        if (agentEntity.getParentId() != 0) {
            throw new ServiceException(Result.AGENT_CAN_NOT_ADD_SALES.getCode(), Result.AGENT_CAN_NOT_ADD_SALES.getMessage());
        }
        // 验证用户
        UserEntity userEntity = userDao.getUserById(userId);
        if (userEntity == null) {
            throw new ServiceException(Result.USER_NOT_EXIST.getCode(), Result.USER_NOT_EXIST.getMessage());
        }

        UserAgentRoleEntity userAgentRoleEntity = userAgentRoleDao.getUserAgentRoleByUserId(userId);
        if (userAgentRoleEntity == null) {
            throw new ServiceException(Result.USER_AGENT_ROLE_NOT_EXIST.getCode(), Result.USER_AGENT_ROLE_NOT_EXIST.getMessage());
        }
        int result = userAgentRoleDao.updateSalesAgentRoleByUserId(userId, agentEntity.getId(), agentEntity.getAgentName(),
                UserRoleEnum.MANAGER.getIndex(), UserRoleEnum.MANAGER.getName());
        return result > 0;
    }

    /**
     * <p>Description: 根据用户id更新业务人员的关系信息,更新角色及所在代理商</p>
     * <p>userId 用户名id</p>
     * <p>agentId 代理商id</p>
     * <p>roleId 业务人员角色id</p>
     * <p>date 2017/10/17 10:00 </p>
     * <p>return boolean</p>
     */
    @Override
    public boolean updateSalesAgentRoleByUserId(int userId, int agentId, int roleId) throws ServiceException, Exception {
        if (userId <= 0) {
            throw new IllegalArgumentException("userId is illegal");
        }
        if (agentId <= 0) {
            throw new IllegalArgumentException("agentId is illegal");
        }

        // 验证用户角色，用户角色不能为空
        UserRoleEntity userRoleEntity = userRoleDao.getUserRoleById(roleId);
        if (userRoleEntity == null) {
            throw new ServiceException(Result.USER_ROLE_NOT_EXIST.getCode(), Result.USER_ROLE_NOT_EXIST.getMessage());
        }
        // 门店下只能更新，店员的相关角色
        if (userRoleEntity.getId() == UserRoleEnum.USER.getIndex() ||
                userRoleEntity.getId() == UserRoleEnum.MANAGER.getIndex()) {
            throw new ServiceException(Result.USER_ROLE_NOT_CORRECT.getCode(), Result.USER_ROLE_NOT_CORRECT.getMessage());
        }

        // 验证代理商是否存在，代理商需存在库中
        AgentEntity agentEntity = agentDao.getAgentById(agentId);
        if (agentEntity == null) {
            throw new ServiceException(Result.AGENT_NOT_EXIST.getCode(), Result.AGENT_NOT_EXIST.getMessage());
        }

        // 不能在最顶级的代理添加，员工的角色
        if (agentEntity.getParentId() == 0) {
            // 按角色及agentId 查询是否在管理帐户
            //userAgentRoleDao.getUserAgentRoleByAgentIdAndRoleId(agentEntity.getId(), UserRoleEnum.MANAGER.getIndex());
            throw new ServiceException(Result.AGENT_CAN_NOT_ADD_SALES.getCode(), Result.AGENT_CAN_NOT_ADD_SALES.getMessage());
        }

        // 验证用户
        UserEntity userEntity = userDao.getUserById(userId);
        if (userEntity == null) {
            throw new ServiceException(Result.USER_NOT_EXIST.getCode(), Result.USER_NOT_EXIST.getMessage());
        }

        UserAgentRoleEntity userAgentRoleEntity = userAgentRoleDao.getUserAgentRoleByUserId(userId);
        if (userAgentRoleEntity == null) {
            throw new ServiceException(Result.USER_AGENT_ROLE_NOT_EXIST.getCode(), Result.USER_AGENT_ROLE_NOT_EXIST.getMessage());
        }

        int result = userAgentRoleDao.updateSalesAgentRoleByUserId(userId, agentEntity.getId(), agentEntity.getAgentName(),
                userRoleEntity.getId(), userRoleEntity.getRoleName());
        return result > 0;
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
     * <p>date 2017/10/17 10:00 </p>
     * <p>return boolean</p>
     */
    @Override
    public boolean updateUserAgentRoleByUserId(int userId, int salesId) throws ServiceException, Exception {
        if (userId <= 0) {
            throw new IllegalArgumentException("userId is illegal");
        }
        if (salesId <= 0) {
            throw new IllegalArgumentException("salesId is illegal");
        }

        // 验证用户
        UserDetailEntity userDetailEntity = userDao.getUserDetailByUserId(userId);
        if (userDetailEntity == null) {
            throw new ServiceException(Result.USER_NOT_EXIST.getCode(), Result.USER_NOT_EXIST.getMessage());
        }
        // user必须是普通用户或者店员的角色
        if (userDetailEntity.getRoleId() != UserRoleEnum.USER.getIndex() && userDetailEntity.getRoleId()!=UserRoleEnum.EMPLOYEE.getIndex() && userDetailEntity.getRoleId()!=UserRoleEnum.MANAGER.getIndex()) {
            throw new ServiceException(Result.USER_ROLE_NOT_CORRECT.getCode(), Result.USER_ROLE_NOT_CORRECT.getMessage());
        }

        UserDetailEntity salesDetail = userDao.getUserDetailByUserId(salesId);
        if (salesDetail == null) {
            throw new ServiceException(Result.USER_AGENT_ROLE_NOT_EXIST.getCode(), Result.USER_AGENT_ROLE_NOT_EXIST.getMessage());
        }
        // 业务人员角色不正确
        if (  salesDetail.getRoleId() == UserRoleEnum.MANAGER.getIndex()) {
            throw new ServiceException(Result.USER_ROLE_NOT_CORRECT.getCode(), Result.USER_ROLE_NOT_CORRECT.getMessage());
        }
        // 业务人员没有绑定相应的关系，有角色但没有和代理有关系
        if (salesDetail.getAgentId() == 0 || StringUtils.isEmpty(salesDetail.getAgentName())) {
            throw new ServiceException(Result.SALES_AGENT_ROLE_NOT_EXIST.getCode(), Result.SALES_AGENT_ROLE_NOT_EXIST.getMessage());
        }
        int result=0;
        //普通用户角色
        if(userDetailEntity.getRoleId() == UserRoleEnum.USER.getIndex()) {
             result = userAgentRoleDao.updateUserAgentRoleByUserId(userId, salesDetail.getAgentId(), salesDetail.getAgentName(), UserRoleEnum.USER.getIndex(),
                    UserRoleEnum.USER.getName(), salesDetail.getId(), salesDetail.getUserName());
        }
        //店员角色
        else if(userDetailEntity.getRoleId() == UserRoleEnum.EMPLOYEE.getIndex()){
            result = userAgentRoleDao.updateUserAgentRoleByUserId(userId, salesDetail.getAgentId(), salesDetail.getAgentName(), UserRoleEnum.EMPLOYEE.getIndex(),
                    UserRoleEnum.EMPLOYEE.getName(), salesDetail.getId(), salesDetail.getUserName());
        }

        return result > 0;
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
     * <p>date 2017/10/17 10:00 </p>
     * <p>return boolean</p>
     */
    @Override
    public boolean updateReferrerAgentRoleByUserId(int userId, int referrerId) throws ServiceException, Exception {
        if (userId <= 0) {
            throw new IllegalArgumentException("userId is illegal");
        }
        if (referrerId <= 0) {
            throw new IllegalArgumentException("referrerId is illegal");
        }

        // 验证用户
        UserDetailEntity userEntity = userDao.getUserDetailByUserId(userId);
        if (userEntity == null) {
            throw new ServiceException(Result.USER_NOT_EXIST.getCode(), Result.USER_NOT_EXIST.getMessage());
        }
        // 当前用户是普通用户角色，不是抛异常
        if (userEntity.getRoleId() != UserRoleEnum.USER.getIndex()) {
            throw new ServiceException(Result.USER_ROLE_NOT_CORRECT.getCode(), Result.USER_ROLE_NOT_CORRECT.getMessage());
        }

        UserDetailEntity referrerDetail = userDao.getUserDetailByUserId(referrerId);
        if (referrerDetail == null) {
            throw new ServiceException(Result.USER_AGENT_ROLE_NOT_EXIST.getCode(), Result.USER_AGENT_ROLE_NOT_EXIST.getMessage());
        }
        // 推荐人是普通用户角色，不是抛异常
        if (referrerDetail.getRoleId() != UserRoleEnum.USER.getIndex()) {
            throw new ServiceException(Result.USER_ROLE_NOT_CORRECT.getCode(), Result.USER_ROLE_NOT_CORRECT.getMessage());
        }


        int salesId = 0;
        String salesName = "";
        int agentId = 0;
        String agentName = "";
        // 推荐人要如果和业务人员有绑定相应的关系
//        if (referrerDetail.getAgentId() == 0 || StringUtils.isEmpty(referrerDetail.getAgentName())
//                || referrerDetail.getSalesId() == 0 || StringUtils.isEmpty(referrerDetail.getSalesName())) {
//
//            throw new ServiceException(Result.SALES_AGENT_ROLE_NOT_EXIST.getCode(), Result.SALES_AGENT_ROLE_NOT_EXIST.getMessage());
//        }
        // 推荐人要如果和业务人员有绑定相应的关系，则赋值
        if (referrerDetail.getAgentId() != 0 && StringUtils.isNotEmpty(referrerDetail.getAgentName())
                && referrerDetail.getSalesId() != 0 && StringUtils.isNotEmpty(referrerDetail.getSalesName())) {

            salesId = referrerDetail.getSalesId();
            salesName = referrerDetail.getSalesName();
            agentId = referrerDetail.getAgentId();
            agentName = referrerDetail.getAgentName();
        }

        int result = userAgentRoleDao.updateReferrerAgentRoleByUserId(userId, agentId, agentName, UserRoleEnum.USER.getIndex(),
                UserRoleEnum.USER.getName(), salesId, salesName, referrerDetail.getId(), referrerDetail.getUserName());
        return result > 0;
    }

    /**
     * <p>Description: 根据用户通过手机号进行登陆</p>
     * <p>mobile 用户登陆的手机号</p>
     * <p>password 用户登陆的密码</p>
     * <p>date 2017/10/27 10:00 </p>
     * <p>return UserDetailResponse</p>
     */
    public UserDetailResponse userLoginByMobile(String mobile, String password) throws ServiceException, Exception {
        if (StringUtils.isEmpty(mobile)) {
            throw new IllegalArgumentException("mobile is null or empty");
        }
        if (!PatternHelper.isMobile(mobile)) {
            throw new IllegalArgumentException("mobile format error");
        }
        if (StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("password is null or empty");
        }
        UserDetailEntity userDetail = userDao.getUserDetailByMobile(mobile);
        UserDetailResponse result = null;
        if (userDetail == null) {
            throw new ServiceException(Result.USER_NOT_EXIST.getCode(), Result.USER_NOT_EXIST.getMessage());
        }
        // 对password进行处理，后进行比较
        String userPassword = encryptUtil.validateLoginPass((long) userDetail.getId(), password);
        // 密码不等
        if (!userPassword.equals(userDetail.getPassword())) {
            throw new ServiceException(Result.USER_PASS_ERROR.getCode(), Result.USER_PASS_ERROR.getMessage());
        }

        result = this.getUserDetailResponse(userDetail);
        return result;
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
     */
    @Transactional(readOnly = true)
    public UserAllListResponse getUserAll(int pageNum, int pageSize, String startTime, String endTime, Integer logStatus, Integer companyId, Integer agentId, String searchInput) throws Exception {
        UserAllListResponse res = new UserAllListResponse();
        PageHelper.startPage(pageNum, pageSize);
        List<UserAllEntity> list = userDao.getAllUser(startTime, endTime, logStatus, companyId, agentId, searchInput);
        PageInfo pageInfo = new PageInfo(list);
        res.setList(list);
        res.setTotal(pageInfo.getTotal());
        res.setPages(pageInfo.getPages());
        return res;
    }
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
    public UserAllListResponse getUserAllExport( String startTime, String endTime, Integer logStatus, Integer companyId, Integer agentId, String searchInput) throws Exception{
        List<UserAllEntity> list = userDao.getAllUser(startTime, endTime, logStatus, companyId, agentId, searchInput);
        UserAllListResponse res = new UserAllListResponse();
        res.setList(list);
        return res;
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
    @Transactional(readOnly = true)
    public UserListResponse getUserByAgentIdOrRoleId(Integer agentId, Integer roleId) {
        UserListResponse userListResponse = new UserListResponse();
        List<UserEntity> list = userDao.getUserByAgentIdOrRoleId(agentId, roleId);
        userListResponse.setList(list);
        return userListResponse;
    }

    /**
     * <p>Description:更新用户状态</p>
     * <p>param status:状态</p>
     * <p>param id:编号</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/3 14:53</p>
     * <p>return:</p>
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserStatus(Integer status, Integer id) throws Exception {
        boolean flag = true;
        if (status == null) {
            flag = false;
            throw new Exception("statu is null");
        }

        if (id == null || id == 0) {
            flag = false;
            throw new Exception("id is null");
        }

        try {
            userDao.updateUserStatus(status, id);
        } catch (Exception ex) {
            flag = false;
            throw new Exception("updateUserStatus is fail!");

        }
        return flag;
    }

    /**
     * <p>Description:通过用户编号查用户</p>
     * <p>param id:用户编号</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/4 18:18</p>
     * <p>return:</p>
     * <p>throws: </p>
     */
    public UserAllListResponse getAllUserOne(Integer id) {
        UserAllListResponse res = new UserAllListResponse();
        UserAllEntity entity = userDao.getAllUserOne(id);
        List<UserAllEntity> list = new ArrayList<>();
        list.add(entity);
        res.setList(list);
        return res;
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
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserDescripe(int userid,String descripe) throws  Exception{
        boolean flag=false;
        try{
            userDao.updateUserDescripe(userid,descripe);
            flag=true;
        }catch(Exception ex)
        {
           throw new Exception("updateUserDescripe更新备注信息异常:"+ex.getMessage());
        }
        return flag;

    }

    /**
     *<p>Description:通过id查询我的客户</p>
     *<p>param type:1 店员  2 门店</p>
     *<p>param userId:店员或门店id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/22 15:13</p>
     *<p>return:</p>
     */
    @Transactional(readOnly = true)
    public MyCustomerResponse selectUserCountByUserId(int type, int userId) throws ServiceException,Exception{
        Response<UserCountResponse> invitecountRes= barCodeFeign.getInviteCount(type,userId);
        if(invitecountRes.getCode()>0){
            throw new ServiceException(invitecountRes.getCode(),invitecountRes.getMessage());
        }
        UserCountResponse userResponse=invitecountRes.getValue();
        if(userResponse==null){
            throw new ServiceException(6001,"in selectUserCountByUserId,get order UserCountResponse is null");
        }
        UserCountEntity orderCountEntity=userResponse.getEntity();
        MyCustomerEntity entity=new MyCustomerEntity();
        if(orderCountEntity!=null){
            entity.setAllInviteNum(orderCountEntity.getAllCount());
            entity.setDayInviteNum(orderCountEntity.getNowCount());
        }
        UserCountEntity userCountEntity= userAgentRoleDao.selectUserCountByUserId(type,userId);
        if(userCountEntity!=null){
            entity.setDayCustomerNum(userCountEntity.getNowCount());
            entity.setAllCustomerNum(userCountEntity.getAllCount());
            entity.setDayCouponNum(userCountEntity.getNowCouponNum());
            entity.setAllCouponNum(userCountEntity.getAllCouponNum());
        }
        MyCustomerResponse response=new MyCustomerResponse();
        response.setEntity(entity);
        return response;
    }
    /**
     *<p>Description:通过用户id修改收款人id</p>
     *<p>param userId:用户id</p>
     *<p>param receiverId:收款人id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/28 9:35</p>
     */
    @Transactional(rollbackFor = {Exception.class,ServiceException.class})
    public int updateReceiverIdByUserId(int receiverid,int userid) throws ServiceException,Exception{
        return userAgentRoleDao.updateReceiverIdByUserId(userid,receiverid);
    }

    /**
     *<p>Description:查询未确认的绑定信息</p>
     *<p>param userId:登录人id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/28 13:40</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public ManagerBindMessageResponse getBindMessage(int userId)throws ServiceException,Exception{
        ManagerBindMessageResponse response=null;
        String updateReceiverAccount=userId+"_updateReceiverAccount";
        if(redisHelper.exists(updateReceiverAccount)){
            String jsonstr=redisHelper.get(updateReceiverAccount);
            JSONObject json=JSON.parseObject(jsonstr);
            if(json!=null){
                response=new ManagerBindMessageResponse();
                Integer uid=json.getInteger("userId");
                String nickName=json.get("nickName")==null?"":json.getString("nickName");
                String iconUrl=json.get("iconUrl")==null?"":json.get("iconUrl").toString();
                response.setUserId(uid);
                response.setNickName(nickName);
                response.setIconUrl(iconUrl);
            }
        }
        return response;
    }
    /**
     *<p>Description:邀请店员</p>
     *<p>param inviteId:邀请人id</p>
     *<p>param saleId:被邀请人id</p>
     *<p>param codekey:二维码键值</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/5 10:04</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    @Transactional(rollbackFor = {Exception.class,ServiceException.class})
    public  Response<String>  updateInviteSaleJoin(int inviteId,int saleId,String codekey) throws ServiceException,Exception{
        UserDetailResponse saledetail=this.getUserDetailByUserId(inviteId);
        if(saledetail==null){
            throw new ServiceException(70001,"updateInviteSaleJoin can not get invite's msg is null");
        }
        int roleId=saledetail.getRoleId();
        if(roleId!=UserRoleEnum.EMPLOYEE.getIndex()){
            throw new ServiceException(70002,"updateInviteSaleJoin invite's roleId is error");
        }
        int agentId=saledetail.getAgentId();
        UserDetailResponse userdetail=this.getUserDetailByUserId(saleId);
        if(userdetail==null){
            throw new ServiceException(70002,"updateInviteSaleJoin  sales's msg is null");
        }
        int count=saleJoinDao.getCountByCodekey(codekey);
        if(count==0){
            updateSalesAgentRoleByUserId(saleId,agentId,UserRoleEnum.EMPLOYEE.getIndex());
            InviteSaleJoinEntity inviteJoin=new InviteSaleJoinEntity();
            inviteJoin.setCodekey(codekey);
            inviteJoin.setInviteId(inviteId);
            inviteJoin.setSaleId(saleId);
            saleJoinDao.insertInviteSaleJoin(inviteJoin);
        }
        Response<String> response=new Response<>(Result.SUCCESS,Result.SUCCESS.getMessage());
        return response;
    }
    public Response<UserNewDetailResponse> getUserBySearch(int pageNum,int pageSize,int saleId)throws ServiceException,Exception{
        if(pageNum<=0){
            throw new ServiceException(71001,"pageNum value is error");
        }
        if(pageSize<=0){
            throw new ServiceException(71002,"pageSize value is error");
        }
        if(saleId<=0){
            throw new ServiceException(71003,"saleId value is error");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<UserNewDetailEntity> list=userDao.getUserBySearch(1,saleId);
        PageInfo p=new PageInfo(list);
        UserNewDetailResponse response=new UserNewDetailResponse();
        response.setList(list);
        response.setTotal(p.getTotal());
        response.setPages(p.getPages());
        Response<UserNewDetailResponse> res=new Response<UserNewDetailResponse>(Result.SUCCESS,response);
        return res;
    }
    public UserDetailListResponse getUserDetailByCompanyIds(int type,String ids) throws ServiceException,Exception{
        if(type!=1&& type!=2){
            throw new ServiceException(72001,"type value is error");
        }
        if(StringUtils.isEmpty(ids)){
            throw new ServiceException(72002,"ids value is error");
        }
        String [] ids_str=ids.split(",");
        if(ids_str.length==0){
            throw new ServiceException(72002,"ids value is error");
        }
        List<Integer> ids_arr=new ArrayList<>();
        for(int i=0;i<ids_str.length;i++){
            try{
                int id=Integer.parseInt(ids_str[i]);
                ids_arr.add(id);
            }catch (Exception ex){
                throw new ServiceException(72002,"ids value is error");
            }

        }
        UserDetailListResponse response=new UserDetailListResponse();
        List<UserDetailEntity> list=userDao.getUserDetailByCompanyIds(type,ids_arr);
        response.setList(list);
        return response;
    }
}
