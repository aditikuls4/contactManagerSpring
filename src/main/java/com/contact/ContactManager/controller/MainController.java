package com.contact.ContactManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contact.ContactManager.dao.UserDao;
import com.contact.ContactManager.entity.User;
import com.contact.ContactManager.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MainController {
	@Autowired
	UserDao userDao;
	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping("/home")
	public String getHomePage(Model model)
	{
		model.addAttribute("title", "Home- Page");
		System.out.println("This is home page");
		return "home";
	}
	
	@GetMapping("/about")
	public String getAbout(Model model)
	{
		model.addAttribute("title", "About- Page");
		System.out.println("This is about Page");
		return "about";
	}
	@GetMapping("/signup")
	public String getSignupForm(Model model)
	{
		model.addAttribute("title", "SignupForm- Page");
		System.out.println("This is SignupForm Page");
		model.addAttribute("user",new User());
		return "signupForm";
	}
	@PostMapping("/processForm")
	public String proccessForm(@Valid @ModelAttribute("user") User user,BindingResult result1,Model model,@RequestParam(value="enabled",defaultValue = "false") boolean agreement,
			 HttpSession session)
	
	{
		try
		{
			if(!agreement)
			{
				System.out.println("not agreed to terms and condition");	
			throw new   Exception("Terms and conditions must be agreed\"");
			}
			
			if(result1.hasErrors())
			{
			System.out.println("error"+result1.toString());
			model.addAttribute("user",user);
			return "signupForm";
			}
			
			
			user.setRole("USER");
			user.setImage("abc.jpg");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			System.out.println("agreement"+agreement);
			System.out.println(user);
			model.addAttribute("title", "Process form- Page");
			model.addAttribute("user",user);
			User result=userDao.save(user);
			model.addAttribute("user",new User());
			session.setAttribute("message", new Message("user saved successfully", "alert-success"));
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		
			session.setAttribute("message", new Message("Something went wrong!!"+e.getMessage(), "alert-danger"));

		}
		
		System.out.println("This is process Form Page");
		return "signupForm";
	}
	
	@GetMapping("/signin")
	public String mylogin(Model model)
	{
		
		model.addAttribute("title","login form");
		return "login";
		
	}
	
	
}


