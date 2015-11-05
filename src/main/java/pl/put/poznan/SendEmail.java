package pl.put.poznan;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author piotrowy
 */
public class SendEmail {

    private final String sendTo;
    private final String sendFrom;
    private final String host;

    public SendEmail(String to, String from, String host) {
        this.sendTo = to;
        this.sendFrom = from;
        this.host = host;
    }

    public void send(String subject, String body) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sendFrom));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(sendTo));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        }catch(MessagingException ex){
            Logger.getLogger(StructureContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
