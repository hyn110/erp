package com.fmi110.erp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by huangyunning on 2017/12/15.
 */
public class TestMail {
    public static void main(String[] args) throws Exception {
        ApplicationContext context  = new ClassPathXmlApplicationContext("applicationContext.xml");
        MailUtil           mailUtil = (MailUtil) context.getBean("mailUtil");
        mailUtil.sendMail("huangyunning@itcast.cn","测试邮件","hello,fmi110");
    }
}
