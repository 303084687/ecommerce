package com.weichuang.ecommerce.coupon.service;

import com.weichuang.ecommerce.coupon.entity.ReferRecomme;
import com.weichuang.ecommerce.coupon.entity.response.CompanyNumResponse;

/**
 * <p>ClassName: IReferRecommeService.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:推荐优惠券关系 </p>
 * <p>author wanggongliang</p>
 * <p>2017年12月22日 上午11:40:12</p>
 */
public interface IReferRecommeService {
    // 增加
    public void addReferRecomme(ReferRecomme recomme);

    // 根据门店或者公司以及员工查询数据统计(包含查询分享次数) query 1公司2门店3个人,queryId 为对用的主键
    public CompanyNumResponse getReferList(int queryType, int queryId);
}
