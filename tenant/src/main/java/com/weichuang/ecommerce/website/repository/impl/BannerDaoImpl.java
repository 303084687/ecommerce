package com.weichuang.ecommerce.website.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.website.constants.NameSpaceConstant;
import com.weichuang.ecommerce.website.entity.WebsiteBannerEntity;
import com.weichuang.ecommerce.website.repository.IBannerDao;

/**
 * <p>ClassName: BannerDaoImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:banner接口实现类 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月7日 下午1:38:14</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class BannerDaoImpl implements IBannerDao {
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate;

    /**
     * @Title:addBanner  
     * @Description:增加
     * @param banner 实体
     * @return
     */
    @Override
    public int addBanner(WebsiteBannerEntity banner) {
        return sqlSessionTemplate.insert(NameSpaceConstant.WEBSITE_BANNER + ".insert", banner);
    }

    /**
     * @Title:updateBanner  
     * @Description:修改(包含设置发布状态)
     * @param banner 实体
     * @return
     */
    @Override
    public int updateBanner(WebsiteBannerEntity banner) {
        return sqlSessionTemplate.update(NameSpaceConstant.WEBSITE_BANNER + ".update", banner);
    }

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
    @Override
    public List<WebsiteBannerEntity> bannerList(String title, int type, int status, String startTime, String endTime,
            int classify) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);// 类型
        map.put("status", status);// 状态
        map.put("title", title);// 标题
        map.put("classify", classify);// 1轮播2其它
        map.put("startTime", startTime);// 开始时间
        map.put("endTime", endTime);// 结束时间
        return sqlSessionTemplate.selectList(NameSpaceConstant.WEBSITE_BANNER + ".bannerList", map);
    }

    /**
     * @Title:channelList  
     * @Description:根据渠道和类型列表显示(提供给前端使用)
     * @param channe 1web 2h5 3安卓 4ios
     * @param type 类型 目前默认为1
     * @param systemTime 系统时间
     * @return
     */
    @Override
    public List<WebsiteBannerEntity> channelList(int channel, String types, String systemTime) {
        Map<String, Object> map = new HashMap<>();
        List<Integer> tids = new ArrayList<Integer>();
        for (String pid : types.split(",")) {
            tids.add(Integer.parseInt(pid));
        }
        map.put("types", tids);// 类型
        map.put("channel", channel);// 状态
        map.put("systemTime", systemTime);// 状态
        return sqlSessionTemplate.selectList(NameSpaceConstant.WEBSITE_BANNER + ".channelList", map);
    }

    /**
     * @Title:selectById  
     * @Description:根据主键查询详情
     * @param id 主键
     * @return
     */
    @Override
    public WebsiteBannerEntity selectById(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);// 类型
        return sqlSessionTemplate.selectOne(NameSpaceConstant.WEBSITE_BANNER + ".selectById", map);
    }

    /**
     * @Title: updateHotById
     * @Description:置顶功能
     * @param id 主键
     * @param maxSort 查询出来的排序最大数
     * @return
     */
    @Override
    public int updateHotById(int id, int maxSort, int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);// 主键
        map.put("maxSort", maxSort);// 状态
        map.put("type", type);// 类型
        return sqlSessionTemplate.update(NameSpaceConstant.WEBSITE_BANNER + ".updateHotById", map);
    }

    /**
     * @Title: maxSort
     * @Description:根据类型查询出最大的排序参数
     * @param type
     * @return
     */
    @Override
    public int maxSort(int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);// 类型
        return sqlSessionTemplate.selectOne(NameSpaceConstant.WEBSITE_BANNER + ".maxSort", map);
    }

    /**
     * @Title:updateBrowseNumber  
     * @Description:根据主键更新浏览记录数
     * @param id
     * @return
     */
    @Override
    public int updateBrowseNumber(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);// 主键
        return sqlSessionTemplate.update(NameSpaceConstant.WEBSITE_BANNER + ".updateBrowseNumber", map);
    }
}
