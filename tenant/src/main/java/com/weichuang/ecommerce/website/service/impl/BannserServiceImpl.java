package com.weichuang.ecommerce.website.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.website.entity.WebsiteBannerEntity;
import com.weichuang.ecommerce.website.entity.request.WebsiteBannerRequest;
import com.weichuang.ecommerce.website.entity.response.WebsiteBannerListResponse;
import com.weichuang.ecommerce.website.entity.response.WebsiteBannerResponse;
import com.weichuang.ecommerce.website.repository.IBannerDao;
import com.weichuang.ecommerce.website.service.IBannerService;

/**
 * <p>ClassName: BannserServiceImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:banner服务接口实现类 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月7日 下午2:47:03</p>
 */
@Service
@SuppressWarnings("all")
public class BannserServiceImpl implements IBannerService {
    // 注册banner服务接口
    @Autowired
    private IBannerDao bannerDao;

    /**
     * @Title:addBanner  
     * @Description:增加
     * @param banner 实体
     * @return
     */
    @Override
    @Transactional
    public int addBanner(WebsiteBannerRequest request) throws ServiceException {
        WebsiteBannerEntity entity = new WebsiteBannerEntity();
        BeanUtils.copyProperties(request, entity);
        return bannerDao.addBanner(entity);
    }

    /**
     * @Title:updateBanner  
     * @Description:修改(包含设置发布状态)
     * @param banner 实体
     * @return
     */
    @Override
    @Transactional
    public int updateBanner(WebsiteBannerRequest request) throws ServiceException {
        WebsiteBannerEntity entity = new WebsiteBannerEntity();
        BeanUtils.copyProperties(request, entity);
        return bannerDao.updateBanner(entity);
    }

    /**
     * @Title:bannerList  
     * @Description:管理列表显示
     * @param title 标题
     * @param type 类型 目前默认为1
     * @param status 状态1正常2禁止
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNum当前页码pageSize每页显示的记录数
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public WebsiteBannerListResponse bannerList(String title, int type, int status, String startTime, String endTime,
            int pageNum, int pageSize, int classify) throws ServiceException {
        // 执行查询数据和分页处理
        PageHelper.startPage(pageNum, pageSize);
        WebsiteBannerListResponse response = new WebsiteBannerListResponse();
        List<WebsiteBannerEntity> list = bannerDao.bannerList(title, type, status, startTime, endTime, classify);
        PageInfo pageInfo = new PageInfo(list);
        response.setList(list);// 返回的数据集合
        response.setPages(pageInfo.getPages());// 返回的总页数，用于分页使用
        return response;

    }

    /**
     * @Title:channelList  
     * @Description:根据渠道和类型列表显示(提供给前端使用)
     * @param channe 1web 2h5 3安卓 4ios
     * @param type 类型 目前默认为1
     * @param pageNum当前页码pageSize每页显示的记录数
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public WebsiteBannerListResponse channelList(int channel, String type, int pageNum, int pageSize)
            throws ServiceException {
        // 获取系统当前时间并格式化
        String systemTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        // 执行查询数据和分页处理
        PageHelper.startPage(pageNum, pageSize);
        WebsiteBannerListResponse response = new WebsiteBannerListResponse();
        List<WebsiteBannerEntity> list = bannerDao.channelList(channel, type, systemTime);
        PageInfo pageInfo = new PageInfo(list);
        response.setList(list);// 返回的数据集合
        response.setPages(pageInfo.getPages());// 返回的总页数，用于分页使用
        return response;
    }

    /**
     * @Title:selectById  
     * @Description:根据主键查询详情
     * @param id 主键
     * @return
     */
    @Override
    @Transactional
    public WebsiteBannerResponse selectById(int id) throws ServiceException {
        WebsiteBannerEntity entity = bannerDao.selectById(id);
        // 增加浏览次数
        bannerDao.updateBrowseNumber(id);
        WebsiteBannerResponse response = new WebsiteBannerResponse();
        response.setEntity(entity);
        return response;
    }

    /**
     * @Title: updateHotById
     * @Description:置顶功能
     * @param id 主键
     * @param maxSort 查询出来的排序最大数
     * @return
     */
    @Override
    @Transactional
    public int updateHotById(int id, int type) throws ServiceException {
        // 根据类型的不同查询最大的排序数
        int maxSort = bannerDao.maxSort(type);
        // 更新排序置顶
        return bannerDao.updateHotById(id, maxSort, type);
    }

}
