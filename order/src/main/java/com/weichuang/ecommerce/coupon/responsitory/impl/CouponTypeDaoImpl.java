package com.weichuang.ecommerce.coupon.responsitory.impl;

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
import com.weichuang.ecommerce.coupon.entity.CouponType;
import com.weichuang.ecommerce.coupon.responsitory.ICouponTypeDao;

/**
 * <p>ClassName: CouponTypeDaoImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:优惠券类型接口实现 </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月22日 下午1:42:43</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class CouponTypeDaoImpl implements ICouponTypeDao {
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate;

    // 增加
    @Override
    public int addCouponType(CouponType couponType) {
        return sqlSessionTemplate.insert(NameSpaceConstant.COUPON_TYPE + ".addCouponType", couponType);
    }

    // 修改
    @Override
    public int updateCouponType(CouponType couponType) {
        return sqlSessionTemplate.update(NameSpaceConstant.COUPON_TYPE + ".updateCouponType", couponType);
    }

    // 根据主键删除(物理删除)
    @Override
    public int deleteCouponType(int id, int status) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", status);
        return sqlSessionTemplate.update(NameSpaceConstant.COUPON_TYPE + ".deleteCouponType", map);
    }

    // 根据券号类型状态以及创建时间查询列表
    @Override
    public List<CouponType> getCouponTypeList(String typeCode, int category, int status, String startTime,
            String endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("typeCode", typeCode);
        map.put("category", category);
        map.put("status", status);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return sqlSessionTemplate.selectList(NameSpaceConstant.COUPON_TYPE + ".getCouponTypeList", map);
    }

    // 根据类型号查询详细信息(单个)
    @Override
    public CouponType getCouponTypeById(String typeCode, int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("typeCode", typeCode);
        map.put("id", id);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.COUPON_TYPE + ".getCouponTypeById", map);
    }

    // 根据多个券类型号查询信息返回集合
    @Override
    public List<CouponType> getCouponTypeListByIds(String typeCode) {
        Map<String, Object> map = new HashMap<>();
        // 处理传过来的优惠券id字符串
        List<String> typeCodeList = new ArrayList<>();
        for (String pid : typeCode.split(",")) {
            typeCodeList.add(pid);
        }
        map.put("typeCode", typeCodeList);
        return sqlSessionTemplate.selectList(NameSpaceConstant.COUPON_TYPE + ".getCouponTypeListByIds", map);
    }

    // 类型集合(用于下拉框)只查询正常状态下的,用于生成优惠券
    @Override
    public List<CouponType> getTypeList() {
        return sqlSessionTemplate.selectList(NameSpaceConstant.COUPON_TYPE + ".getTypeList");
    }

    // 当receiveCounts有领取总数量限制的时候,每领取一张批量更新剩余领取数量
    @Override
    public int updateReceiveCount(String typeCode, int number) {
        Map<String, Object> map = new HashMap<>();
        // 处理传过来的优惠券id字符串
        List<String> typeCodeList = new ArrayList<>();
        for (String pid : typeCode.split(",")) {
            typeCodeList.add(pid);
        }
        map.put("typeCodeList", typeCodeList);
        map.put("number", number);
        return sqlSessionTemplate.update(NameSpaceConstant.COUPON_TYPE + ".updateReceiveCount", map);
    }

    // 根据限制使用渠道或者类型查询优惠券列表
    @Override
    public List<CouponType> getLimitPlatId(int platLimitId, int category) {
        Map<String, Object> map = new HashMap<>();
        map.put("platLimitId", platLimitId);
        map.put("category", category);
        return sqlSessionTemplate.selectList(NameSpaceConstant.COUPON_TYPE + ".getLimitPlatId", map);
    }
}
