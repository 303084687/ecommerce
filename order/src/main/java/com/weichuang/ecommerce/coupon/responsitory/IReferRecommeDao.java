package com.weichuang.ecommerce.coupon.responsitory;

import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.coupon.entity.ReferCompany;
import com.weichuang.ecommerce.coupon.entity.ReferRecomme;

/**
 * <p>ClassName: ReferRecommeDao.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:推荐者和被推荐者关系 </p>
 * <p>author wanggongliang</p>
 * <p>2017年11月1日 下午2:06:45</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public interface IReferRecommeDao {
    // 增加
    public int addReferRecomme(ReferRecomme recomme);

    // 根据门店或者公司以及员工查询数据统计 query 1公司2门店3个人,queryId 为对用的主键
    public List<ReferCompany> getReferList(int queryType, int queryId);
}
