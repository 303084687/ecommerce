package com.weichuang.ecommerce.coupon.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.coupon.entity.CouponType;
import com.weichuang.ecommerce.coupon.entity.request.CouponTypeAddRequest;
import com.weichuang.ecommerce.coupon.entity.response.CouponTypeListResponse;
import com.weichuang.ecommerce.coupon.entity.response.CouponTypeResponse;
import com.weichuang.ecommerce.coupon.responsitory.ICouponTypeDao;
import com.weichuang.ecommerce.coupon.service.ICouponTypeService;
import com.weichuang.ecommerce.coupon.util.CouponHelper;

/**
 * <p>ClassName: CouponTypeServiceImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:优惠券类型service实现 </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月25日 上午10:42:36</p>
 */
@Service
@SuppressWarnings("all")
public class CouponTypeServiceImpl implements ICouponTypeService {
    @Autowired
    private ICouponTypeDao couponTypeDao;

    // 增加
    @Override
    @Transactional
    public int addCouponType(CouponTypeAddRequest typeAddRequest) throws ServiceException, Exception {
        // 优惠券code
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        CouponType couponType = new CouponType();
        couponType.setCategory(typeAddRequest.getCategory());
        couponType.setDay(typeAddRequest.getDay());
        couponType.setDescription(typeAddRequest.getDescription());
        couponType.setDiscountMoney(typeAddRequest.getDiscountMoney());
        // 处理时间
        couponType.setTimeType(typeAddRequest.getTimeType());
        if (typeAddRequest.getTimeType() == 1) {
            couponType.setStartTime(format.parse(typeAddRequest.getStartTime()));
            couponType.setExpireTime(format.parse(typeAddRequest.getExpireTime()));
        } else {
            couponType.setDay(typeAddRequest.getDay());
        }
        // 处理平台
        couponType.setPlatLimit(typeAddRequest.getPlatLimit());
        if (typeAddRequest.getPlatLimit().equals("2")) {
            couponType.setPlatLimitId(typeAddRequest.getPlatLimitId());
        }
        // 处理商品
        couponType.setProductLimit(typeAddRequest.getProductLimit());
        if (typeAddRequest.getProductLimit() == 2) {
            couponType.setProductId(typeAddRequest.getProductId());
        }
        couponType.setTypeCode(CouponHelper.createUUID());
        couponType.setOrderMin(typeAddRequest.getOrderMin());
        couponType.setPersonalCounts(typeAddRequest.getPersonalCounts());
        couponType.setReceiveCounts(typeAddRequest.getReceiveCounts());
        couponType.setReceiveSurplus(typeAddRequest.getReceiveCounts());
        couponType.setTitle(typeAddRequest.getTitle());
        couponType.setCreator(typeAddRequest.getCreator());
        return couponTypeDao.addCouponType(couponType);
    }

    // 修改
    @Override
    @Transactional
    public int updateCouponType(CouponTypeAddRequest typeAddRequest) throws ServiceException, Exception {
        // 优惠券code
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        CouponType couponType = new CouponType();
        couponType.setId(typeAddRequest.getId());
        couponType.setCategory(typeAddRequest.getCategory());
        couponType.setDescription(typeAddRequest.getDescription());
        couponType.setDiscountMoney(typeAddRequest.getDiscountMoney());
        // 处理时间
        couponType.setTimeType(typeAddRequest.getTimeType());
        if (typeAddRequest.getTimeType() == 1) {
            couponType.setStartTime(format.parse(typeAddRequest.getStartTime()));
            couponType.setExpireTime(format.parse(typeAddRequest.getExpireTime()));
            couponType.setDay(null);
        } else {
            couponType.setDay(typeAddRequest.getDay());
            couponType.setStartTime(null);
            couponType.setExpireTime(null);
        }
        // 处理平台
        couponType.setPlatLimit(typeAddRequest.getPlatLimit());
        if (typeAddRequest.getPlatLimit().equals("2")) {
            couponType.setPlatLimitId(typeAddRequest.getPlatLimitId());
        } else {
            couponType.setPlatLimitId(null);
        }
        // 处理商品
        couponType.setProductLimit(typeAddRequest.getProductLimit());
        if (typeAddRequest.getProductLimit() == 2) {
            couponType.setProductId(typeAddRequest.getProductId());
        } else {
            couponType.setProductId(null);
        }
        couponType.setOrderMin(typeAddRequest.getOrderMin());
        couponType.setPersonalCounts(typeAddRequest.getPersonalCounts());
        couponType.setReceiveCounts(typeAddRequest.getReceiveCounts());
        couponType.setReceiveSurplus(typeAddRequest.getReceiveCounts());
        couponType.setTitle(typeAddRequest.getTitle());
        return couponTypeDao.updateCouponType(couponType);
    }

    // 根据主键删除(物理删除)
    @Override
    @Transactional
    public int deleteCouponType(int id, int status) throws ServiceException {
        return couponTypeDao.deleteCouponType(id, status);
    }

    // 根据券号类型状态以及创建时间查询列表后台管理接口
    @Override
    @Transactional(readOnly = true)
    public CouponTypeListResponse getCouponTypeList(String typeCode, int category, int status, String startTime,
            String endTime, int pageNum, int pageSize) throws ServiceException {
        // 执行查询数据和分页处理
        PageHelper.startPage(pageNum, pageSize);
        List<CouponType> list = couponTypeDao.getCouponTypeList(typeCode, category, status, startTime, endTime);
        CouponTypeListResponse response = new CouponTypeListResponse();
        PageInfo pageInfo = new PageInfo(list);
        // 返回的数据
        response.setList(list);
        // 总的页数
        response.setPages(pageInfo.getPages());
        // 总的记录数
        response.setTotal(pageInfo.getTotal());
        return response;
    }

    // 根据类型号查询详细信息
    @Override
    @Transactional(readOnly = true)
    public CouponTypeResponse getCouponTypeById(String typeCode, int id) throws ServiceException {
        CouponType couponType = couponTypeDao.getCouponTypeById(typeCode, id);
        CouponTypeResponse response = new CouponTypeResponse();
        response.setCouponType(couponType);
        return response;
    }

    // 类型集合(用于下拉框)只查询正常状态下的,用于生成优惠券
    @Override
    @Transactional(readOnly = true)
    public CouponTypeListResponse getTypeList() throws ServiceException {
        CouponTypeListResponse response = new CouponTypeListResponse();
        List<CouponType> list = couponTypeDao.getTypeList();
        response.setList(list);
        return response;
    }

    // 根据限制使用渠道或者类型查询优惠券列表
    @Override
    @Transactional(readOnly = true)
    public CouponTypeListResponse getLimitPlatId(int platLimitId, int category) throws ServiceException {
        CouponTypeListResponse response = new CouponTypeListResponse();
        List<CouponType> list = couponTypeDao.getLimitPlatId(platLimitId, category);
        response.setList(list);
        return response;
    }
}
