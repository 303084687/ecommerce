package com.weichuang.ecommerce.order.responsitory.impl;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.order.constants.NameSpaceConstant;
import com.weichuang.ecommerce.order.entity.OrderDetailEntity;
import com.weichuang.ecommerce.order.entity.OrderRecevierEntity;
import com.weichuang.ecommerce.order.responsitory.IOrderRecevierDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>ClassName: OrderRecevierDaoImpl.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单收货人数据访问 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年9月13日 下午15:55:00</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class OrderRecevierDaoImpl implements IOrderRecevierDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * <p>Description: 添加订单收货人</p>
     * <p>param entity 订单收货人实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/13 15:00 </p>
     * <p>return </p>
     */
    @Override
    public int addOrderRecevier(OrderRecevierEntity entity) {
        return sqlSessionTemplate.insert(NameSpaceConstant.ORDER_RECEVIER + ".addOrderRecevier", entity);
    }

    /**
     * <p>Description: 查询订单收货人</p>
     * <p>param entity 订单id </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/15 15:00 </p>
     * <p>return OrderRecevierEntity</p>
     */
    @Override
    public OrderRecevierEntity getOrderReceviersByOrderId(int orderId) {
        OrderRecevierEntity result = sqlSessionTemplate.selectOne(NameSpaceConstant.ORDER_RECEVIER + ".getOrderReceviersByOrderId", orderId);
        return result;
    }

    /**
     * <p>Description: 根据订单编号查询收货人详情</p>
     * <p>param orderNo 订单编号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/26 10:00 </p>
     * <p>return OrderRecevierEntity</p>
     */
    @Override
    public OrderRecevierEntity getOrderReceviersByOrderNo(String orderNo) {
        OrderRecevierEntity result = sqlSessionTemplate.selectOne(NameSpaceConstant.ORDER_RECEVIER + ".getOrderReceviersByOrderNo", orderNo);
        return result;
    }

    /**
     * <p>Description: 根据订单编号更新收货地址</p>
     * <p>param orderNo 订单编号 </p>
     * <p>param receiverName 收货人名称 </p>
     * <p>param provinceId 省id </p>
     * <p>param cityId 市id </p>
     * <p>param conutyId 县id </p>
     * <p>param address 详情地址 </p>
     * <p>param mobile 收货人手机号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/12/28 10:00 </p>
     * <p>return List<OrderDetailEntity></p>
     */
    @Override
    public int updateOrderRecevierByOrderNo(String orderNo,
                                            String receiverName,
                                            int provinceId,
                                            int cityId,
                                            int countyId,
                                            String address,
                                            String mobile) {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("orderNo", orderNo);
        map.put("receiverName", receiverName);
        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("countyId", countyId);
        map.put("address", address);
        map.put("mobile", mobile);

        int result = sqlSessionTemplate.update(NameSpaceConstant.ORDER_RECEVIER + ".updateOrderRecevierByOrderNo", map);
        return result;
    }
}
