package com.weichuang.ecommerce.barcode.repository;

import com.weichuang.ecommerce.barcode.entity.SalesPullNewSetEntity;

import java.util.List;

public interface ISalesPullNewSetDao {
    /**
     *<p>Description:通过活动id查设置</p>
     *<p>param aid:活动id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/27 17:13</p>
     */
    public List<SalesPullNewSetEntity> findListByAid(int aid,int salesId);
    /**
     *<p>Description:查询红包拉新设置</p>
     *<p>param aid:活动id</p>
     *<p>param pullNewNum:拉新个数</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/27 17:32</p>
     */
    public SalesPullNewSetEntity findGtPullNewNum(int aid,int pullNewNum);

    /**
     *<p>Description:查询红包拉新设置</p>
     *<p>param aid:活动id</p>
     *<p>param pullNewNum:拉新个数</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/27 17:32</p>
     */
    public SalesPullNewSetEntity findEqPullNewNum(int aid,int pullNewNum);

    public SalesPullNewSetEntity selectById(int id);
    
    
    /**
     *<p>Description:查询红包拉新设置</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2017/12/26 18:00</p>
     */
    public List<SalesPullNewSetEntity> getAllPullNewSetList();
    
    /**
     *<p>Description:更新红包拉新设置</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2017/12/26 18:00</p>
     */
    public int updatePullNewSet(SalesPullNewSetEntity entity);
    
    /**
     *<p>Description:增加红包拉新设置</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2017/12/26 18:00</p>
     */
    public int addPullNewSet(SalesPullNewSetEntity entity);
    
    
}
