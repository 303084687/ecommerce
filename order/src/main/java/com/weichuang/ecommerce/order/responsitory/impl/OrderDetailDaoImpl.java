package com.weichuang.ecommerce.order.responsitory.impl;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.order.constants.NameSpaceConstant;
import com.weichuang.ecommerce.order.entity.OrderDetailEntity;
import com.weichuang.ecommerce.order.responsitory.IOrderDetailDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * <p>ClassName: OrderDetailDaoImpl.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单详情数据访问 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年9月13日 下午3:06:28</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class OrderDetailDaoImpl implements IOrderDetailDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * <p>Description: 添加订单详情</p>
     * <p>param entity 订单详情实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/13 15:00 </p>
     * <p>return </p>
     */
    @Override
    public int addOrderDetail(OrderDetailEntity entity) {
        return sqlSessionTemplate.insert(NameSpaceConstant.ORDER_DETAIL + ".addOrderDetail", entity);
    }

    /**
     * <p>Description: 根据订单Id查询订单详情</p>
     * <p>param orderId 订单Id </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/15 11:00 </p>
     * <p>return List<OrderDetailEntity></p>
     */
    @Override
    public List<OrderDetailEntity> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetailEntity> result = sqlSessionTemplate.selectList(NameSpaceConstant.ORDER_DETAIL + ".getOrderDetailsByOrderId", orderId);
        return result;
    }

    /**
     * <p>Description: 根据订单编号查询订单详情</p>
     * <p>param orderNo 订单编号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/25 18:00 </p>
     * <p>return List<OrderDetailEntity></p>
     */
    public List<OrderDetailEntity> getOrderDetailsByOrderNo(String orderNo){
        List<OrderDetailEntity> result = sqlSessionTemplate.selectList(NameSpaceConstant.ORDER_DETAIL + ".getOrderDetailsByOrderNo", orderNo);
        return result;
    }
}
