package com.weichuang.ecommerce.withdraw.repository;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.MyBatisConfig;

import com.weichuang.ecommerce.withdraw.entity.IncomeSetEntity;
import com.weichuang.ecommerce.withdraw.entity.request.IncomeSetRequest;
import com.weichuang.ecommerce.withdraw.entity.response.IncomeSetListResponse;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * <p>ClassName: IIncomeSetDao.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:提成参数设置 </p>
 * <p>author liuzhanchao</p>
 * <p>2017年12月27日 下午2:44:28</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public interface IIncomeSetDao {

    /**
     * 获取提成参数列表
     * @return
     * @throws ServiceException
     */
    public List<IncomeSetEntity> getAllIncomeSetList() ;

    /**
     * 更新提成设置参数
     * @param entity
     * @return
     * @throws ServiceException
     */
    public int updateIncomeSet(IncomeSetEntity entity) ;

    /**
     * 新增提成设置参数
     * @param entity
     * @return
     * @throws ServiceException
     */
    public int addIncomeSet(IncomeSetEntity entity) ;

    /**
     * 更改提成参数状态---0：失效；1：正常
     * @param entity
     * @return
     * @throws ServiceException
     */
    public int updateIncomeSetStatus(IncomeSetEntity entity) ;

}
