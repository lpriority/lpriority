package com.lp.mail.service;

import java.util.Date;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailServiceImpl {

	private static final int NUM_CHARS = 10;
	private static String chars = "abcdefghijklmonpqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static Random r = new Random();

	private static MailSender mailSender;

	@SuppressWarnings("static-access")
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public static void sendMails(String to, String from, String password) {
		// creating message
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject("Your password has been modified successfully.");
		message.setText("Your New Password is:\t " + password+"\n\n--\nThank you\nLearning Priority");
		message.setSentDate(new Date());
		// sending message
		mailSender.send(message);
	}

	public static String getUniqueID() {
		char[] buf = new char[NUM_CHARS];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = chars.charAt(r.nextInt(chars.length()));
		}
		return new String(buf);
	}

	public static void FirstTimeUserInfoMail(String toAddress,
			String fromAddress, String verificaitonCode, long userTypeId) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromAddress);
		message.setTo(toAddress);
		message.setSubject("Verification Code Sent!!!");
		message.setText("Your Email Verification has been done Successfully. \n"
				+ "Please find the verification code below, and enter this verification code "
				+ "along with your Email Id while Registering for LEARNING PRIORITY.\n\n"
				+ "Your Security code is  :  " + verificaitonCode+"\n\n--Thank you\nLearning Priority");
		message.setSentDate(new Date());
		mailSender.send(message);
	}

	public static void sendInvitations(String toAddress, String fromAddress,
			String msg, long userTypeId) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromAddress);
		message.setTo(toAddress);
		message.setSubject("Invitation");
		message.setText("Come and Join the Learning Priority community.\n"
				+ "Get involved at www.learningpriority.com.\n\n  " + msg);
		message.setSentDate(new Date());
		message.setText("Thank you");
		mailSender.send(message);
	}

	public static void setTextContent(Message msg) throws MessagingException {
		String myText = "Thank you ";
		msg.setText(myText);
		msg.setContent(myText, "text/plain");
	}

	public static void contactUsMail(String name,String phone, String iam, String comment, String to, String from) {
		// creating message
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject("");
		message.setFrom(from);
		message.setTo(from);
		message.setSubject("Comments from Site Visitors " );
		message.setText("Name : " + name + " \n" 
				+ "Phone : " + phone + "\n" + "Sender : " + iam + "\n"
				+ "Comment: " + comment + "\n" + "Sender Email id : " + to+"\n\n --\n Thank you");
		message.setSentDate(new Date());
		// sending message
		mailSender.send(message);
	}
	public static boolean sendGroupMail(String[] to,String[] cc,String[] bcc, String from,String subject, String body) {
    	boolean status = true;
    	try{
         SimpleMailMessage message = new SimpleMailMessage();
         message.setFrom(from);
         message.setTo(to);
         message.setSubject(subject);
         message.setSentDate(new Date());
         message.setText(body);
         message.setCc(cc);
         message.setBcc(bcc);
         mailSender.send(message);
    	}catch(Exception e){
    		status = false;
    		e.printStackTrace();
    	}
    	return status;
    }
	public static void promoteMail(String to, String from, String gradeName,long userTypeId,String fullname) {
		// creating message
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject("");
		if(userTypeId==4){
			message.setSubject("Your Child "+fullname+" promoted");
        }else {
           	message.setSubject("You are promoted");
        }
		if(userTypeId==4){
			message.setText("Your Child "+fullname+" promoted to "+gradeName+". \n"
                    + "Please select subjects for promoted grade.\n\n"
                    + "Thank you");
        }else {
           	message.setText("You are promoted to "+gradeName+". \n"
                   + "Please select subjects for promoted grade.\n\n"
                   + "Thank you");
        }
		message.setSentDate(new Date());
		mailSender.send(message);
	}
}

