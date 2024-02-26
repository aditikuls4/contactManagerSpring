package com.contact.ContactManager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.contact.ContactManager.dao.UserDao;
import com.contact.ContactManager.entity.User;

public class CustomUserDetailServiceImpl  implements UserDetailsService{

	@Autowired
	private UserDao userDao;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user=userDao.getUserByUserName(username);
		
		if(user==null)
		{
			throw new UsernameNotFoundException("username could not be found!!!!");
		}
		
		CustomUserDetails customUserDetails=new CustomUserDetails(user);
		
		return customUserDetails;
	}
	
	

}
