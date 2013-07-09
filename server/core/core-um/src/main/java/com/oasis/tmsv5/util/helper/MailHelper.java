package com.oasis.tmsv5.util.helper;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.oasis.tmsv5.service.helper.ResourceDispHelper;

/**
 * <pre>
 * 邮件发送工具类
 *
 * @date 2013-6-27 上午11:20:33
 */
@Component
public class MailHelper {

    @Autowired(required = false)
    @Qualifier("mailSender")
    private MailSender mailSender;

    @Autowired(required = false)
    @Qualifier("mailSender")
    private JavaMailSender javaMailSender;

    @Autowired(required = false)
    @Qualifier("phoneTextSender")
    private MailSender phoneTextSender;

    private static final Log logger = LogFactory.getLog(MailHelper.class);

    private Resource resource;

    public static Properties mailConfig = new Properties();

    public static final String MAIL_SWITCH_ON = "mail.switch.on";

    public static final String MAIL_HOST = "mail.host";

    public static final String MAIL_TO = "mail.to";

    public static final String MAIL_FROM = "mail.from";

    public static final String MAIL_SUBJECT = "mail.subject";

    public static final String SUPPORT_MAIL_TO = "mail.support.to";

    public static final String SUPPORT_MAIL_FROM = "mail.support.from";

    public static final String SUPPORT_MAIL_SUBJECT = "mail.support.subject";

    public static final String MESSAGE_FROM = "message.from";

    public static final String MESSAGE_HOST = "messsage.host";

    public static final String COMMENT_MAIL_TO = "mail.comment.to";

    public static final String COMMENT_MAIL_FROM = "mail.comment.from";

    public static final String COMMENT_MAIL_SUBJECT = "mail.comment.subject";

    public static final String JOB_MAIL_SUBJECT = "mail.job.subject";

    public static final String MAIL_SPLITOR = "@";

    public void setResource(Resource resource) {
        this.resource = resource;
        getMailConfig();
    }

    public void send(SimpleMailMessage msg) {
        try {
            mailSender.send(msg);
        } catch (MailException me) {
            logger.error("Mail sending failed");
            logger.error(me);
        }
    }

    /**
     * <p>
     * 发送邮件
     * 
     * @param tos 收件人
     * @param bccs 秘密抄送
     * @param ccs 抄送
     * @param message 内容
     * @param subject 主题
     * @param multipart
     * @throws javax.mail.MessagingException
     */
    public void send(String[] tos, String[] bccs, String[] ccs, String message, String subject)
            throws javax.mail.MessagingException {
        // 使用JavaMail的MimeMessage，支付更加复杂的邮件格式和内容
        MimeMessage msg = javaMailSender.createMimeMessage();
        Properties properties = getMailConfig();
        if (!Boolean.parseBoolean(properties.getProperty(MAIL_SWITCH_ON))) {
            logger.trace("Mail sending disabled.");
            return;
        }
        if (tos == null && ccs == null && bccs == null) {
            logger.trace("Mail sending disabled.");
            return;
        }
        try {
            // 创建MimeMessageHelper对象，处理MimeMessage的辅助类
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8"); // 设置UTF-8或gbk编码，否则邮件会有乱码
            // 使用辅助类MimeMessage设定参数
            helper.setFrom(properties.getProperty(MAIL_FROM));
            if (tos != null) {
                helper.setTo(tos);
            }
            if (ccs != null) {
                helper.setCc(ccs);
            }
            if (bccs != null) {
                helper.setBcc(bccs);
            }
            message = ResourceDispHelper.getInstance().getValue("BODY_BEGIN") + message
                    + ResourceDispHelper.getInstance().getValue("BODY_END");
            helper.setText(message, true);
            helper.setSubject(subject);
            helper.setSentDate(new Date(System.currentTimeMillis()));
            // 发送邮件
            javaMailSender.send(msg);
            logger.trace("Mail sending successful.");
        } catch (MessagingException e) {
            logger.error("Mail sending failed");
            logger.error(e);
        }
    }

    public void sendPoneText(SimpleMailMessage msg) {
        try {
            phoneTextSender.send(msg);
        } catch (MailException me) {
            logger.error("Mail sending failed");
            logger.error(me);
        }
    }

    private Properties getMailConfig() {
        if (!mailConfig.isEmpty()) {
            return mailConfig;
        }
        try {
            mailConfig.load(resource.getInputStream());
        } catch (IOException e) {
            logger.error("can't find mail configuration property");
            // logger.error(LogMessageUtil.printStack(e));
        }
        return mailConfig;
    }
    
    public void test(){
        System.out.println("test OK.");
    }

}