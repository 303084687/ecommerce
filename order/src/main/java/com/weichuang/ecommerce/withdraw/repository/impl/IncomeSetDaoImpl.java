package com.weichuang.ecommerce.withdraw.repository.impl;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.RedisHelper;
import com.weichuang.ecommerce.withdraw.constants.NameSpaceConstant;
import com.weichuang.ecommerce.withdraw.entity.IncomeSetEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesWithdrawDetailEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesWithdrawEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesWithdrawInfoEntity;
import com.weichuang.ecommerce.withdraw.repository.IIncomeSetDao;
import com.weichuang.ecommerce.withdraw.repository.ISalesWithdrawDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>ClassName: IncomeSetDaoImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description: :提成参数修改</p>
 * <p>author liuzhanchao</p>
 * <p>2017年12月27日 下午2:50:49</p>
 */

@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class IncomeSetDaoImpl implements IIncomeSetDao {
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    private RedisHelper redisHelper;


    /**
     * 获取提成参数列表
     * @return
     * @throws ServiceException
     */
    @Override
    @Cacheable(value = "incomeDao",key = "'incomeDao_'+#root.methodName")
    public List<IncomeSetEntity> getAllIncomeSetList(){
        return sqlSessionTemplate.selectList(NameSpaceConstant.INCOME_SET
                + ".selectAllIncomeSet");

    }

    /**
     * 更新提成设置参数
     * @param entity
     * @return
     * @throws ServiceException
     */
    @Override
    public int updateIncomeSet(IncomeSetEntity entity) {
        int res= sqlSessionTemplate.update(NameSpaceConstant.INCOME_SET
                + ".updateIncomeSet",entity);
        if (res > 0) {
            redisHelper.del("incomeDao_getAllIncomeSetList");
        }
        return res;
    }

    /**
     * 新增提成设置参数
     * @param entity
     * @return
     * @throws ServiceException
     */
    @Override
    public int addIncomeSet(IncomeSetEntity entity) {
        int res= sqlSessionTemplate.insert(NameSpaceConstant.INCOME_SET
                + ".addIncomeSet",entity);
        if (res > 0) {
            redisHelper.del("incomeDao_getAllIncomeSetList");
        }
        return res;
    }

    /**
     * 更改提成参数状态---0：失效；1：正常
     * @param entity
     * @return
     * @throws ServiceException
     */
    @Override
    public int updateIncomeSetStatus(IncomeSetEntity entity) {
        int res= sqlSessionTemplate.update(NameSpaceConstant.INCOME_SET
                + ".updateIncomeSetStatus",entity);
        if (res > 0) {
            redisHelper.del("incomeDao_getAllIncomeSetList");
        }
        return res;
    }

}
