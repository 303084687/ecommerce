package com.weichuang.ecommerce.barcode.service;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.barcode.entity.InviteNewEnvelopResponse;
import com.weichuang.ecommerce.barcode.entity.SalesPullNewcEntity;
import com.weichuang.ecommerce.barcode.entity.request.CodeAndTextRequest;
import com.weichuang.ecommerce.barcode.entity.request.TenantCodeStoreRequest;
import com.weichuang.ecommerce.barcode.entity.response.SalesPullNewcResponse;
import com.weichuang.ecommerce.barcode.entity.response.TenantCodeStoreResponse;
import com.weichuang.ecommerce.barcode.entity.response.UserCountResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BarcodeService {
    public TenantCodeStoreResponse addBarCode(TenantCodeStoreRequest req,boolean hasimg) throws ServiceException;
    /**
     *<p>Description:分享商品二维码生成</p>
     *<p>param userAgentId:销售id</p>
     *<p>param productId:商品id</p>
     *<p>param productCode:商品编码</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/23 10:12</p>
     *<p>return:</p>
     */
    public TenantCodeStoreResponse addShareBarCode( int userAgentId, int productId,String productCode) throws ServiceException,Exception;
    public TenantCodeStoreResponse addReceiverBarCode( int userId) throws ServiceException,Exception;
    public TenantCodeStoreResponse addInviteSaleBarCode( int userId) throws ServiceException,Exception;
    public TenantCodeStoreResponse addInviteBarCode(TenantCodeStoreRequest req,String mobile) throws ServiceException,Exception;
    public SalesPullNewcResponse getInviteNewBycode(String codekey) throws ServiceException,Exception ;
    public TenantCodeStoreResponse getByCodeKey(String codeKey) throws ServiceException;
    public void mergeImage(CodeAndTextRequest request, HttpServletResponse response) throws Exception;
    /**
     *<p>Description:修改状态</p>
     *<p>param codekey:标识主键</p>
     *<p>param state:状态</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/20 15:12</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public int updateInviteNew(String codekey,Integer state,Integer userId)throws Exception;

    public InviteNewEnvelopResponse getByUserId(Integer userId) throws ServiceException,Exception;

    public int insertNew(SalesPullNewcEntity entity)throws ServiceException,Exception;

    public int RedEnvelopRece(HttpServletRequest req, int salesId, int rid);

    public UserCountResponse getInviteCount(int type,int userId);
}
