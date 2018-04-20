package com.weichuang.ecommerce.coupon.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weichuang.ecommerce.coupon.entity.CouponType;
import com.weichuang.ecommerce.coupon.entity.ReferCompany;
import com.weichuang.ecommerce.coupon.entity.ReferRecomme;
import com.weichuang.ecommerce.coupon.entity.response.CompanyNumResponse;
import com.weichuang.ecommerce.coupon.responsitory.ICouponTypeDao;
import com.weichuang.ecommerce.coupon.responsitory.IReferRecommeDao;
import com.weichuang.ecommerce.coupon.service.IReferRecommeService;

/**
 * <p>ClassName: ReferRecommeServiceImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:推荐返回实现 </p>
 * <p>author wanggongliang</p>
 * <p>2017年12月22日 下午2:21:55</p>
 */
@Service
@SuppressWarnings("all")
public class ReferRecommeServiceImpl implements IReferRecommeService {
    @Autowired
    private IReferRecommeDao referRecommeDao;

    // 优惠券类型
    @Autowired
    private ICouponTypeDao couponTypeDao;

    // 增加
    @Override
    public void addReferRecomme(ReferRecomme recomme) {
        // 首先根据公司的主键查询是否有体验券,有了才赠送
        List<CouponType> list = couponTypeDao.getLimitPlatId(recomme.getReferCompanyId(), 3);
        // 有体验券的情况下才计算发放
        if (list.size() > 0) {
            referRecommeDao.addReferRecomme(recomme);
        }
    }

    // 根据门店或者公司以及员工查询数据统计(包含查询分享次数) query 1公司2门店3个人,queryId 为对用的主键
    @Override
    public CompanyNumResponse getReferList(int queryType, int queryId) {
        CompanyNumResponse response = new CompanyNumResponse();
        // 优惠券发放结果集
        List<ReferCompany> list = referRecommeDao.getReferList(queryType, queryId);
        int nowCompantNum = 0;
        int totalCompanyNum = 0;
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                // 处理结果集公司
                if (queryType == 1) {
                    // 获取系统当天时间
                    LocalDate today = LocalDate.now();
                    // 查询出来的时间进行转换
                    LocalDate date = LocalDate.parse(list.get(i).getDayType());
                    LocalDate time = LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
                    // 当天的数量结果集合
                    if (time.equals(today)) {
                        nowCompantNum = list.get(i).getNumber();
                        // 总的数量包含当天的数量
                        totalCompanyNum += list.get(i).getNumber();
                        // 除了当天的人数
                    } else {
                        totalCompanyNum += list.get(i).getNumber();
                    }
                    response.setCompanyReceiveTotal(totalCompanyNum);
                    response.setReceiveCouponNum(nowCompantNum);
                    // 处理结果集门店
                } else if (queryType == 2) {
                    response.setList(list);
                    // 处理结果集个人
                } else if (queryType == 3) {
                    response.setList(list);
                }

            }
        }
        return response;
    }
}
