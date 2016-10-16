package pl.put.poznan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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
import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author piotrowy
 */
@AllArgsConstructor
public class Mail {

	/**
	 * application email address.
	 */
	private static final String SEND_FROM = AppController.config.email();

	/**
	 * application name.
	 */
	private static final String USERNAME = AppController.config.emailUsername();

	/**
	 * password to email.
	 */
	private static final String PASSWORD = AppController.config.emailPassword();

	/**
	 * host of email address.
	 */
	private static final String HOST = AppController.config.emailHost();

	/**
	 * email address at which charts are sent.
	 */
	@Getter @Setter @NonNull
	private String sendTo;

	/**
	 * file with svg charts.
	 */
	@Getter @Setter @NonNull
	private String file;

	public void sendMail(final String subject, final String body) {
		sendMailWithAttachment(subject, body, false);
	}

	public void sendMailWithAttachment(String subject, String body,
			Boolean attachFile) {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", HOST);
		properties.put("mail.smtp.port", AppController.config.emailPort());
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
}
