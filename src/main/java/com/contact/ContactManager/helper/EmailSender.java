package com.contact.ContactManager.helper;

import java.io.File;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmailSender {

	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		

	}
	

	public  void sendEmailWithAttachment(String message, String to, String subject,String from) {
		// TODO Auto-generated method stub
		boolean f=false;
		String host="smtp.gmail.com";

//		1) get the session object.
		
		Properties properties = System.getProperties();
		System.out.println("properties ->"+ properties);
		properties.put("mail.smtp.host",host);
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.ssl.enable",true);
		properties.put("mail.smtp.auth",true);
		Session session=Session.getInstance(properties,new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("your-email","your-pass");
			}

			
		});
		//compose the message
		MimeMessage mimeMessage=new MimeMessage(session);
		try
		{
		mimeMessage.setSubject(subject);
		mimeMessage.setFrom(from);
		mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
		//send attachment with the text
		
		MimeMultipart mimeMultipart=new MimeMultipart();
		//text
		//file
		MimeBodyPart textpart=new MimeBodyPart();
		MimeBodyPart filepart=new MimeBodyPart();
		textpart.setText(message);
		String filePath="writepicpath";
		File file =new File(filePath);
		filepart.attachFile(file);
		mimeMultipart.addBodyPart(textpart);
		mimeMultipart.addBodyPart(filepart);
		mimeMessage.setContent(mimeMultipart);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			
			Transport.send(mimeMessage);
			System.out.println("Email send Successfully");
			f=true;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public boolean sendEmail(String message, String to, String subject) {
		// TODO Auto-generated method stub
	//setting the system properties
		String from="enter-your-email-here";
		boolean f=false;
		String host = "smtp.gmail.com";
		Properties properties = System.getProperties();
		System.out.println("properties ->"+ properties);
		properties.put("mail.smtp.host",host);
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.ssl.enable",true);
		properties.put("mail.smtp.auth",true);
		
// 	1) create the session to get the session object
		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				//right now this is hardcoded but will change in future
				return new PasswordAuthentication("your email ","your pass");
			}
		
		});
		
		// 2) compose the message (text,multimedia,file)
		MimeMessage mimeMessage=new MimeMessage(session);
		try {
			mimeMessage.setFrom(from);
		 
		mimeMessage.setText(message);
		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));;
		mimeMessage.setSubject(subject);
		}
	
		
		
		
		catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			//3) sending the message
			Transport.send(mimeMessage);
			System.out.println("Email send successfully");
			f=true;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;
	}

}
