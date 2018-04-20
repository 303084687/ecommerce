package com.weichuang.ecommerce.website.service;

import com.weichuang.commons.PageListResult;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.website.entity.ContentEntity;


/**
 * author :han 2018-0118 15:45
 */
public interface IContentService {

    /**
     * 增加
     * @param request
     * @return
     * @throws ServiceException
     */
    public int addContent(ContentEntity request) throws ServiceException;

    /**
     * 更新
     * @param request
     * @return
     * @throws ServiceException
     */
    public int updateContent(ContentEntity request) throws ServiceException;

    /**
     * 列表
     * @param title
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     * @throws ServiceException
     */
    public PageListResult<ContentEntity> contentList(String title, int status, int pageNum, int pageSize) throws ServiceException;

    /**
     * 根据主键查询详情
     * @param id
     * @return
     * @throws ServiceException
     */
    public ContentEntity selectById(int id) throws ServiceException;

    /**
     * 置顶功能
     * @param id
     * @return
     * @throws ServiceException
     */
    public int updateHotById(int id) throws ServiceException;

}
