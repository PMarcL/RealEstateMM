package org.RealEstateMM.services.mail;

import java.security.Security;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

public class GmailSender implements MailConfirmationSender {

	private static final String USERNAME = "housematch.teamb2";
	private static final String PASSWORD = "awesometeam2";
	private static final String TITLE = "House match email confirmation";
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	private String message = "";

	@Override
	public void send(String recipientEmail, UUID confirmationCode) throws CouldNotSendMailException {
		Session session = createMailSession();
		final MimeMessage msg = new MimeMessage(session);

		try {
			createMessageBody(confirmationCode);
			setMessageContent(recipientEmail, msg);
			sendMessage(session, msg);
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

	private void setMessageContent(String recipientEmail, final MimeMessage msg)
			throws MessagingException, AddressException {
		msg.setFrom(new InternetAddress(USERNAME + "@gmail.com"));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));
		msg.setSubject(TITLE);
		msg.setText(message, "utf-8");
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

	private void createMessageBody(UUID confirmationCode) {
		// TODO
		String confirmationLink = "baseURL that we have to figure out how to get" + "/confirmation/" + confirmationCode;
		message = "Please click on the email confirmation link: " + confirmationLink;
	}

}
