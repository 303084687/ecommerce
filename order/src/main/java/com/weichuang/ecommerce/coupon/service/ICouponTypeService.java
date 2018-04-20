package com.weichuang.ecommerce.coupon.service;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.coupon.entity.request.CouponTypeAddRequest;
import com.weichuang.ecommerce.coupon.entity.response.CouponTypeListResponse;
import com.weichuang.ecommerce.coupon.entity.response.CouponTypeResponse;

/**
 * <p>ClassName: ICouponTypeService.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:优惠券类型service </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月24日 下午3:36:31</p>
 */
public interface ICouponTypeService {
    // 增加
    public int addCouponType(CouponTypeAddRequest typeAddRequest) throws ServiceException, Exception;

    // 修改
    public int updateCouponType(CouponTypeAddRequest typeAddRequest) throws ServiceException, Exception;

    // 根据主键删除(物理删除)
    public int deleteCouponType(int id, int status) throws ServiceException;

    // 根据券号类型状态以及创建时间查询列表
    public CouponTypeListResponse getCouponTypeList(String typeCode, int category, int status, String startTime,
            String endTime, int pageNum, int pageSize) throws ServiceException;

    // 根据类型号查询详细信息(单个)
    public CouponTypeResponse getCouponTypeById(String typeCode, int id) throws ServiceException;

    // 类型集合(用于下拉框)只查询正常状态下的,用于生成优惠券
    public CouponTypeListResponse getTypeList() throws ServiceException;

    // 根据限制使用渠道或者类型查询优惠券列表
    public CouponTypeListResponse getLimitPlatId(int platLimitId, int category) throws ServiceException;

}
