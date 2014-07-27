package com.stj.services.impl;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.stj.model.MessageBoard;

@Component
public class DefaultMailService {

	@Value("${mail.transport.protocol}")
	String mailTransportProtocol;

	@Value("${mail.host}")
	String mailHost;

	@Value("${mail.smtp.port}")
	String mailSmtpPort;

	@Value("${mail.user}")
	String mailUser;

	@Value("${mail.password}")
	String mailPassword;

	@Value("${mail.fromAddress}")
	String mailFromAddress;

	@Value("${stjames.implementation.environment}")
	String environment;

	public void sendExceptionMail(RuntimeException e) {
		try {
			Session mailSession = getMailSession();
			Transport transport = mailSession.getTransport();
			MimeMessage message = new MimeMessage(mailSession);
			message.setSubject((new StringBuilder("Exception occurred in ")).append(environment).append(" environment...").toString());
			message.setContent(e.getMessage(), "text/plain");
			message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress("webmaster@stjamesgolfleague.com"));
			message.setFrom(new InternetAddress(mailFromAddress));
			transport.connect();
			Transport.send(message);
			transport.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void sendMessageBoard(MessageBoard messageBoard) {
		try {
			Session mailSession = getMailSession();
			Transport transport = mailSession.getTransport();
			MimeMessage message = new MimeMessage(mailSession);
			message.setSubject("New Message Board entry");
			message.setContent(messageBoard.formatForEmail(), "text/plain");
			message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress("webmaster@stjamesgolfleague.com"));
			message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress("tjcomputing@cox.net"));
			message.setFrom(new InternetAddress(mailFromAddress));
			transport.connect();
			Transport.send(message);
			transport.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private Session getMailSession() {
		Properties mailConfiguration = new Properties();
		mailConfiguration.setProperty("mail.transport.protocol", mailTransportProtocol);
		mailConfiguration.setProperty("mail.host", mailHost);
		mailConfiguration.setProperty("mail.smtp.port", mailSmtpPort);
		mailConfiguration.setProperty("mail.user", mailUser);
		mailConfiguration.setProperty("mail.password", mailPassword);
		Session mailSession = Session.getDefaultInstance(mailConfiguration, null);
		return mailSession;
	}

	public String getMailTransportProtocol() {
		return mailTransportProtocol;
	}

	public void setMailTransportProtocol(String mailTransportProtocol) {
		this.mailTransportProtocol = mailTransportProtocol;
	}

	public String getMailHost() {
		return mailHost;
	}

	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	public String getMailSmtpPort() {
		return mailSmtpPort;
	}

	public void setMailSmtpPort(String mailSmtpPort) {
		this.mailSmtpPort = mailSmtpPort;
	}

	public String getMailUser() {
		return mailUser;
	}

	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public String getMailFromAddress() {
		return mailFromAddress;
	}

	public void setMailFromAddress(String mailFromAddress) {
		this.mailFromAddress = mailFromAddress;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

}
