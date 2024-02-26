package com.contact.ContactManager.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.contact.ContactManager.dao.ContactDao;
import com.contact.ContactManager.dao.UserDao;
import com.contact.ContactManager.entity.Contact;
import com.contact.ContactManager.entity.User;

@RestController
public class SearchController {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ContactDao contactDao;
	
	//search handler
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query,Principal principal)
	{
	
		System.out.println(query);
		String username=principal.getName();
			User user=this.userDao.getUserByUserName(username);
		List<Contact> contacts=	this.contactDao.findByContactNameContainingAndUser(query, user);
	return ResponseEntity.ok(contacts);
	
	}
}
