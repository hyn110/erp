package com.fmi110.biz;
import com.fmi110.entity.Emp;
import com.fmi110.entity.Orderdetail;


/**
 * Created by huangyunning on 2017/12/2.
 */
public interface OrderdetailBiz extends BaseBiz<Orderdetail>{
    void doInStore(Orderdetail orderdetail, Long storeUuid, Emp emp);
}
