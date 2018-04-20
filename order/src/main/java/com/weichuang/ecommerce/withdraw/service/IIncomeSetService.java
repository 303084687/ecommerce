package com.weichuang.ecommerce.withdraw.service;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.withdraw.entity.request.IncomeSetRequest;
import com.weichuang.ecommerce.withdraw.entity.request.WithdrawEntity;
import com.weichuang.ecommerce.withdraw.entity.response.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>ClassName: IIncomeSetService.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:提成设置</p>
 * <p>author liuzhanchao</p>
 * <p>2017年12月27日 13:28:46</p>
 */
@Service
public interface IIncomeSetService {
    /**
     * 获取提成参数列表
     * @return
     * @throws ServiceException
     */
    public IncomeSetListResponse getAllIncomeSetList() throws ServiceException;

    /**
     * 更新提成设置参数
     * @param request
     * @return
     * @throws ServiceException
     */
    public int updateIncomeSet(IncomeSetRequest request) throws ServiceException;

    /**
     * 新增提成设置参数
     * @param request
     * @return
     * @throws ServiceException
     */
    public int addIncomeSet(IncomeSetRequest request) throws ServiceException;

    /**
     * 更改提成参数状态---0：失效；1：正常
     * @param request
     * @return
     * @throws ServiceException
     */
    public int updateIncomeSetStatus(IncomeSetRequest request) throws ServiceException;

}
