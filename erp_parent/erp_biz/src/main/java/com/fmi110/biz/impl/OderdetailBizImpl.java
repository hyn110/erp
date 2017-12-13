package com.fmi110.biz.impl;

import com.fmi110.biz.OrderdetailBiz;
import com.fmi110.dao.BaseDao;
import com.fmi110.dao.OrderdetailDao;
import com.fmi110.dao.StoredetailDao;
import com.fmi110.dao.StoreoperDao;
import com.fmi110.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Service
@Transactional
public class OderdetailBizImpl extends BaseBizImpl<Orderdetail> implements OrderdetailBiz {

    @Autowired
    private OrderdetailDao orderdetailDao;
    @Autowired
    private StoredetailDao storedetailDao;
    @Autowired
    private StoreoperDao   storeoperDao;

    @Override
    public BaseDao<Orderdetail> getBaseDao() {
        System.out.println("======给父类返回dao======");
        return this.orderdetailDao;
    }

    @Override
    public void doInStore(Orderdetail orderdetail, Long storeUuid, Emp emp) {


        /**
         * 1 更新订单详细信息的状态:
             * 1 状态 : 未入库 --> 已入库
             * 2 入库时间(结束时间)
             * 3 入库操作员(库管员)
         */

        Orderdetail detail = orderdetailDao.findById(orderdetail.getUuid());

        if(Orderdetail.STATE_IN.equals(detail.getState())){
            throw new RuntimeException("已入库,请勿重复操作");
        }

        detail.setState(Orderdetail.STATE_IN);
        detail.setEndtime(new Date());
        detail.setEnder(emp);
        // 设置仓库
        Store store = new Store();
        store.setUuid(storeUuid);
        detail.setStore(store);
        /**
         * 2 库存表的操作:
             * 1 判断是否存在库存,含两个条件 : 仓库和商品
             * 2 不存在 ,插入
             * 3 存在数量加1
         */
        Storedetail t1 = new Storedetail();
        t1.setStore(detail.getStore());
        t1.setGoods(detail.getGoods());
        long count = storedetailDao.findStoredCount(t1);
        t1.setNum(count==0?1:count+detail.getNum());
        storedetailDao.save(t1);

        /**
         * 3 更新仓库操作记录:
             * 1 操作时间
             * 2 库管员
             * 3 操作行为:入库 , 出库
         */

        Storeoper opera = new Storeoper();
        opera.setOpertime(detail.getEndtime());
        opera.setEmp(emp);
        opera.setType(Storeoper.IN);
        opera.setGoods(detail.getGoods());
        opera.setNum(detail.getNum());
        opera.setStore(detail.getStore());
        storeoperDao.save(opera);

        /**
         * 4 判断当前订单是否全部入库 , 如果全部入库则修改订单状态为入库(2)
         */
        boolean b = orderdetailDao.checkOrderIsAllIn(orderdetail.getOrdersuuid());
        if(b){
            Orders order = orderdetail.getOrder();
            order.setState(Orders.STATE_IN);
            order.setEndtime(detail.getEndtime());
            order.setEnder(emp);
        }
    }
}
