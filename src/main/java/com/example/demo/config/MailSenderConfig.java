package com.example.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Properties;

@Configuration
public class MailSenderConfig {

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl javaMailSender = setJavaMailSender();
        setProperties(javaMailSender);
        return javaMailSender;
    }

    private JavaMailSenderImpl setJavaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setProtocol("smtp");
        javaMailSender.setHost("mail.nz-smartlife.hu");
        javaMailSender.setPort(465);
        javaMailSender.setUsername("jolevegot@nz-smartlife.hu");
        javaMailSender.setPassword("**********");
        return javaMailSender;
    }

    private void setProperties(JavaMailSenderImpl javaMailSender) {
        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.enable", "true");
    }
}
