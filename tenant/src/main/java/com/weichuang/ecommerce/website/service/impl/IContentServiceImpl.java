package com.weichuang.ecommerce.website.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.PageListResult;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.website.entity.ContentEntity;
import com.weichuang.ecommerce.website.repository.IContentDao;
import com.weichuang.ecommerce.website.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
/**
 * author :han 2018-01-18 15:45
 */
@Service
public class IContentServiceImpl implements IContentService {

    @Autowired
    private IContentDao contentDao;

    @Override
    @Transactional
    public int addContent(ContentEntity entity) throws ServiceException {
        entity.setCreateTime(new Date());
        return contentDao.addContent(entity);
    }

    @Override
    public int updateContent(ContentEntity entity) throws ServiceException {
     /*   ContentEntity entity = new ContentEntity();
        BeanUtils.copyProperties(request, entity);*/
        return contentDao.updateContent(entity);
    }

    @Override
    public PageListResult<ContentEntity> contentList(String title, int status, int pageNum, int pageSize) throws ServiceException {
        PageHelper.startPage(pageNum, pageSize);
        List<ContentEntity> list = contentDao.contentList(title, status);
        PageInfo<ContentEntity> pageInfo = new PageInfo<ContentEntity>(list);
        return new PageListResult<ContentEntity>(list,pageInfo.getTotal(),pageInfo.getPages(),pageNum,pageSize);
    }

    @Override
    public ContentEntity selectById(int id) throws ServiceException {
        return contentDao.selectById(id);
    }

    @Override
    public int updateHotById(int id) throws ServiceException {
        int maxSort = contentDao.maxSort();
        return contentDao.updateHotById(id,maxSort);
    }

}
