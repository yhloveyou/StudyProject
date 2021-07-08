package cn.zz.Study.utils.customize;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class EmailUtils {
    @Resource
    private JavaMailSender sender;

    @Value("${spring.mail.from}")
    private String from;
    //    @Value("${spring.profiles.active}")
//    private String active;
    @Value("${spring.mail.to}")
    private String to;

    /**
     * 发送纯文本的简单邮件
     *
     * @param content 发送的文本内容
     */
    public void sendSimpleMail(String content) {
//        //如果是开发环境,直接返回,不发送邮件
//        if (StringUtils.equals(active,"dev")) {
//            return;
//        }
        //邮件主题
        String subject = "~~~~~~~~~未知异常~~~~~~~~~";
        SimpleMailMessage message = new SimpleMailMessage();
        //发件人邮箱
        message.setFrom(from);
        //要发送的邮箱
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            sender.send(message);
            log.info("简单邮件已经发送。");
        } catch (Exception e) {
            log.error("发送简单邮件时发生异常！", e);
        }
    }
}
