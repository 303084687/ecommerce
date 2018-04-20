package com.weichuang.ecommerce.barcode.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.EncryptUtil;
import com.weichuang.ecommerce.barcode.entity.InviteListEntity;
import com.weichuang.ecommerce.barcode.entity.SalesInvteNewCountEntity;
import com.weichuang.ecommerce.barcode.entity.response.InviteListResponse;
import com.weichuang.ecommerce.barcode.entity.response.SalesInvteNewCountResponse;
import com.weichuang.ecommerce.barcode.repository.ISalesPullNewcDao;
import com.weichuang.ecommerce.barcode.service.ISalesPullNewcService;
import com.weichuang.ecommerce.weixinPay.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* <p>ClassName:SalesPullNewcServiceImpl</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/22 10:18</p>
**/
@Service
public class SalesPullNewcServiceImpl implements ISalesPullNewcService {
    @Autowired
    private ISalesPullNewcDao pullNewcDao;
    /**
     *<p>Description:分页查询邀新list</p>
     *<p>param pageNum:当前页</p>
     *<p>param pageSize:每页大小</p>
     *<p>param saleId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/22 10:33</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public InviteListResponse selectInviteList(Integer pageNum,Integer pageSize,Integer saleId) throws Exception{
        PageHelper.startPage(pageNum,pageSize);
        List<InviteListEntity> list=pullNewcDao.selectInviteList(saleId,1);
        PageInfo p=new PageInfo(list);
        Integer total=new Long(p.getTotal()).intValue();
        Integer pages=p.getPages();
        InviteListResponse response=new InviteListResponse();
        if(list!=null &&!list.isEmpty()){
            for(InviteListEntity entity:list){
                String nickName=entity.getNickName();
                if(StringUtils.isNotEmpty(nickName)){
                    entity.setNickName(EncryptUtil.getFromBase64(nickName));//解密昵称
                }
            }
        }
        response.setList(list);
        response.setPages(pages);
        response.setTotal(total);
        return response;
    }
    /**
     *<p>Description:邀新数量统计</p>
     *<p>param type:1 公司  2 门店</p>
     *<p>param id: 公司或者门店id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/9 17:56</p>
     *<p>return:</p>
     */
    public SalesInvteNewCountResponse selectSalesInvteNewCount(int type, int id){
        SalesInvteNewCountResponse response=new SalesInvteNewCountResponse();
        SalesInvteNewCountEntity entity=pullNewcDao.selectInviteNewCountByType(type,id);
        BeanUtils.copyProperties(entity,response);
        return response;
    }
}
