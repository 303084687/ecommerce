package com.weichuang.ecommerce.barcode.service;

import com.weichuang.commons.Response;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.barcode.entity.SalesIncomeDetailEntity;
import com.weichuang.ecommerce.barcode.entity.response.BalanceWithdrawResponse;
import com.weichuang.ecommerce.barcode.entity.response.SalesIncomeDetailResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;

/**
* <p>ClassName:ISalesShareEnvelopService</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2018/1/2 10:32</p>
**/
public interface ISalesShareEnvelopService {
    public Response<Integer> getShareEnvelopCount(int saleId) throws ServiceException,Exception;
    public Response<String> getShareEnvelop(HttpServletRequest request,int saleId,String codekey) throws ServiceException,Exception;
    public Response<Integer> getShareEnvelopWithdraw(HttpServletRequest request,int saleId) throws ServiceException,Exception;
    public Response<SalesIncomeDetailResponse> selectIncomeDetailList(int saleId) throws ServiceException,Exception;
    public Response<BalanceWithdrawResponse> selectBalanceWithdraw(int saleId) throws ServiceException,Exception;
}
