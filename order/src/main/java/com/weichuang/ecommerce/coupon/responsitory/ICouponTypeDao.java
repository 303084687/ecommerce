package com.weichuang.ecommerce.coupon.responsitory;

import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.coupon.entity.CouponType;

/**
 * <p>ClassName: ICouponTypeDao.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:优惠券类型接口 </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月20日 下午5:02:25</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public interface ICouponTypeDao {
    // 增加
    public int addCouponType(CouponType couponType);

    // 修改
    public int updateCouponType(CouponType couponType);

    // 根据主键删除(物理删除)
    public int deleteCouponType(int id, int status);

    // 根据券号类型状态以及创建时间查询列表
    public List<CouponType> getCouponTypeList(String typeCode, int category, int status, String startTime,
            String endTime);

    // 根据类型号查询详细信息(单个)
    public CouponType getCouponTypeById(String typeCode, int id);

    // 根据多个券类型号查询信息返回集合
    public List<CouponType> getCouponTypeListByIds(String typeCodes);

    // 类型集合(用于下拉框)只查询正常状态下的,用于生成优惠券
    public List<CouponType> getTypeList();

    // 当receiveCounts有领取总数量限制的时候,每领取一张批量更新剩余领取数量
    public int updateReceiveCount(String typeCode, int number);

    // 根据限制使用渠道或者类型查询优惠券列表
    public List<CouponType> getLimitPlatId(int platLimitId, int category);
}
