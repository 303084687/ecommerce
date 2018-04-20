package com.weichuang.ecommerce.appVersion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.appVersion.entity.AppVersionEntity;
import com.weichuang.ecommerce.appVersion.entity.response.AppVersionListResponse;
import com.weichuang.ecommerce.appVersion.entity.response.AppVersionResponse;
import com.weichuang.ecommerce.appVersion.repository.IAppVersionDao;
import com.weichuang.ecommerce.appVersion.service.IAppVersionService;

/**
 * <p>ClassName: AppVersionServiceImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description: 版本管理service实现</p>
 * <p>author wanggongliang</p>
 * <p>2017年11月22日 下午3:23:48</p>
 */
@Service
@SuppressWarnings("all")
public class AppVersionServiceImpl implements IAppVersionService {
    // 注册版本服务接口
    @Autowired
    private IAppVersionDao appVersionDao;

    /**
     * @Title:getAppVersion  
     * @Description:根据客户端查询版本号
     * @param platForm 1安卓2ios
     * @param type 1老板助手2店员助手
     * @return
     */
    @Override
    public AppVersionResponse getAppVersion(int platForm, int type) throws ServiceException {
        AppVersionResponse response = new AppVersionResponse();
        AppVersionEntity entity = appVersionDao.getAppVersion(platForm, type);
        response.setEntity(entity);
        return response;
    }

    /**
     * @Title:getlist  
     * @Description:后台版本管理列表数据
     * @return
     */
    @Override
    public AppVersionListResponse getList(int platForm, int pageNum, int pageSize) throws ServiceException {
        // 执行查询数据和分页处理
        PageHelper.startPage(pageNum, pageSize);
        AppVersionListResponse response = new AppVersionListResponse();
        List<AppVersionEntity> list = appVersionDao.getList(platForm);
        PageInfo pageInfo = new PageInfo(list);
        response.setList(list);// 返回的数据集合
        response.setPages(pageInfo.getPages());// 返回的总页数，用于分页使用
        response.setTotal(pageInfo.getTotal());// 返回的数据总个数
        return response;
    }

    /**
     * @Title:addAppVersion  
     * @Description: 增加版本更新
     * @param version
     * @return
     */
    @Override
    public int addAppVersion(AppVersionEntity version) throws ServiceException {

        return appVersionDao.addAppVersion(version);
    }

    /**
     * @Title:updateAppVersion  
     * @Description:修改版本更新
     * @param version
     * @return
     */
    @Override
    public int updateAppVersion(AppVersionEntity version) throws ServiceException {
        return appVersionDao.updateAppVersion(version);
    }

    /**
     * @Title:getAppVersionById  
     * @Description:根据主键查询版本信息
     * @param id
     * @return
     */
    @Override
    public AppVersionResponse getAppVersionById(int id) throws ServiceException {
        AppVersionResponse response = new AppVersionResponse();
        AppVersionEntity entity = appVersionDao.getAppVersionById(id);
        response.setEntity(entity);
        return response;
    }

}
