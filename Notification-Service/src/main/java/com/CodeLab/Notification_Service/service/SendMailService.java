package com.CodeLab.Notification_Service.service;

import com.CodeLab.Notification_Service.requestDTO.OTPGenerateRequestDTO;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class SendMailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    public  void sendMail(OTPGenerateRequestDTO requestDTO) throws Exception{

        Context context = new Context();
        context.setVariable("otpCode",requestDTO.getOtp());


        String htmlMail = templateEngine.process("RegistrationOTP",context);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);

        messageHelper.setSubject("Your CodeLab OTP for Verification");

        messageHelper.setTo(requestDTO.getEmail());
        messageHelper.setText(htmlMail,true);

        javaMailSender.send(message);
    }

}
