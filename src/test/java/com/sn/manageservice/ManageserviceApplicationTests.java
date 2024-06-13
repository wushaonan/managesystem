package com.sn.manageservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Transport;

@SpringBootTest
class ManageserviceApplicationTests {

    @Value("${mail.username}")
    private String emailName;

//    @Autowired
//    private JavaMailSender mailSender;

    @Test
    void contextLoads() {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人(取yml文件配置好的发件人)
        message.setFrom(emailName);
        //邮件主题
        message.setSubject("验证码发送须知");
        //邮件接收人（用户注册时传的邮件地址）
        message.setTo("545328326@qq.com");
        //邮件内容（我这里发送的内容是获取的随机数的验证码）
        message.setText("123456");
        //发送邮件
//        mailSender.send(message);

    }

}
