package pl.put.poznan;

import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author piotrowy
 */
public class Mail {

	private static final String SEND_FROM = "rnasprite@gmail.com";
	private static final String USERNAME = "RnaSprite";
	private static final String PASSWORD = "Ryb0s0my";
	private static final String HOST = "smtp.gmail.com";
	private String sendTo;
	private String file;

	public Mail(String to, String file) {
		this.setSendTo(to);
		this.setFile(file);
	}

	public void sendMail(String subject, String body) {
		sendMailWithAttachment(subject, body, false);
	}

	public void sendMailWithAttachment(String subject, String body,
			Boolean attachFile) {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", HOST);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(properties, null);
		/*
		 * new javax.mail.Authenticator() {
		 * 
		 * @Override protected PasswordAuthentication
		 * getPasswordAuthentication() { return new
		 * PasswordAuthentication(USERNAME, PASSWORD); } });
		 */

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SEND_FROM));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(sendTo));
			message.setSubject(subject);

			if (attachFile && new File(this.file).exists()) {
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText(body);

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);

				messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(this.file);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(this.file);
				multipart.addBodyPart(messageBodyPart);

				// Send the complete message parts
				message.setContent(multipart);
				Logger.getLogger(Mail.class.getName()).log(Level.INFO,
						"Email sent");
			} else {
				message.setText(body);
			}

			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", this.USERNAME, this.PASSWORD);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException ex) {
			Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/*
	 * TODO Dorobić obsługę inbox...
	 */

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
}
