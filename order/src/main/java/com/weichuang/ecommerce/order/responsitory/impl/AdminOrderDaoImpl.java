package com.weichuang.ecommerce.order.responsitory.impl;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.order.constants.NameSpaceConstant;
import com.weichuang.ecommerce.order.entity.AdminOrderDetailEntity;
import com.weichuang.ecommerce.order.entity.AdminOrderEntity;
import com.weichuang.ecommerce.order.entity.OrderEntity;
import com.weichuang.ecommerce.order.responsitory.IAdminOrderDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>ClassName: IAdminOrderDao.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:后台管理订单数据访层 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年11月2日 下午2:51:13</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class AdminOrderDaoImpl implements IAdminOrderDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * <p>Description: 根据订单编号查询订单</p>
     * <p>param orderNo 订单编号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/15 09:00 </p>
     * <p>return OrderEntity</p>
     */
    @Override
    public AdminOrderDetailEntity getOrderByNo(String orderNo) {
        return sqlSessionTemplate.selectOne(NameSpaceConstant.ORDER + ".getAdminOrderByNo", orderNo);
    }

    /**
     * <p>Description: 根据条件查询订单管理用户订单列表,已发货的订单列表的查询(配送中的订单)</p>
     * <p>param createStartTime 订单创建开始时间 </p>
     * <p>param createEndTime 订单创建结束时间 </p>
     * <p>param sendStartTime 发货时间起始时间 </p>
     * <p>param sendEndTime 发货时间终止时间 </p>
     * <p>param orderNo 订单编号 </p>
     * <p>param mobile 购买人手机号 </p>
     * <p>param pageNum 开始页面码 </p>
     * <p>param pageSize 每页显示数据的数量 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/2 16:00 </p>
     * <p>return UserOrderListResponse</p>
     */
    @Override
    public List<AdminOrderEntity> getSentOrderList(String createStartTime,
                                                   String createEndTime,
                                                   String sendStartTime,
                                                   String sendEndTime,
                                                   String orderNo,
                                                   String mobile,
                                                   String receiverName,
                                                   String receiverMobile) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("createStartTime", createStartTime);
        map.put("createEndTime", createEndTime);
        map.put("sendStartTime", sendStartTime);
        map.put("sendEndTime", sendEndTime);
        map.put("orderNo", orderNo);
        map.put("mobile", mobile);
        map.put("receiverName", receiverName);
        map.put("receiverMobile", receiverMobile);
        List<AdminOrderEntity> result = sqlSessionTemplate.selectList(NameSpaceConstant.ORDER + ".getSentOrderList", map);
        return result;
    }

    /**
     * <p>Description: 根据条件查询订单管理订单列表,用户未发货的订单列表的查询（配送中的订单）</p>
     * <p>param createStartTime 订单创建开始时间 </p>
     * <p>param createEndTime 订单创建结束时间 </p>
     * <p>param sendStartTime 发货时间起始时间 </p>
     * <p>param sendEndTime 发货时间终止时间 </p>
     * <p>param orderNo 订单编号 </p>
     * <p>param mobile 购买人手机号 </p>
     * <p>param pageNum 开始页面码 </p>
     * <p>param pageSize 每页显示数据的数量 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/2 16:00 </p>
     * <p>return UserOrderListResponse</p>
     */
    @Override
    public List<AdminOrderEntity> getUnsentOrderList(String createStartTime,
                                                     String createEndTime,
                                                     String sendStartTime,
                                                     String sendEndTime,
                                                     String orderNo,
                                                     String mobile,
                                                     String receiverName,
                                                     String receiverMobile) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("createStartTime", createStartTime);
        map.put("createEndTime", createEndTime);
        map.put("sendStartTime", sendStartTime);
        map.put("sendEndTime", sendEndTime);
        map.put("orderNo", orderNo);
        map.put("mobile", mobile);
        map.put("receiverName", receiverName);
        map.put("receiverMobile", receiverMobile);
        List<AdminOrderEntity> result = sqlSessionTemplate.selectList(NameSpaceConstant.ORDER + ".getUnsentOrderList", map);
        return result;
    }


    /**
     * <p>Description: 根据条件查询订单管理用户订单列表</p>
     * <p>param createStartTime 订单创建开始时间 </p>
     * <p>param createEndTime 订单创建结束时间 </p>
     * <p>param orderNo 订单编号 </p>
     * <p>param mobile 购买人手机号 </p>
     * <p>param pageNum 开始页面码 </p>
     * <p>param pageSize 每页显示数据的数量 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/2 16:00 </p>
     * <p>return UserOrderListResponse</p>
     */
    @Override
    public List<AdminOrderEntity> getAdminOrderList(String createStartTime,
                                                    String createEndTime,
                                                    String orderNo,
                                                    String mobile,
                                                    int status,
                                                    String receiverName,
                                                    String receiverMobile) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("createStartTime", createStartTime);
        map.put("createEndTime", createEndTime);
        map.put("orderNo", orderNo);
        map.put("mobile", mobile);
        map.put("status", status);
        map.put("receiverName", receiverName);
        map.put("receiverMobile", receiverMobile);
        List<AdminOrderEntity> result = sqlSessionTemplate.selectList(NameSpaceConstant.ORDER + ".getAdminOrderList", map);
        return result;
    }

    /**
     * 订单回收站
     * @param createStartTime
     * @param createEndTime
     * @param orderNo
     * @param mobile
     * @param status
     * @param receiverName
     * @param receiverMobile
     * @return
     */
    @Override
    public List<AdminOrderEntity> getAdminOrderRecycle(String createStartTime,
                                                    String createEndTime,
                                                    String orderNo,
                                                    String mobile,
                                                    int status,
                                                    String receiverName,
                                                    String receiverMobile) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("createStartTime", createStartTime);
        map.put("createEndTime", createEndTime);
        map.put("orderNo", orderNo);
        map.put("mobile", mobile);
        map.put("status", status);
        map.put("receiverName", receiverName);
        map.put("receiverMobile", receiverMobile);
        List<AdminOrderEntity> result = sqlSessionTemplate.selectList(NameSpaceConstant.ORDER + ".getAdminOrderRecycle", map);
        return result;
    }
    /**
     * <p>Description: 根据订单编号更新发货次数加1</p>
     * <p>param orderNo 订单编号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/13 </p>
     * <p>return int</p>
     */
    @Override
    public int updateOrderSentTimesByOrderNo(String orderNo) {
        int result = sqlSessionTemplate.update(NameSpaceConstant.ORDER + ".updateOrderSentTimesByOrderNo", orderNo);
        return result;
    }

    /**
     * <p>Description: 根据订单编号更新未发货记录的发货时间-</p>
     * <p>param orderNo 订单编号 </p>
     * <p>param sendTime 更新后的发货时间 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/15 </p>
     * <p>return int</p>
     */
    @Override
    public int updateOrderSendTimeByOrderNo(String orderNo, int sendTime){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNo", orderNo);
        map.put("sendTime", sendTime);
        int result = sqlSessionTemplate.update(NameSpaceConstant.ORDER + ".updateOrderSendTimeByOrderNo", map);
        return result;
    }

    /**
     * <p>Description: 根据订单编号更新订单状态</p>
     * <p>param orderNo 订单编号 </p>
     * <p>param status 订单状态 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/17 </p>
     * <p>return int</p>
     */
    public int updateOrderStatusByOrderNo(String orderNo, int status){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNo", orderNo);
        map.put("status", status);
        int result = sqlSessionTemplate.update(NameSpaceConstant.ORDER + ".updateOrderStatusByOrderNo", map);
        return result;
    }

    /**
     * 删除订单
     * @param orderNo
     * @return
     * <p>date 2018/1/2 </p>
     */
    public boolean updateOrdeStatusToDel(String orderNo){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNo", orderNo);
        int result = sqlSessionTemplate.update(NameSpaceConstant.ORDER + ".updateOrdeStatusToDel", map);
        if(result == 1){
            return true;
        }else {
            return false;
        }

    }

    public List<OrderEntity> orderList(String startTime, String endTime, int status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("status", status);
        return  sqlSessionTemplate.selectList(NameSpaceConstant.ORDER +".getOrderList",map);
    }
}
