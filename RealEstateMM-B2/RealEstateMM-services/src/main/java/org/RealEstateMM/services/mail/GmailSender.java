package org.RealEstateMM.services.mail;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.RealEstateMM.services.mail.email.Email;

import com.sun.mail.smtp.SMTPTransport;

public class GmailSender implements MailSender {

	private static final String USERNAME = "housematch.teamb2";
	private static final String PASSWORD = "awesometeam2";
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	public void sendEmail(Email email) throws CouldNotSendMailException {
		Session session = createMailSession();
		final MimeMessage mimeMessage = new MimeMessage(session);

		try {
			setMessageContent(email, mimeMessage);
			sendMessage(session, mimeMessage);
		} catch (MessagingException e) {
			throw new CouldNotSendMailException();
		}
	}

	private void sendMessage(Session session, final MimeMessage msg)
			throws NoSuchProviderException, MessagingException, SendFailedException {
		SMTPTransport t = (SMTPTransport) session.getTransport("smtps");

		t.connect("smtp.gmail.com", USERNAME + "@gmail.com", PASSWORD);
		t.sendMessage(msg, msg.getAllRecipients());
		t.close();
	}

	private void setMessageContent(Email email, final MimeMessage msg) throws MessagingException, AddressException {
		msg.setFrom(new InternetAddress(USERNAME + "@gmail.com"));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.recipientEmailAddress, false));
		msg.setSubject(email.subject);
		msg.setText(email.body, "utf-8");
		msg.setSentDate(new Date());
	}

	@SuppressWarnings("restriction")
	private Session createMailSession() {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		Properties props = System.getProperties();
		props.setProperty("mail.smtps.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "25");
		props.setProperty("mail.smtp.socketFactory.port", "25");
		props.setProperty("mail.smtps.auth", "true");
		props.put("mail.smtps.quitwait", "false");

		Session session = Session.getInstance(props, null);
		return session;
	}

}
