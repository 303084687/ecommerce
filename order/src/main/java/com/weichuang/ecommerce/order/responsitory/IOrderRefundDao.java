package com.weichuang.ecommerce.order.responsitory;

import com.weichuang.ecommerce.order.entity.OrderRefundEntity;

import java.util.List;

/**
 * <p>ClassName: IOrderRefundDao.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单退款 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年11月10日</p>
 */
public interface IOrderRefundDao {
	/**
	 * <p>Description: 增加订单发货详情</p>
	 * <p>param OrderSentEntity 订单发货实体 </p>
	 * <p>author jiangkesen </p>
	 * <p>date 2017/11/3 09:00 </p>
	 * <p>return int</p>
	 */
	public int addOrderRefund(OrderRefundEntity entity);

	/**
	 * <p>Description: 根据订单编号查询订单退款信息</p>
	 * <p>param orderNo 订单编号 </p>
	 * <p>author jiangkesen </p>
	 * <p>date 2017/11/20 18:00 </p>
	 * <p>return OrderRefundEntity</p>
	 */
	public OrderRefundEntity getOrderRefundByOrderNo(String orderNo);

	/**
	 * <p>Description: 根据订单编号更新退款的状态为成功</p>
	 * <p>param orderNo 订单编号 </p>
	 * <p>author jiangkesen </p>
	 * <p>date 2017/11/29 09:00 </p>
	 * <p>return int</p>
	 */
	public int updateOrderRefundStatusToClosedByOrderNo(String orderNo);
}
