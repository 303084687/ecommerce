package com.weichuang.ecommerce.order.responsitory.impl;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.order.constants.NameSpaceConstant;
import com.weichuang.ecommerce.order.entity.OrderRefundEntity;
import com.weichuang.ecommerce.order.responsitory.IOrderRefundDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

/**
 * <p>ClassName: OrderRefundDaoImpl.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单退款 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年11月10日</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class OrderRefundDaoImpl implements IOrderRefundDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * <p>Description: 增加订单发货详情</p>
     * <p>param OrderSentEntity 订单发货实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/3 09:00 </p>
     * <p>return int</p>
     */
    @Override
    public int addOrderRefund(OrderRefundEntity entity){
        return sqlSessionTemplate.insert(NameSpaceConstant.ORDER_REFUND + ".addOrderRefund", entity);
    }

    /**
     * <p>Description: 根据订单编号查询订单退款信息</p>
     * <p>param orderNo 订单编号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/20 18:00 </p>
     * <p>return OrderRefundEntity</p>
     */
    @Override
    public OrderRefundEntity getOrderRefundByOrderNo(String orderNo){
        return sqlSessionTemplate.selectOne(NameSpaceConstant.ORDER_REFUND + ".getOrderRefundByOrderNo", orderNo);
    }

    /**
     * <p>Description: 根据订单编号更新退款的状态为成功</p>
     * <p>param orderNo 订单编号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/29 09:00 </p>
     * <p>return int</p>
     */
    @Override
    public int updateOrderRefundStatusToClosedByOrderNo(String orderNo){
        return sqlSessionTemplate.update(NameSpaceConstant.ORDER_REFUND + ".updateOrderRefundStatusToClosedByOrderNo", orderNo);
    }
}
