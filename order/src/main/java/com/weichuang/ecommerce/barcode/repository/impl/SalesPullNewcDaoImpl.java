package com.weichuang.ecommerce.barcode.repository.impl;

import com.weichuang.ecommerce.MyBatisConfig;

import com.weichuang.ecommerce.barcode.constants.NameSpaceConstant;
import com.weichuang.ecommerce.barcode.entity.InviteListEntity;
import com.weichuang.ecommerce.barcode.entity.SalesIncomeDetailEntity;
import com.weichuang.ecommerce.barcode.entity.SalesInvteNewCountEntity;
import com.weichuang.ecommerce.barcode.entity.SalesPullNewcEntity;
import com.weichuang.ecommerce.barcode.repository.ISalesPullNewcDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* <p>ClassName:SalesPullNewcDaoImpl</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:邀新二维码dao</p>
* <p>author:zhanghongsheng</p>
* <p>2017/11/20 10:50</p>
**/
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class SalesPullNewcDaoImpl implements ISalesPullNewcDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    //保存
    public int insertNew(SalesPullNewcEntity entity) {
        return sqlSessionTemplate.insert(NameSpaceConstant.SALES_PULL_NEWC+".insertSelective",entity);
    }

    //通过codekey查找邀新信息
    public SalesPullNewcEntity selectByCodekey(String codekey) {
        return sqlSessionTemplate.selectOne(NameSpaceConstant.SALES_PULL_NEWC+".selectByCodekey",codekey);

    }

    /**
     *<p>Description:</p>
     *<p>param codekey:标识主键</p>
     *<p>param state:状态</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/20 15:12</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public int updateInviteNew(String codekey,Integer state,Integer userId) {
        SalesPullNewcEntity entity=new SalesPullNewcEntity();
        entity.setCodeKey(codekey);
        entity.setState(state);
        entity.setUserId(userId);
        return sqlSessionTemplate.update(NameSpaceConstant.SALES_PULL_NEWC+".updateInviteNew",entity);
    }
    /**
     *<p>Description:获取当前销售的邀新个数</p>
     *<p>param aid:活动id</p>
     *<p>param salesId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/27 17:27</p>
     */
    public int selectInviteNewCount(int aid,int salesId){
        SalesPullNewcEntity entity=new SalesPullNewcEntity();
        entity.setAid(aid);
        entity.setSaleId(salesId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.SALES_PULL_NEWC+".selectInviteNewCount",entity);
    }
    /**
     *<p>Description:查询邀新集合</p>
     *<p>param saleId:销售id</p>
     *<p>param state:状态</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/22 10:12</p>
     */
    public List<InviteListEntity> selectInviteList(Integer saleId, Integer state){
        Map param=new HashMap();
        param.put("saleId",saleId);
        param.put("state",state);
        return sqlSessionTemplate.selectList(NameSpaceConstant.SALES_PULL_NEWC+".selectInviteList",param);
    }
    /**
     *<p>Description:邀新数量统计</p>
     *<p>param type:1 公司  2 门店</p>
     *<p>param id: 公司或者门店id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/9 17:56</p>
     *<p>return:</p>
     */
    public SalesInvteNewCountEntity selectInviteNewCountByType(int type, int id){
        Map param=new HashMap();
        param.put("type",type);
        param.put("id",id);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.SALES_PULL_NEWC+".selectInviteNewCountByType",param);
    }
}
