package com.weichuang.ecommerce.barcode.repository.impl;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.barcode.constants.NameSpaceConstant;
import com.weichuang.ecommerce.barcode.entity.SalesPullNewSetEntity;
import com.weichuang.ecommerce.barcode.repository.ISalesPullNewSetDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class SalesPullNewSetDaoImpl implements ISalesPullNewSetDao {
    @Autowired
    private SqlSessionTemplate sessionTemplate;
     /**
     *<p>Description:通过活动id查设置</p>
     *<p>param aid:活动id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/27 17:13</p>
     */
    public List<SalesPullNewSetEntity> findListByAid(int aid,int salesId) {
        Map param=new HashMap();
        param.put("aid",aid);
        param.put("salesId",salesId);
        return sessionTemplate.selectList(NameSpaceConstant.SALES_PULL_NEWC_SET+".findListByAid",param);
    }

    /**
     *<p>Description:查询红包拉新设置</p>
     *<p>param aid:活动id</p>
     *<p>param pullNewNum:拉新个数</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/27 17:32</p>
     */
    public SalesPullNewSetEntity findGtPullNewNum(int aid, int pullNewNum) {

        SalesPullNewSetEntity entity=new SalesPullNewSetEntity();
        entity.setAid(aid);
        entity.setPullNewNum(pullNewNum);
        return sessionTemplate.selectOne(NameSpaceConstant.SALES_PULL_NEWC_SET+".findGtPullNewNum",entity);
    }
    /**
     *<p>Description:查询红包拉新设置</p>
     *<p>param aid:活动id</p>
     *<p>param pullNewNum:拉新个数</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/27 17:32</p>
     */
    public SalesPullNewSetEntity findEqPullNewNum(int aid, int pullNewNum) {

        SalesPullNewSetEntity entity=new SalesPullNewSetEntity();
        entity.setAid(aid);
        entity.setPullNewNum(pullNewNum);
        return sessionTemplate.selectOne(NameSpaceConstant.SALES_PULL_NEWC_SET+".findEqPullNewNum",entity);
    }
    /**
     *<p>Description:</p>
     *<p>param id:</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/28 17:00</p>
     */
    public SalesPullNewSetEntity selectById(int id){
        return sessionTemplate.selectOne(NameSpaceConstant.SALES_PULL_NEWC_SET+".selectById",id);

    }
    
    /**
     *<p>Description:查询红包拉新设置</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2017/12/26 18:00</p>
     */
    public List<SalesPullNewSetEntity> getAllPullNewSetList(){
        return sessionTemplate.selectList(NameSpaceConstant.SALES_PULL_NEWC_SET+".getAllPullNewSetList");
    }
    
    /**
     *<p>Description:更新红包拉新设置</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2017/12/26 18:00</p>
     */
    public int updatePullNewSet(SalesPullNewSetEntity entity){
        return sessionTemplate.update(NameSpaceConstant.SALES_PULL_NEWC_SET+".updatePullNewSet",entity);
    }
    
    /**
     *<p>Description:增加红包拉新设置</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2017/12/26 18:00</p>
     */
    public int addPullNewSet(SalesPullNewSetEntity entity){
        return sessionTemplate.insert(NameSpaceConstant.SALES_PULL_NEWC_SET+".addPullNewSet",entity);
        
    }
}
