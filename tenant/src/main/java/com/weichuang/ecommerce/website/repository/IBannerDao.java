package com.weichuang.ecommerce.website.repository;

import java.util.List;

import com.weichuang.ecommerce.website.entity.WebsiteBannerEntity;

/**
 * <p>ClassName: IBannerDao.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:banner管理接口 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月7日 上午11:32:12</p>
 */
public interface IBannerDao {
    /**
     * @Title:addBanner  
     * @Description:增加
     * @param banner 实体
     * @return
     */
    public int addBanner(WebsiteBannerEntity banner);

    /**
     * @Title:updateBanner  
     * @Description:修改(包含设置发布状态)
     * @param banner 实体
     * @return
     */
    public int updateBanner(WebsiteBannerEntity banner);

    /**
     * @Title:bannerList  
     * @Description:管理列表显示
     * @param title 标题
     * @param type 类型 目前默认为1
     * @param status 状态1正常2禁止
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    public List<WebsiteBannerEntity> bannerList(String title, int type, int status, String startTime, String endTime,
            int classify);

    /**
     * @Title:channelList  
     * @Description:根据渠道和类型列表显示(提供给前端使用)
     * @param channe 1web 2h5 3安卓 4ios
     * @param type 类型 目前默认为1
     * @return
     */
    public List<WebsiteBannerEntity> channelList(int channel, String types, String systemTime);

    /**
     * @Title:selectById  
     * @Description:根据主键查询详情
     * @param id 主键
     * @return
     */
    public WebsiteBannerEntity selectById(int id);

    /**
     * @Title: updateHotById
     * @Description:置顶功能
     * @param id 主键
     * @param maxSort 查询出来的排序最大数
     * @return
     */
    public int updateHotById(int id, int maxSort, int type);

    /**
     * @Title: maxSort
     * @Description:根据类型查询出最大的排序参数
     * @param type
     * @return
     */
    public int maxSort(int type);

    /**
     * @Title:updateBrowseNumber  
     * @Description:根据主键更新浏览记录数
     * @param id
     * @return
     */
    public int updateBrowseNumber(int id);

}
