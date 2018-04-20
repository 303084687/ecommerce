package com.weichuang.ecommerce.order.responsitory;


import com.weichuang.ecommerce.order.entity.OrderDetailEntity;

import java.util.List;

/**
 * <p>ClassName: IOrderDetailDao.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单详情数据访层 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年09月13日 下午3:03:08</p>
 */
public interface IOrderDetailDao {

	/**
	 * <p>Description: 添加订单详情</p>
	 * <p>param OrderDetailEntity 订单详情实体 </p>
	 * <p>author jiangkesen </p>
	 * <p>date 2017/9/15 09:00 </p>
	 * <p>return int</p>
	 */
	public int addOrderDetail(OrderDetailEntity entity);


	/**
	 * <p>Description: 根据订单Id查询订单详情</p>
	 * <p>param orderId 订单Id </p>
	 * <p>author jiangkesen </p>
	 * <p>date 2017/9/15 11:00 </p>
	 * <p>return List<OrderDetailEntity></p>
	 */
	public List<OrderDetailEntity> getOrderDetailsByOrderId(int orderId);

	/**
	 * <p>Description: 根据订单编号查询订单详情</p>
	 * <p>param orderNo 订单编号 </p>
	 * <p>author jiangkesen </p>
	 * <p>date 2017/9/25 18:00 </p>
	 * <p>return List<OrderDetailEntity></p>
	 */
	public List<OrderDetailEntity> getOrderDetailsByOrderNo(String orderNo);

}
