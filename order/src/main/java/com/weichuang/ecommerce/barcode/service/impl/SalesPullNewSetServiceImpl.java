package com.weichuang.ecommerce.barcode.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.EncryptUtil;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.barcode.entity.InviteListEntity;
import com.weichuang.ecommerce.barcode.entity.SalesPullNewSetEntity;
import com.weichuang.ecommerce.barcode.entity.request.SalesPullNewSetRequest;
import com.weichuang.ecommerce.barcode.entity.response.InviteListResponse;
import com.weichuang.ecommerce.barcode.entity.response.SalesPullNewSetListResponse;
import com.weichuang.ecommerce.barcode.entity.response.SalesPullNewSetResponse;
import com.weichuang.ecommerce.barcode.repository.ISalesPullNewSetDao;
import com.weichuang.ecommerce.barcode.repository.ISalesPullNewcDao;
import com.weichuang.ecommerce.barcode.service.ISalesPullNewSetService;
import com.weichuang.ecommerce.barcode.service.ISalesPullNewcService;
import com.weichuang.ecommerce.weixinPay.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <p>ClassName:SalesPullNewcServiceImpl</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:liuzhanchao</p>
* <p>2017/12/26 10:18</p>
**/
@Service
public class SalesPullNewSetServiceImpl implements ISalesPullNewSetService {
    @Autowired
    private ISalesPullNewSetDao pullNewSetDao;

    /**
     *<p>Description:查询红包拉新设置</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2017/12/26 18:00</p>
     */
    public SalesPullNewSetListResponse getAllPullNewSetList()
            throws ServiceException {
        SalesPullNewSetListResponse listResponse = new SalesPullNewSetListResponse();
        List<SalesPullNewSetEntity> list = pullNewSetDao.getAllPullNewSetList();
        if (list.size() > 0) {
            listResponse.setList(list);
        }
        return listResponse;
    }

    /**
     *<p>Description:更新红包拉新设置</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2017/12/26 18:00</p>
     */
    public int updatePullNewSet(SalesPullNewSetRequest request)
            throws ServiceException {

        SalesPullNewSetEntity entity = new SalesPullNewSetEntity();
        entity.setAid(request.getAid());
        entity.setOperatorId(request.getOperatorId());
        entity.setId(request.getId());
        entity.setPullNewIncome(request.getPullNewIncome());
        entity.setPullNewNum(request.getPullNewNum());
        entity.setState(request.getState());
        int result = pullNewSetDao.updatePullNewSet(entity);
        return result;

    }

    /**
     *<p>Description:增加红包拉新设置</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2017/12/26 22:00</p>
     */
    public int addPullNewSet(SalesPullNewSetRequest request)
            throws ServiceException {
        SalesPullNewSetEntity entity = new SalesPullNewSetEntity();
        entity.setAid(request.getAid());
        entity.setOperatorId(request.getOperatorId());
        entity.setPullNewIncome(request.getPullNewIncome());
        entity.setPullNewNum(request.getPullNewNum());
        entity.setState(request.getState());
        int result = pullNewSetDao.addPullNewSet(entity);
        return result;
    }
}
