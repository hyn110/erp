package com.fmi110.erp.job;

import com.fmi110.biz.StoredetailBiz;
import com.fmi110.erp.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时发送库存预警邮件
 * Created by huangyunning on 2017/12/18.
 */
@Component
public class MailJob {
    @Autowired
    private StoredetailBiz storedetailBiz;
    @Autowired
    private MailUtil mailUtil;

    public void doJob(){
        Long count = storedetailBiz.getStoreAlertCount();
        if(count!=null&count>0){
            // 发送邮件
            try {
                System.out.println("==========发送预警邮件==========");
                mailUtil.sendMailUseTemplate("huangyunning@itcast.cn","库存预警",count+"");
            } catch (Exception e) {
                e.printStackTrace();
                // 日志记录
            }
        }
    }
}
