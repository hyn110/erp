package com.fmi110.erp;

import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送邮件的工具类,使用邮件内容使用了 freemark 模版
 */
@Component
public class MailUtil {
    @Autowired
    private JavaMailSender       javaMailSender;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    /**
     * 发送邮件的帐号
     */
//    @Value(value = "${mail.username}")
    private String               mailHost = "1009225458@qq.com";

    /**
     * 从freemarker模版中构建邮件内容
     * @param to      收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendMailUseTemplate(String to,String subject,String content) throws Exception {
        String              text = "";
        Map<String, String> map  = new HashMap<String, String>(1);
        map.put("content", content);

        // 根据模板内容，动态把map中的数据填充进去，生成HTML
        Template template = freeMarkerConfigurer.getConfiguration()
                                                .getTemplate("mail.ftl");
        // map中的key，对应模板中的${key}表达式
        text = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

        sendMail(to, subject, text);
    }

    /**
     * 发送邮件,手动指定主题和内容
     *
     * @param to      收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendMail(String to, String subject, String content) throws MessagingException {
        MimeMessage       message       = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
        messageHelper.setFrom(mailHost);  // 设置发件人
        messageHelper.setSubject(subject); // 设置邮件主题

        messageHelper.setTo(to);  // 设置收件人
        messageHelper.setText(content, true); // 设置文本内容
        javaMailSender.send(message);
    }

    /**
     * 带附件发送邮件
     * @param to        收件人
     * @param subject   主题
     * @param content   正文
     * @param filePaths 文件的路径
     * @throws MessagingException
     */
    public void sendMail(String to, String subject, String content, String... filePaths) throws MessagingException {
        //获取JavaMailSender bean
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        //设置utf-8或GBK编码，否则邮件会有乱码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
        try {
            messageHelper.setFrom(mailHost);  // 设置发件人
            messageHelper.setSubject(subject); // 设置邮件主题

            messageHelper.setTo(to);  // 设置收件人
            messageHelper.setText(content, true);

            //附件内容
//            messageHelper.addInline("b", new File("E:/logo.png"));
            for (String path : filePaths) {
                File file = new File(path);
                // 这里的方法调用和插入图片是不同的，使用MimeUtility.encodeWord()来解决附件名称的中文问题
                messageHelper.addAttachment(MimeUtility.encodeWord(file.getName()), file);
            }

            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
