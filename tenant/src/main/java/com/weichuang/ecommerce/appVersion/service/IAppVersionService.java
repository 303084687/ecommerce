package com.weichuang.ecommerce.appVersion.service;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.appVersion.entity.AppVersionEntity;
import com.weichuang.ecommerce.appVersion.entity.response.AppVersionListResponse;
import com.weichuang.ecommerce.appVersion.entity.response.AppVersionResponse;

/**
 * <p>ClassName: IAppVersionService.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description: 版本管理service</p>
 * <p>author wanggongliang</p>
 * <p>2017年11月22日 下午3:20:16</p>
 */
public interface IAppVersionService {
    /**
     * @Title:getAppVersion  
     * @Description:根据客户端查询版本号
     * @param platForm 1安卓2ios
     * @param type 1老板助手2店员助手
     * @return
     */
    public AppVersionResponse getAppVersion(int platForm, int type) throws ServiceException;

    /**
     * @Title:getlist  
     * @Description:后台版本管理列表数据
     * @return
     */
    public AppVersionListResponse getList(int platForm, int pageNum, int pageSize) throws ServiceException;

    /**
     * @Title:addAppVersion  
     * @Description: 增加版本更新
     * @param version
     * @return
     */
    public int addAppVersion(AppVersionEntity version) throws ServiceException;

    /**
     * @Title:updateAppVersion  
     * @Description:修改版本更新
     * @param version
     * @return
     */
    public int updateAppVersion(AppVersionEntity version) throws ServiceException;

    /**
     * @Title:getAppVersionById  
     * @Description:根据主键查询版本信息
     * @param id
     * @return
     */
    public AppVersionResponse getAppVersionById(int id) throws ServiceException;

}
