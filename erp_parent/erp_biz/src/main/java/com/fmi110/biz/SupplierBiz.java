package com.fmi110.biz;
import com.fmi110.entity.Supplier;

import java.io.OutputStream;


/**
 * Created by huangyunning on 2017/12/2.
 */
public interface SupplierBiz extends BaseBiz<Supplier>{
    public void export(OutputStream os,Supplier t1);
}
