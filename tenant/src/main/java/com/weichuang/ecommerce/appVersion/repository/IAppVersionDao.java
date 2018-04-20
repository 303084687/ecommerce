package com.weichuang.ecommerce.appVersion.repository;

import java.util.List;

import com.weichuang.ecommerce.appVersion.entity.AppVersionEntity;

/**
 * <p>ClassName: IAppVersionDao.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:app版本管理接口 </p>
 * <p>author wanggongliang</p>
 * <p>2017年11月22日 下午2:03:05</p>
 */
public interface IAppVersionDao {
    /**
     * @Title:getAppVersion  
     * @Description:根据客户端查询版本号
     * @param platForm 1安卓2ios
     * @param type 1老板助手2店员助手
     * @return
     */
    public AppVersionEntity getAppVersion(int platForm, int type);

    /**
     * @Title:getlist  
     * @Description:后台版本管理列表数据
     * @return
     */
    public List<AppVersionEntity> getList(int platForm);

    /**
     * @Title:addAppVersion  
     * @Description: 增加版本更新
     * @param version
     * @return
     */
    public int addAppVersion(AppVersionEntity version);

    /**
     * @Title:updateAppVersion  
     * @Description:修改版本更新
     * @param version
     * @return
     */
    public int updateAppVersion(AppVersionEntity version);

    /**
     * @Title:getAppVersionById  
     * @Description:根据主键查询版本信息
     * @param id
     * @return
     */
    public AppVersionEntity getAppVersionById(int id);
}
