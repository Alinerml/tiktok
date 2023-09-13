package com.tiktok.common.common.utils;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
@Component
public class EmailUtil{
    public static void sendEmail(String toEmail,String fromEmail,
                                 final String authEmail,final String authPaw,
                                 String title,String text){
        // 收件人电子邮箱
        String to = toEmail;

        // 发件人电子邮箱
        String from = fromEmail;

        // 指定发送邮件的主机为 localhost
        String host = "smtp.qq.com";  //QQ 邮件服务器

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");

        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");

        // 获取默认session对象
        Session session = Session.getInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(authEmail, authPaw); //发件人邮件用户名、授权码
            }
        });

        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: 头部头字段
            //message.setSubject("I love u");
            message.setSubject(title);

            // 设置消息体
            message.setContent(text, "text/html;charset=UTF-8");
            // 发送 HTML 消息, 可以插入html标签
            // 发送消息
            Transport.send(message);
            System.out.println("Sent email successfully...");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EmailUtil.sendEmail("13533825467@163.com","952060132@qq.com","952060132@qq.com","ltgvzpiflpombbhc","hihi","hello email");
    }
}
