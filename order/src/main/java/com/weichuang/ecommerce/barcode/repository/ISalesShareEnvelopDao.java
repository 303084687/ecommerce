package com.weichuang.ecommerce.barcode.repository;

import com.weichuang.ecommerce.barcode.entity.SalesIncomeDetailEntity;
import com.weichuang.ecommerce.barcode.entity.SalesShareEnvelope;
import com.weichuang.ecommerce.barcode.entity.SalesShareEnvelopeWithdraw;
import com.weichuang.ecommerce.barcode.entity.SalesShareEnvelopeWithdrawDetail;

import java.math.BigDecimal;
import java.util.List;

/**
* <p>ClassName:ISalesShareEnvelopDao</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2018/1/2 10:18</p>
**/
public interface ISalesShareEnvelopDao {
    public int selectCountByUserId(int userid);
    public int saveShareEnvelop(SalesShareEnvelope entity);
    public int selectCountByUserIdCodekey(int userId,String codekey);
    public BigDecimal selectAllIncome(int userId);
    public void insertDetailList(List<SalesShareEnvelopeWithdrawDetail> list);
    public List<SalesShareEnvelope> selectAllIncomeList(int userId);
    public void updateAllIncomeState(List<Integer> ids);
    public void saveSalesEnvelopWithdraw(SalesShareEnvelopeWithdraw withdraw);
    public List<SalesIncomeDetailEntity> selectIncomeDetail(int saleId);
}
