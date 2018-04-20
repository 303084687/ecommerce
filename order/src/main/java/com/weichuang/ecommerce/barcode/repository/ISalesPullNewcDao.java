package com.weichuang.ecommerce.barcode.repository;


import com.weichuang.ecommerce.barcode.entity.InviteListEntity;
import com.weichuang.ecommerce.barcode.entity.SalesInvteNewCountEntity;
import com.weichuang.ecommerce.barcode.entity.SalesPullNewcEntity;

import java.util.List;

/**
* <p>ClassName:ISalesPullNewcDao</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:邀新接口</p>
* <p>author:zhanghongsheng</p>
* <p>2017/11/20 10:44</p>
**/
public interface ISalesPullNewcDao {
    public int insertNew(SalesPullNewcEntity entity);
    public SalesPullNewcEntity selectByCodekey(String codekey);
    /**
     *<p>Description:</p>
     *<p>param codekey:标识主键</p>
     *<p>param state:状态</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/20 15:12</p>
     */
    public int updateInviteNew(String codekey,Integer state,Integer userId);
    /**
     *<p>Description:获取当前销售的邀新个数</p>
     *<p>param aid:活动id</p>
     *<p>param salesId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/27 17:27</p>
     */
    public int selectInviteNewCount(int aid,int salesId);
    /**
     *<p>Description:查询邀新集合</p>
     *<p>param saleId:销售id</p>
     *<p>param state:状态</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/22 10:12</p>
     */
    public  List<InviteListEntity> selectInviteList(Integer saleId,Integer state);
    /**
     *<p>Description:邀新数量统计</p>
     *<p>param type:1 公司  2 门店</p>
     *<p>param id: 公司或者门店id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/9 17:56</p>
     *<p>return:</p>
     */
    public SalesInvteNewCountEntity selectInviteNewCountByType(int type,int id);
}
