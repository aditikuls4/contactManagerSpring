package com.contact.ContactManager.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contact.ContactManager.dao.UserDao;
import com.contact.ContactManager.entity.User;
import com.contact.ContactManager.helper.EmailSender;
import com.contact.ContactManager.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotPasswordController {
	Random random=new Random(1000);
	
	@Autowired
	EmailSender emailSender;
	@Autowired
	UserDao ur;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/forgot_Password")
	public String openEmailForm()
	{
		return "fg_email_form";
	}

	
	@PostMapping("/otp_generate")
	public String otpForm(@RequestParam("email") String email,HttpSession httpSession)
	{
	
		
		int newOtp=random.nextInt(99999);
		System.out.println("new otp is"+newOtp);
		
		String message="your otp is "+ newOtp;
		String subject="OTP FOR CONTACT MANAGEMENT";
	
		boolean  isOtpSend=emailSender.sendEmail(message,email,subject);
		if(isOtpSend)
		{	
			httpSession.setAttribute("message",new Message("Otp sent successfully","success"));
			httpSession.setAttribute("myotp",newOtp);
			httpSession.setAttribute("email",email);
			return "otpForm";
		}
		
		else
		{
			httpSession.setAttribute("message",new Message("Please check email id","danger"));
			return "fg_email_form";
		}
		}
	
	@PostMapping("/changePass")
	public String changePass(@RequestParam("verifyOtp") int verifyOtp ,HttpSession httpSession) 
	{
		
		int myOtp=(int) httpSession.getAttribute("myotp");
		//first check otp is valid or not
		if(myOtp==verifyOtp)
		{
			//check for user valid
			String email=(String)httpSession.getAttribute("email");
			User user=this.ur.getUserByUserName(email);
			if(user==null)
			{
				//it means user does not exist
				httpSession.setAttribute("message",new Message("Please check email id user does not exist","danger"));
				return "fg_email_form";
			}
			else
			{
				//now you can go to change password page
				return "change_password_form";
			}
		}
		else
		{
			httpSession.setAttribute("message",new Message("Please verify otp","danger"));
			return "otpForm";
		}
	}
	
	
	@PostMapping("/changePassSuccess")
	public String changePassSuccessfully(@RequestParam("password") String password,HttpSession httpSession)
	{
		String email=(String)httpSession.getAttribute("email");
		User user=this.ur.getUserByUserName(email);
		user.setPassword(this.bCryptPasswordEncoder.encode(password));
		this.ur.save(user);
		
		return "redirect:/signin?changePass=password changed successfully....";
		
	}
}
