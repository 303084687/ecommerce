package com.weichuang.ecommerce.website.repository;

import com.weichuang.ecommerce.website.entity.ContentEntity;

import java.util.List;
/**
 * author :han 2018-0118 15:45
 */
public interface IContentDao {

    public int addContent(ContentEntity content);

    public int updateContent(ContentEntity content);

    public List<ContentEntity> contentList(String title, int status);

    public ContentEntity selectById(int id);

    public int updateHotById(int id,int maxSort);

    public int maxSort();
}
