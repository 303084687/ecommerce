package com.weichuang.ecommerce.coupon.service;

import java.math.BigDecimal;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.coupon.entity.request.CouponAddRequest;
import com.weichuang.ecommerce.coupon.entity.response.CouponListResponse;
import com.weichuang.ecommerce.coupon.entity.response.CouponNumResponse;
import com.weichuang.ecommerce.coupon.entity.response.CouponResponse;
import com.weichuang.ecommerce.coupon.entity.response.CouponUsedListResponse;

/**
 * <p>ClassName: ICouponService.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description: 优惠券service接口</p>
 * <p>author wanggongliang</p>
 * <p>2017年10月24日 下午3:27:54</p>
 */
public interface ICouponService {

    // 指定批量发送优惠券
    public void batchAppointCoupon(String typeCode, int type, String creator, String companyIds)
            throws ServiceException, Exception;

    // 指定给某个用户发送优惠券
    public void singleBindCoupon(String typeCode, int userId, String openId, String creator) throws ServiceException,
            Exception;

    // 批量生成优惠券
    public int addCoupon(CouponAddRequest request) throws ServiceException, Exception;

    // 批量or单个绑定优惠券
    public void bindCoupon(String openId, int referId, String referOpenId, String typeCode, String exceptTypeCode,
            int type) throws ServiceException, Exception;

    // 根据状态,是否使用,使用者,券号等查询优惠券列表
    public CouponListResponse getCouponList(String typeCode, String couponCode, String userName, int isUsed,
            int status, int pageNum, int pageSize) throws ServiceException, Exception;

    // 根据券号查询优惠券详情
    public CouponResponse getCouponByCode(String couponCode) throws ServiceException, Exception;

    // 根据用户主键和时间查询可用优惠券列表(删选条件：未过期 已到使用日期 状态有效的 未使用的)
    public CouponUsedListResponse couponNotUsedList(String openId, int pageNum, int pageSize) throws ServiceException,
            Exception;

    // 根据用户主键和时间查询不可用优惠券列表总数量（删选条件：已过期 未到使用日期 状态无效的 已使用的）
    public CouponUsedListResponse couponUsedList(String openId, int pageNum, int pageSize) throws ServiceException,
            Exception;

    // 根据券号将优惠券标为已经使用提交订单
    public int referCouponOrder(String userName, String orderId, String couponCode, int usedPlat)
            throws ServiceException;

    // 根据券号将优惠券标为已经使用取消订单
    public int cancelCouponOrder(String couponCode) throws ServiceException;

    // 批量修改优惠券状态为禁止使用
    public int updateCouponStatus(String ids) throws ServiceException;

    // 根据用户,未使用,未过期,渠道,最低订单金额,筛选符合订单的优惠券列表，用于订单页面的可使用列表
    public CouponUsedListResponse choseCoupon(String openId, int platForm, BigDecimal orderMoney, String productIds)
            throws ServiceException;

    // 根据用户openId和时间查询优惠券可用和不可用的数量
    public CouponNumResponse couponNum(String openId) throws ServiceException;

    // 根据销售给特定的健身房老用户发放体验券

    public void oldUserCoupon(int userId, String userOpenId, int saleId, String typeCode, int companyId)
            throws ServiceException, Exception;

    // 根据券号删除优惠券
    public int deleteCoupon(String couponCode) throws ServiceException;
}
