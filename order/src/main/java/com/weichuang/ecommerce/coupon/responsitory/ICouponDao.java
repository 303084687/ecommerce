package com.weichuang.ecommerce.coupon.responsitory;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.coupon.entity.Coupon;
import com.weichuang.ecommerce.coupon.entity.CouponExpire;
import com.weichuang.ecommerce.coupon.entity.response.CouponListEntity;
import com.weichuang.ecommerce.coupon.entity.response.CouponUsedEntity;

/**
 * <p>ClassName: ICouponDao.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:优惠券接口 </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月20日 下午5:22:51</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public interface ICouponDao {
    // 批量生成优惠券
    public int addCoupon(List<Coupon> list);

    // 根据状态,是否使用,使用者,券号等查询优惠券列表
    public List<CouponListEntity> getCouponList(String typeCode, String couponCode, String userName, int isUsed,
            int status);

    // 根据券号查询优惠券详情
    public Coupon getCouponByCode(String couponCode);

    // 根据用户主键和时间查询可用优惠券列表(删选条件：未过期 已到使用日期 状态有效的 未使用的)
    public List<CouponUsedEntity> couponNotUsedList(String openId, String time);

    // 查询可用的数量
    public int couponNotUsedCount(String openId, String time);

    // 根据用户主键和时间查询不可用优惠券列表总数量（删选条件：已过期 未到使用日期 状态无效的 已使用的）
    public List<CouponUsedEntity> couponUsedList(String openId, String time);

    // 查询不可用的数量
    public int couponUsedCount(String openId, String time);

    // 根据券号将优惠券标为已经使用提交订单
    public int referCouponOrder(String userName, String orderNo, String couponCode, int usedPlat);

    // 根据券号将优惠券标为已经使用取消订单
    public int cancelCouponOrder(String couponCode);

    // 批量修改优惠券状态为禁止使用
    public int updateCouponStatus(String ids);

    // 当有个人领取总量限制的时候查询领取的总数量(根据类型code和用户主键或者openId)
    public int getPersonCouponCount(String typeCode, int userId, String openId);

    // 批量or单个绑定优惠券
    public int bindCoupon(List<Coupon> list);

    // 根据用户,未使用,未过期,渠道,最低订单金额,筛选符合订单的优惠券列表，用于订单页面的可使用列表
    public List<CouponUsedEntity> choseCoupon(String openId, int platForm, BigDecimal orderMoney, String time);

    // 根据优惠券过期时间提醒
    public List<CouponExpire> getExpireCouponList(String nowTime, String compareTime);

    // 根据券号删除优惠券
    public int deleteCoupon(String couponCode);

}
