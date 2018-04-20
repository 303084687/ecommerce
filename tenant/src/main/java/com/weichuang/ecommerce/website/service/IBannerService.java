package com.weichuang.ecommerce.website.service;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.website.entity.request.WebsiteBannerRequest;
import com.weichuang.ecommerce.website.entity.response.WebsiteBannerListResponse;
import com.weichuang.ecommerce.website.entity.response.WebsiteBannerResponse;

/**
 * <p>ClassName: IBannerService.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:网站banner列表service接口 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月7日 下午2:09:51</p>
 */
public interface IBannerService {
    /**
     * @Title:addBanner  
     * @Description:增加
     * @param banner 实体
     * @return
     */
    public int addBanner(WebsiteBannerRequest request) throws ServiceException;

    /**
     * @Title:updateBanner  
     * @Description:修改(包含设置发布状态)
     * @param banner 实体
     * @return
     */
    public int updateBanner(WebsiteBannerRequest request) throws ServiceException;

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
    public WebsiteBannerListResponse bannerList(String title, int type, int status, String startTime, String endTime,
            int pageNum, int pageSize, int classify) throws ServiceException;

    /**
     * @Title:channelList  
     * @Description:根据渠道和类型列表显示(提供给前端使用)
     * @param channe 1web 2h5 3安卓 4ios
     * @param type 类型 目前默认为1
     * @return
     */
    public WebsiteBannerListResponse channelList(int channel, String type, int pageNum, int pageSize)
            throws ServiceException;

    /**
     * @Title:selectById  
     * @Description:根据主键查询详情
     * @param id 主键
     * @return
     */
    public WebsiteBannerResponse selectById(int id) throws ServiceException;

    /**
     * @Title: updateHotById
     * @Description:置顶功能
     * @param id 主键
     * @param maxSort 查询出来的排序最大数
     * @return
     */
    public int updateHotById(int id, int type) throws ServiceException;

}
