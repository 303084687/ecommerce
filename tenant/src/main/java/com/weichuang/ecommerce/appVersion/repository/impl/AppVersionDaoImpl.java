package com.weichuang.ecommerce.appVersion.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.appVersion.constants.NameSpaceConstant;
import com.weichuang.ecommerce.appVersion.entity.AppVersionEntity;
import com.weichuang.ecommerce.appVersion.repository.IAppVersionDao;

/**
 * <p>ClassName: AppVersionDaoImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:版本更新接口实现 </p>
 * <p>author wanggongliang</p>
 * <p>2017年11月22日 下午2:09:35</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class AppVersionDaoImpl implements IAppVersionDao {
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate;

    /**
     * @Title:getAppVersion  
     * @Description:根据客户端查询版本号
     * @param platForm 1安卓2ios
     * @param type 1老板助手2店员助手
     * @return
     */
    @Override
    public AppVersionEntity getAppVersion(int platForm, int type) {
        Map<String, Object> param = new HashMap<>();
        param.put("platForm", platForm);
        param.put("type", type);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.APP_VERSION + ".getAppVersion", param);
    }

    /**
     * @Title:getlist  
     * @Description:后台版本管理列表数据
     * @return
     */
    @Override
    public List<AppVersionEntity> getList(int platForm) {
        Map<String, Object> param = new HashMap<>();
        param.put("platForm", platForm);
        return sqlSessionTemplate.selectList(NameSpaceConstant.APP_VERSION + ".getList", param);
    }

    /**
     * @Title:addAppVersion  
     * @Description: 增加版本更新
     * @param version
     * @return
     */
    @Override
    public int addAppVersion(AppVersionEntity version) {
        return sqlSessionTemplate.insert(NameSpaceConstant.APP_VERSION + ".addAppVersion", version);
    }

    /**
     * @Title:updateAppVersion  
     * @Description:修改版本更新
     * @param version
     * @return
     */
    @Override
    public int updateAppVersion(AppVersionEntity version) {
        return sqlSessionTemplate.update(NameSpaceConstant.APP_VERSION + ".updateAppVersion", version);
    }

    /**
     * @Title:getAppVersionById  
     * @Description:根据主键查询版本信息
     * @param id
     * @return
     */
    @Override
    public AppVersionEntity getAppVersionById(int id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.APP_VERSION + ".getAppVersionById", param);
    }

}
