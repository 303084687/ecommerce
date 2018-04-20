package com.weichuang.ecommerce.withdraw.service.impl;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.withdraw.entity.IncomeSetEntity;
import com.weichuang.ecommerce.withdraw.entity.request.IncomeSetRequest;
import com.weichuang.ecommerce.withdraw.entity.response.IncomeSetListResponse;
import com.weichuang.ecommerce.withdraw.repository.IIncomeSetDao;
import com.weichuang.ecommerce.withdraw.service.IIncomeSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * ClassName: IWithdrawService.java
 * </p>
 * <p>
 * Company: 伟创科技(北京)科技有限公司
 * </p>
 * <p>
 * Description:销售和代理分红service
 * </p>
 * <p>
 * author liuzhanchao
 * </p>
 * <p>
 * 2017年12月27日 下午2:28:46
 * </p>
 */
@Service
@SuppressWarnings("all")
public class IncomeSetServiceImpl implements IIncomeSetService {
    @Autowired
    private IIncomeSetDao incomeSetDao;


    /**
     * 获取提成设置参数
     *
     * @return
     * @throws Exception
     */
    @Override
    public IncomeSetListResponse getAllIncomeSetList() throws ServiceException {
        IncomeSetListResponse listResponse = new IncomeSetListResponse();
        List<IncomeSetEntity> list = incomeSetDao.getAllIncomeSetList();
        listResponse.setList(list);

        return listResponse;
    }

    /**
     * <p>Description:更新提成参数设置</p>
     * <p>author:liuzhanchao</p>
     * <p>date:2017/12/27 11:00</p>
     */
    @Override
    public int updateIncomeSet(IncomeSetRequest request) throws ServiceException {
        IncomeSetEntity entity = new IncomeSetEntity();
        entity.setAgentPercent(request.getAgentPercent());
        entity.setId(request.getId());
        entity.setSalesPercent(request.getSalesPercent());
        int res = incomeSetDao.updateIncomeSet(entity);
        return res;
    }

    /**
     * <p>Description:增加提成设置</p>
     * <p>author:liuzhanchao</p>
     * <p>date:2017/12/27 11:40</p>
     */
    @Override
    public int addIncomeSet(IncomeSetRequest request) throws ServiceException {
        IncomeSetEntity entity = new IncomeSetEntity();
        entity.setAgentPercent(request.getAgentPercent());
        entity.setType(request.getType());
        entity.setSalesPercent(request.getSalesPercent());
        int res=incomeSetDao.addIncomeSet(entity);
        return res;
    }

    /**
     * <p>Description:更新提成参数状态0：失效；1：正常</p>
     * <p>author:liuzhanchao</p>
     * <p>date:2017/12/27 11:00</p>
     */
    @Override
    public int updateIncomeSetStatus(IncomeSetRequest request) throws ServiceException {
        IncomeSetEntity entity = new IncomeSetEntity();
        entity.setId(request.getId());
        entity.setStatus(request.getStatus());
        return incomeSetDao.updateIncomeSetStatus(entity);
    }
}
