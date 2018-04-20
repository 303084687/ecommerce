package com.weichuang.ecommerce.website.repository.impl;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.website.constants.NameSpaceConstant;
import com.weichuang.ecommerce.website.entity.ContentEntity;
import com.weichuang.ecommerce.website.repository.IContentDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * author :han 2018-01-18 15:45
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class ContentDaoImpl implements IContentDao{

    @Autowired
    public SqlSessionTemplate sqlSessionTemplate;

    @Override
    public int addContent(ContentEntity content) {
        return sqlSessionTemplate.insert(NameSpaceConstant.CONTENT + ".insert", content);
    }

    @Override
    public int updateContent(ContentEntity content) {
        return sqlSessionTemplate.update(NameSpaceConstant.CONTENT + ".update", content);
    }

    @Override
    public List<ContentEntity> contentList(String title, int status) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);// 状态
        map.put("title", title);// 标题
        return sqlSessionTemplate.selectList(NameSpaceConstant.CONTENT + ".contentList", map);
    }

    @Override
    public ContentEntity selectById(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);// 类型
        return sqlSessionTemplate.selectOne(NameSpaceConstant.CONTENT + ".selectById", map);
    }

    @Override
    public int updateHotById(int id,int maxSort) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);// 主键
        map.put("maxSort", maxSort);// 状态
        return sqlSessionTemplate.update(NameSpaceConstant.CONTENT + ".updateHotById", map);
    }

    @Override
    public int maxSort() {
        Map<String, Object> map = new HashMap<>();
        return sqlSessionTemplate.selectOne(NameSpaceConstant.CONTENT + ".maxSort", map);
    }

}
