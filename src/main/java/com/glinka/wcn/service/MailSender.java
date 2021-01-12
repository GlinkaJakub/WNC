package com.glinka.wcn.service;

import com.glinka.wcn.model.dao.Token;

public interface MailSender {
    void sendMail(String to, String subject, String centent);
    void save(Token token);
    Token getToken(Long user);
}
