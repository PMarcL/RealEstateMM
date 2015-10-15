package org.RealEstateMM.domain.emailsender;

import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.RealEstateMM.domain.emailsender.email.EmailMessage;

import com.sun.mail.smtp.SMTPTransport;

public class GmailSender implements EmailSender {

	private static final String USERNAME = "housematch.teamb2";
	private static final String PASSWORD = "awesometeam2";
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	public void sendEmail(EmailMessage email) throws CouldNotSendMailException {
		Session session = createMailSession();

		try {
			MimeMessage message = setMessageContent(email, session);
			sendMessage(session, message);
		} catch (MessagingException | NoSuchProviderException e) {
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

	private MimeMessage setMessageContent(EmailMessage email, Session session) throws MessagingException, AddressException {
		MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress(USERNAME + "@gmail.com"));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.recipientEmailAddress, false));
		message.setSubject(email.subject);
		message.setText(email.body, "utf-8");
		message.setSentDate(new Date());

		return message;
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
