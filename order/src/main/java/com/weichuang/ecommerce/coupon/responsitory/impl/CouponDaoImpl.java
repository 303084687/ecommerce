package com.weichuang.ecommerce.coupon.responsitory.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.coupon.constants.NameSpaceConstant;
import com.weichuang.ecommerce.coupon.entity.Coupon;
import com.weichuang.ecommerce.coupon.entity.CouponExpire;
import com.weichuang.ecommerce.coupon.entity.response.CouponListEntity;
import com.weichuang.ecommerce.coupon.entity.response.CouponUsedEntity;
import com.weichuang.ecommerce.coupon.responsitory.ICouponDao;

/**
 * <p>ClassName: CouponDaoImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:优惠券实体接口实现类 </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月22日 下午1:41:53</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class CouponDaoImpl implements ICouponDao {
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate;

    // 批量生成优惠券
    @Override
    public int addCoupon(List<Coupon> list) {
        return sqlSessionTemplate.insert(NameSpaceConstant.COUPON + ".addCoupon", list);
    }

    // 根据状态,是否使用,使用者,券号等查询优惠券列表
    @Override
    public List<CouponListEntity> getCouponList(String typeCode, String couponCode, String userName, int isUsed,
            int status) {
        Map<String, Object> map = new HashMap<>();
        map.put("typeCode", typeCode);
        map.put("couponCode", couponCode);
        map.put("userName", userName);
        map.put("isUsed", isUsed);
        map.put("status", status);
        return sqlSessionTemplate.selectList(NameSpaceConstant.COUPON + ".getCouponList", map);
    }

    // 根据券号查询优惠券详情
    @Override
    public Coupon getCouponByCode(String couponCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("couponCode", couponCode);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.COUPON + ".getCouponByCode", map);
    }

    // 根据用户主键和时间查询可用优惠券列表(删选条件：未过期 已到使用日期 状态有效的 未使用的)
    @Override
    public List<CouponUsedEntity> couponNotUsedList(String openId, String time) {
        Map<String, Object> map = new HashMap<>();
        map.put("openId", openId);
        map.put("time", time);
        return sqlSessionTemplate.selectList(NameSpaceConstant.COUPON + ".couponNotUsedList", map);
    }

    // 根据用户主键和时间查询不可用优惠券列表总数量（删选条件：已过期 未到使用日期 状态无效的 已使用的）
    @Override
    public List<CouponUsedEntity> couponUsedList(String openId, String time) {
        Map<String, Object> map = new HashMap<>();
        map.put("time", time);
        map.put("openId", openId);
        return sqlSessionTemplate.selectList(NameSpaceConstant.COUPON + ".couponUsedList", map);
    }

    // 根据券号将优惠券标为已经使用提交订单
    @Override
    public int referCouponOrder(String userName, String orderNo, String couponCode, int usedPlat) {
        Map<String, Object> map = new HashMap<>();
        map.put("userName", userName);
        map.put("orderId", orderNo);
        map.put("usedPlat", usedPlat);
        map.put("couponCode", couponCode);
        return sqlSessionTemplate.update(NameSpaceConstant.COUPON + ".referCouponOrder", map);
    }

    // 根据券号将优惠券标为已经使用取消订单
    @Override
    public int cancelCouponOrder(String couponCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("couponCode", couponCode);
        return sqlSessionTemplate.update(NameSpaceConstant.COUPON + ".cancelCouponOrder", map);
    }

    // 批量修改优惠券状态为禁止使用
    @Override
    public int updateCouponStatus(String ids) {
        Map<String, Object> map = new HashMap<>();
        // 处理传过来的优惠券id字符串
        List<String> couponIds = new ArrayList<>();
        for (String pid : ids.split(",")) {
            couponIds.add(pid);
        }
        map.put("ids", couponIds);
        return sqlSessionTemplate.update(NameSpaceConstant.COUPON + ".updateCouponStatus", map);
    }

    // 当有个人领取总量限制的时候查询领取的总数量(根据类型code和用户主键)
    @Override
    public int getPersonCouponCount(String typeCode, int userId, String openId) {
        Map<String, Object> map = new HashMap<>();
        map.put("typeCode", typeCode);
        map.put("userId", userId);
        map.put("openId", openId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.COUPON + ".getPersonCouponCount", map);
    }

    // 批量or单个绑定优惠券
    @Override
    public int bindCoupon(List<Coupon> list) {
        return sqlSessionTemplate.insert(NameSpaceConstant.COUPON + ".bindCoupon", list);
    }

    // 根据用户,未使用,未过期,渠道,最低订单金额,筛选符合订单的优惠券列表，用于订单页面的可使用列表
    @Override
    public List<CouponUsedEntity> choseCoupon(String openId, int platForm, BigDecimal orderMoney, String time) {
        Map<String, Object> map = new HashMap<>();
        map.put("openId", openId);
        map.put("platForm", platForm);
        map.put("orderMoney", orderMoney);
        map.put("time", time);
        return sqlSessionTemplate.selectList(NameSpaceConstant.COUPON + ".choseCoupon", map);
    }

    // 根据用户主键和时间查询可用优惠券数量(删选条件：未过期 已到使用日期 状态有效的 未使用的)
    @Override
    public int couponNotUsedCount(String openId, String time) {
        Map<String, Object> map = new HashMap<>();
        map.put("openId", openId);
        map.put("time", time);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.COUPON + ".couponNotUsedCount", map);
    }

    // 根据用户主键和时间查询不可用优惠券总数量（删选条件：已过期 未到使用日期 状态无效的 已使用的）
    @Override
    public int couponUsedCount(String openId, String time) {
        Map<String, Object> map = new HashMap<>();
        map.put("time", time);
        map.put("openId", openId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.COUPON + ".couponUsedCount", map);
    }

    // 根据优惠券过期时间提醒
    @Override
    public List<CouponExpire> getExpireCouponList(String nowTime, String compareTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("nowTime", nowTime);
        map.put("compareTime", compareTime);
        return sqlSessionTemplate.selectList(NameSpaceConstant.COUPON + ".getExpireCouponList", map);
    }

    // 根据券号删除优惠券
    @Override
    public int deleteCoupon(String couponCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("couponCode", couponCode);
        return sqlSessionTemplate.delete(NameSpaceConstant.COUPON + ".deleteCoupon", map);
    }
}
