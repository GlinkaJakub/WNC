package com.glinka.wcn.service.impl;

import com.glinka.wcn.model.dao.Token;
import com.glinka.wcn.repository.VerificationTokenRepository;
import com.glinka.wcn.service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSenderImpl implements MailSender {

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void save(Token token) {
        verificationTokenRepository.save(token);
    }

    @Override
    public Token getToken(Long user) {
        return verificationTokenRepository.findAllByUserUserId(user);
    }

    @Override
    public void sendMail(String to, String subject, String centent) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setReplyTo("wyszukiwarkaczasopism@gmail.com");
            helper.setFrom("wyszukiwarkaczasopism@gmail.com");
            helper.setSubject(subject);
            helper.setText(centent, true);
        } catch (MessagingException e){
            e.printStackTrace();
        }
        javaMailSender.send(mail);
    }
}
