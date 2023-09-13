package com.tiktok;

import com.tiktok.common.common.utils.EmailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TiktokDomApplication.class)
public class MailTests {
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private TemplateEngine templateEngine;
    @Test
    public void testTextMail() {
        emailUtil.sendEmail("13533825467@163.com", "952060132@qq.com", "952060132@qq.com", "ltgvzpiflpombbhc", "hihi", "hello email");
    }
    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username","sunday");
        String content = templateEngine.process("/mail/demo",context);
        System.out.println(content);
        emailUtil.sendEmail("13533825467@163.com", "952060132@qq.com", "952060132@qq.com", "ltgvzpiflpombbhc", "hihi", "hello email");
    }


}
