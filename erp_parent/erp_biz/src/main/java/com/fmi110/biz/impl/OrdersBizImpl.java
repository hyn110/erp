package com.fmi110.biz.impl;

import com.fmi110.biz.OrdersBiz;
import com.fmi110.dao.BaseDao;
import com.fmi110.dao.OrderdetailDao;
import com.fmi110.dao.OrdersDao;
import com.fmi110.entity.Emp;
import com.fmi110.entity.Orderdetail;
import com.fmi110.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Service
@Transactional
public class OrdersBizImpl extends BaseBizImpl<Orders> implements OrdersBiz {

    @Autowired
    private OrdersDao      dao;
    @Autowired
    private OrderdetailDao orderdetailDao;

    @Override
    public BaseDao<Orders> getBaseDao() {
        System.out.println("======给父类返回dao======");
        return this.dao;
    }

    @Override
    public void save(Orders order) {
        // 设置订单创建时间
        // 设置订单类型
        // 设置订单状态
        order.setCreatetime(new Date());
        order.setType(Orders.TYPE_IN);
        order.setState(Orders.STATE_CREATE);

        /**
         * todo : 1 商品总价的计算不应该依据前端的数据计算,容易被攥改!!
         * todo : 2 金额的计算问题!!!误差解决
         */

        Double totalMoney = 0d;
        for (Orderdetail o : order.getOrderdetails()) {
            totalMoney += o.getMoney();
            // 设置明细的状态
            // 设置明细的关联关系
            o.setState(Orderdetail.STATE_NOT_IN);
            o.setOrder(order);
        }
        order.setTotalmoney(totalMoney);
        dao.save(order);
    }

    /**
     * 审核
     *
     * @param uuid      审核人的id
     * @param orderUuid 订单的 id
     */
    @Override
    public void doCheck(Long uuid, Long orderUuid) {
        // 获取订单对象
        // 设置审核人
        // 设置审核日期
        // 设置订单状态
        Orders order = dao.findById(orderUuid);

        if (Orders.STATE_CHECK.equals(order.getState())) {
            throw new RuntimeException("订单已审核...");
        }
        Emp checker = new Emp(); // 审核人
        checker.setUuid(uuid);
        order.setChecker(checker);
        order.setChecktime(new Date());
        order.setState(Orders.STATE_CHECK);
    }

    @Override
    public void doStart(Long uuid, Long orderUuid) {
        // 获取订单对象
        // 设置确认人
        // 设置确认日期
        // 设置订单状态
        Orders order   = dao.findById(orderUuid);
        Emp    starter = new Emp(); // 审核人
        starter.setUuid(uuid);
        if (Orders.STATE_START.equals(order.getState())) {
            throw new RuntimeException("订单已确认...");
        }
        order.setStarter(starter);
        order.setStarttime(new Date());
        order.setState(Orders.STATE_START);
    }

    @Override
    public void delete(Orders orders) {
//        /**
//         * 手动维护外键关系,前端页面只传递了订单的主键,这里订单详情需要自己查数据库处理
//         */
//        List<Orderdetail> orderdetails = orderdetailDao.findByOrderUuid(orders.getUuid());
//        if (null != orderdetails) {
//            orders = orderdetails.get(0)
//                                 .getOrder(); // 重新赋值orders对象,解决session只能绑定一个持久态对象的问题
//
//            for (Orderdetail o : orderdetails) {
//                orderdetailDao.delete(o);
//            }
//        }
        // 对象变为持久态
        orders = dao.findById(orders.getUuid());
        super.delete(orders);
    }
}
