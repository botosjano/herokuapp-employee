package com.botosjano.springboot.employeeapp.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	private final Log log = LogFactory.getLog(this.getClass());

	@Value("${spring.mail.username}")
	private String MESSAGE_FROM;

	@Value("${my-app.url}")
	private String myUrl;

	private JavaMailSender javaMailSender;

	@Autowired
	public EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendMessage(String email, String code, String username) {
		SimpleMailMessage message = null;

		try {

			message = new SimpleMailMessage();
			message.setFrom(MESSAGE_FROM);
			message.setTo(email);
			message.setSubject("Registration successful");
			message.setText("Dear " + username + "! \n \n" + "Thank you for the registration! "
					+ "\nYou can activate your account here: " + myUrl + code);

			javaMailSender.send(message);

			log.info("Email elkuldve ide: " + email);

		} catch (Exception ex) {
			log.error("Error occured while sending to " + email + " " + ex);
		}
	}

}
