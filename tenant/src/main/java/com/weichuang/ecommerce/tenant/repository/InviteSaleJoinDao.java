package com.weichuang.ecommerce.tenant.repository;

import com.weichuang.ecommerce.tenant.entity.InviteSaleJoinEntity;

/**
* <p>ClassName:InviteSaleJoinDao</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2018/1/5 9:54</p>
**/
public interface InviteSaleJoinDao {
    public int insertInviteSaleJoin(InviteSaleJoinEntity entity);
    public int getCountByCodekey(String codekey);
}
