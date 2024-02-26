package com.contact.ContactManager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.contact.ContactManager.dao.ContactDao;
import com.contact.ContactManager.dao.UserDao;
import com.contact.ContactManager.entity.Contact;
import com.contact.ContactManager.entity.EmailReq;
import com.contact.ContactManager.entity.User;
import com.contact.ContactManager.helper.EmailSender;
import com.contact.ContactManager.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserDao userDaO;
	
	@Autowired
	ContactDao contactDao;
	@Autowired
	EmailSender emailSender;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	
	@ModelAttribute
	public void SameComp(Model model,Principal principal)
	{String username=principal.getName();
		System.out.println("USERNAME IS"+username);
		User user=userDaO.getUserByUserName(username);
		System.out.println(user);
		model.addAttribute("user",user);
		
	}

	@GetMapping("/index")
	public String getDashboard()
	{	
		return "user/user_dashboard";
	}
		
	@GetMapping("/add_contact")
	public String addContactForm( Model model)
	{
		model.addAttribute("title","add contact form");
		model.addAttribute("contact",new Contact());
		return "user/AddContact";
	}
	
	//process addcontactform
	@PostMapping("/process_contact")
	public String processaddContactform(@ModelAttribute Contact contact,
			@RequestParam("processimage") MultipartFile file
			,Principal principal,
			HttpSession session
			)
	{
		try {
			
			String username=principal.getName();
			User user=	this.userDaO.getUserByUserName(username);
			
			//process img 
			if(file.isEmpty())
			{
				System.out.println("file is empty");
				contact.setImage("contact.jpg");
			}
			else
			{
				contact.setImage(file.getOriginalFilename());
				System.out.println(file.getOriginalFilename());
			File savefile=	new ClassPathResource("static/img").getFile();
			Path path=Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
			}
			
			
			contact.setUser(user);
			user.getContactList().add(contact);
			this.userDaO.save(user);
			System.out.println("contact is->" +contact);
			System.out.println("contact added succesfully");
			session.setAttribute("message",new Message("contact added successfully","success"));
			

			
			}
	
	
		catch (Exception e) {
		 e.printStackTrace();
			session.setAttribute("message",new Message("something went wrong","danger"));
		
	}
		return "user/AddContact";
	}
	
	//show contact form
	@GetMapping("/show_contact/{page}")
	public String showContactForm( @PathVariable("page")Integer page, Model model,Principal principal)
	{
		model.addAttribute("title","add contact form");
		String username=principal.getName();
		User user= this.userDaO.getUserByUserName(username);
		
		Pageable pagable=PageRequest.of(page, 5);
		Page<Contact> contacts=this.contactDao.getContactByUserName(user.getUserId(),pagable);
		
		model.addAttribute("contacts", contacts);
		model.addAttribute("currentPage",page);
		model.addAttribute("totalPages",contacts.getTotalPages());
		return "user/ShowContact";
	}
	
	// showing contact by id i.e->http://localhost:8080/users/2/contacts
	@GetMapping("/{contactId}/contacts")
	public String getContactDetailById(@PathVariable("contactId") int contactId,Model model,Principal principal)
	{
		String username=principal.getName();
		User user=this.userDaO.getUserByUserName(username);
		
	Contact contact=	this.contactDao.getById(contactId);
	System.out.println("contact detail-> "+ contact);
	
	if(user.getUserId()== contact.getUser().getUserId())
	{
		model.addAttribute("contact",contact);
	}
		return "user/contact_page_byid";
	}
	
	@GetMapping("/delete/{contactId}")
	public String deleteContact(@PathVariable("contactId") int contactId,Principal principal,HttpSession session)
	{
		
		String username=principal.getName();
		User user=this.userDaO.getUserByUserName(username);
		Contact contact=this.contactDao.getById(contactId);
		
		if(user.getUserId()== contact.getUser().getUserId())
		{
			user.getContactList().remove(contact);
			this.userDaO.save(user);
			session.setAttribute("message",new Message("contact deleted successfully ","success"));
		}
		return "redirect:/users/show_contact/0";
	}
	@PostMapping("/update_contact/{contactId}")
	public String updateContact(@PathVariable("contactId") int contactId,Model model)
	{
		
		Contact contact=this.contactDao.getById(contactId);
		
		model.addAttribute("contact",contact);
		return "user/update_contact_form";
	}
	@PostMapping("/process_update_contact")
	public String processupdateContact(@ModelAttribute Contact contact,
			@RequestParam("processimage") MultipartFile file
			,Principal principal,
			HttpSession session)
	{
try {
			
			String username=principal.getName();
			User user=	this.userDaO.getUserByUserName(username);
			System.out.println("user details are->hahahaha "+ user);
			System.out.println("contact details are->hahahaha "+ contact);
			Contact oldcontactDetail=this.contactDao.findById(contact.getContactId()).get();
			
			//process img 
			if(!file.isEmpty())
			{
			//delete old pic 
				File deleteFile=new ClassPathResource("static/img").getFile();
				File file1=new File(deleteFile,oldcontactDetail.getImage());
				file1.delete();
				
				//update pic 
				
				
			File savefile=	new ClassPathResource("static/img").getFile();
			Path path=Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			contact.setImage(file.getOriginalFilename());
			}
			else
			{
				contact.setImage(oldcontactDetail.getImage());
			}
			
			contact.setUser(user);
			
			this.contactDao.save(contact);
			System.out.println("contact is->" +contact);
			System.out.println("contact added succesfully");
			session.setAttribute("message",new Message("contact added successfully","success"));
			

			
			}
	
	
		catch (Exception e) {
		 e.printStackTrace();
			session.setAttribute("message",new Message("something went wrong","danger"));
		
	}
		return "redirect:/users/"+contact.getContactId()+"/contacts";

		
	}
	@GetMapping("/view_user_profile")
	public String getUserProfile( Model model)
	{
		model.addAttribute("title","showing user profile");
		
		return "user/ViewUserProfile";
	}
	
	//email
	@GetMapping("/sendEmail/{contactId}")
	public String sendEmail(@PathVariable("contactId") int contactId, Model model,Principal principal)
	{
		String username=principal.getName();
		User user=this.userDaO.getUserByUserName(username);
		
	Contact contact=	this.contactDao.getById(contactId);
	System.out.println("contact detail-> "+ contact);
	
	if(user.getUserId()== contact.getUser().getUserId())
	{
		model.addAttribute("contact",contact);
	}
	
		return "user/EmailForm";
	}
	
	
	@PostMapping("/sendEmail/{contactId}")
	public String sendingEmail(@PathVariable("contactId") int contactId,
			@ModelAttribute EmailReq emailReq
			,HttpSession session)
	{
		Contact contactPerson=this.contactDao.getContactBycontactId(contactId);
		String emailTo=contactPerson.getEmail();
		
		System.out.println(emailReq);
		emailReq.setTo(emailTo);
		emailReq.setFrom(contactPerson.getUser().getEmail());
		
		
		
		boolean issuccess=emailSender.sendEmail(emailReq.message, emailReq.to, emailReq.subject);
	
		if(issuccess==true)
		{
			session.setAttribute("message",new Message("email sent successfully","success"));	
		return "redirect:/users/sendEmail/"+contactPerson.getContactId();
		
		}
		else
		{
			return "user/error_Page";
		}
		
		
}
	@GetMapping("/settings")
	public String getSettings()
	{
		return "user/settings";
	}
	
	@PostMapping("/change_password")
	public String postSettings(@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword,Principal principal,HttpSession session)
	{

		String userName=principal.getName();
		User user=this.userDaO.getUserByUserName(userName);
		if(this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword()))
		{
				user.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
				this.userDaO.save(user);
				session.setAttribute("message",new Message("password changes  successfully","success"));
				return "redirect:/users/view_user_profile";
		}
		else
		{
			session.setAttribute("message",new Message("Worng password!! can't change the password now","danger"));
			return "user/settings";
			
		}
		
	}
	

}

