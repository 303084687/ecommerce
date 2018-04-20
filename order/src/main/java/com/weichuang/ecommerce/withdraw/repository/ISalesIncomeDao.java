package com.weichuang.ecommerce.withdraw.repository;

import java.util.List;

import com.weichuang.ecommerce.withdraw.entity.SalesDailyIncomeDetailEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesDailyIncomeSumEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesIncomeEntity;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.withdraw.entity.response.BonusTemplate;

/**
 * <p>ClassName: ISalesIncomeDao.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:业务人员预计收入接口类 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月22日 下午3:28:21</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public interface ISalesIncomeDao {
    /**
     * 增加销售人员提成信息
     *
     * @param salesIncomeEntity
     * @return
     */
    public int addSalesIncome(SalesIncomeEntity salesIncomeEntity);

    /**
     * @param saleId 销售人员id
     * @return
     * @Title:selectBySaleId
     * @Description:根据销售人员主键查询分红(一天7天以及可提现)
     */
    public List<BonusTemplate> selectBySaleId(int saleId);

    /**
     * @param saleId
     * @param status     1待提现2已提现
     * @param systemTime
     * @return
     * @Title:updateCashStatus
     * @Description:根据销售人员id和系统时间批量更新已提现状态
     */
    public int updateCashStatus(int saleId, int status, String systemTime, String oldTime);

    
    
    /**
     * @param saleId
     * @param status     1待提现2已提现
     * @param systemTime
     * @return
     * @Title:updateCashStatus
     * @Description:根据销售人员id和系统时间批量更新已提现状态
     */
    public int updateCashStatusSuccess(int saleId, int status, String systemTime, String oldTime);
    /**
     * @param saleId
     * @param systemTime
     * @return
     * @Title:cashMoney
     * @Description:根据销售人员id和系统时间查询可提现的金额,用于和前台传过来的金额进行二次比较
     */
    public BonusTemplate cashMoney(int saleId, String systemTime);


    /**
     * @param saleId
     * @param startTime
     * @param endTime
     * @return
     * @Title:getDailyIncomeSumBySalesId
     * @Description:根据业务人员的id查询某一时间段内每天的预计的可提成的汇总
     */
    public List<SalesDailyIncomeSumEntity> getDailyIncomeSumBySalesId(int saleId, String startTime, String endTime);

    /**
     * @param saleId
     * @param startTime
     * @param endTime
     * @return
     * @Title:SalesDailyIncomeDetailEntity
     * @Description:根据业务人员的id查询某一时间段内每天的预计的可提成的详细信息
     */
    public List<SalesDailyIncomeDetailEntity> getDailyIncomeDetailBySalesId(int saleId, String startTime, String endTime);

    /**
     * @param orderNo 订单编号
     * @return int
     * @Title:deleteSalesIncomeByOrderNo
     * @Description:订单退款后，逻辑删除业务人员收入
     */
    public int deleteSalesIncomeByOrderNo(String orderNo);
    /**
     * @param saleId 销售人员id
     * @return
     * @Title:selectBySaleId
     * @Description:根据销售人员主键查询分红(一天7天以及可提现)
     */
    public List<BonusTemplate> selectSumBySaleId(int saleId);
}
