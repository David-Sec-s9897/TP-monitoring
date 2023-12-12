package com.secdavid.tpmonitoring.mail;

import com.secdavid.tpmonitoring.util.EmailUtils;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class MailService {

    private static final Logger LOGGER = Logger.getLogger(MailService.class.getName());

    @Resource(name = "java:jboss/mail/uni")
    private Session session;

    public void send(final String addresses, final String subject, final String text) {
        System.out.println("sending email to: " + addresses +" subject "+ subject);
        try {
            final Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addresses));
            message.setFrom(InternetAddress.parse("david.sec@unicorn.com")[0]);
            message.setSubject(subject);
            message.setContent(text,"text/html; charset=utf-8");

            Transport.send(message);
            LOGGER.log(Level.INFO, "Email send.");

        } catch (MessagingException e) {
            LOGGER.log(Level.SEVERE, "Cannot send mail", e);
        }
    }
}