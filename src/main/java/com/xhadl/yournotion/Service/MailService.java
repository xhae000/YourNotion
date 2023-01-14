package com.xhadl.yournotion.Service;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

public interface MailService {
    public MimeMessage creatMessage(String to) throws MessagingException, UnsupportedEncodingException;
    public String sendSimpleMessage(String to) throws Exception ;

}
