package com.jwt.example.jwt.configuration;
import com.example.mailotp.service.MailOtpService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class ExtService {
    @Bean
    public MailOtpService mailOtpService(JavaMailSender javaMailSender) {
        return new MailOtpService(javaMailSender);
    }

}
