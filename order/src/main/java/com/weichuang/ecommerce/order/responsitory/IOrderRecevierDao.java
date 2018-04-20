package com.weichuang.ecommerce.order.responsitory;


import com.weichuang.ecommerce.order.entity.OrderDetailEntity;
import com.weichuang.ecommerce.order.entity.OrderRecevierEntity;

import java.util.List;

/**
 * <p>ClassName: IOrderRecevierDao.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单收货人信息 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年09月13日 下午15:51:00</p>
 */
public interface IOrderRecevierDao {
    /**
     * <p>Description: 增加订单收货人</p>
     * <p>param OrderRecevierEntity 订单收货实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/15 09:00 </p>
     * <p>return int</p>
     */
    public int addOrderRecevier(OrderRecevierEntity entity);

    /**
     * <p>Description: 根据订单Id查询收货人详情</p>
     * <p>param orderId 订单Id </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/15 11:00 </p>
     * <p>return List<OrderDetailEntity></p>
     */
    public OrderRecevierEntity getOrderReceviersByOrderId(int orderId);

    /**
     * <p>Description: 根据订单编号查询收货人详情</p>
     * <p>param orderNo 订单编号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/26 10:00 </p>
     * <p>return List<OrderDetailEntity></p>
     */
    public OrderRecevierEntity getOrderReceviersByOrderNo(String orderNo);

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
    public int updateOrderRecevierByOrderNo(String orderNo,
                                            String receiverName,
                                            int provinceId,
                                            int cityId,
                                            int countyId,
                                            String address,
                                            String mobile);
}
