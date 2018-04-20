package com.weichuang.ecommerce.barcode.repository.impl;
/**
* <p>ClassName:SalesShareEnvelopDaoImpl</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2018/1/2 10:19</p>
**/

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.barcode.constants.NameSpaceConstant;
import com.weichuang.ecommerce.barcode.entity.SalesIncomeDetailEntity;
import com.weichuang.ecommerce.barcode.entity.SalesShareEnvelope;
import com.weichuang.ecommerce.barcode.entity.SalesShareEnvelopeWithdraw;
import com.weichuang.ecommerce.barcode.entity.SalesShareEnvelopeWithdrawDetail;
import com.weichuang.ecommerce.barcode.repository.ISalesShareEnvelopDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AutoConfigureAfter(MyBatisConfig.class)

public class SalesShareEnvelopDaoImpl implements ISalesShareEnvelopDao{
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    /**
     *<p>Description:通过用户id查询的红包次数</p>
     *<p>param userid:用户id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/2 10:20</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public int selectCountByUserId(int userid) {

        return sqlSessionTemplate.selectOne(NameSpaceConstant.SALES_SHARE_ENVELP+".selectCountByUserId",userid);
    }
    public int saveShareEnvelop(SalesShareEnvelope entity){
        return sqlSessionTemplate.insert(NameSpaceConstant.SALES_SHARE_ENVELP+".insertSelective",entity);

    }
    /**
     *<p>Description:查询分享红包个数</p>
     *<p>param userId:销售id</p>
     *<p>param codekey:二维码键值</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/4 10:42</p>
     *<p>return:</p>
     */
    public int selectCountByUserIdCodekey(int userId,String codekey){
        Map param=new HashMap();
        param.put("userid",userId);
        param.put("codekey",codekey);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.SALES_SHARE_ENVELP+".selectCountByUserIdCodekey",param);
    }
    /**
     *<p>Description:查询未提现得分享红包总额</p>
     *<p>param userId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/4 10:31</p>
     *<p>return:</p>
     */
    public BigDecimal selectAllIncome(int userId){
        return sqlSessionTemplate.selectOne(NameSpaceConstant.SALES_SHARE_ENVELP+".selectAllIncome",userId);
    }
    /**
     *<p>Description:分享红包提现详细</p>
     *<p>param list:插入的集合</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/4 11:03</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public void insertDetailList(List<SalesShareEnvelopeWithdrawDetail> detaillist){
         sqlSessionTemplate.insert(NameSpaceConstant.SALES_SHARE_ENVELP_WITHDRAW_DETAIL+".insertDetailList",detaillist);
    }
    /**
     *<p>Description:分享红包未提取的记录集合</p>
     *<p>param userId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/4 11:07</p>
     *<p>return:</p>
     */
    public List<SalesShareEnvelope> selectAllIncomeList(int userId){
        return sqlSessionTemplate.selectList(NameSpaceConstant.SALES_SHARE_ENVELP+".selectAllIncomList",userId);
    }
    /**
     *<p>Description:批量修改分享红包领取状态</p>
     *<p>param ids:id的集合</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/4 11:15</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public void updateAllIncomeState(List<Integer> ids){
         sqlSessionTemplate.update(NameSpaceConstant.SALES_SHARE_ENVELP+".updateAllIncomeState",ids);
    }
    /**
     *<p>Description:保存分享红包提现表</p>
     *<p>param withdraw:实体</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/4 11:56</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public void saveSalesEnvelopWithdraw(SalesShareEnvelopeWithdraw withdraw){
        sqlSessionTemplate.insert(NameSpaceConstant.SALES_SHARE_ENVELP_WITHDRAW+".insertSelective",withdraw);

    }
    /**
     *<p>Description:分享红包提现列表</p>
     *<p>param saleId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/4 16:53</p>
     *<p>return:</p>
     */
    public List<SalesIncomeDetailEntity> selectIncomeDetail(int saleId){
        return sqlSessionTemplate.selectList(NameSpaceConstant.SALES_SHARE_ENVELP+".selectIncomeDetail",saleId);
    }
}
