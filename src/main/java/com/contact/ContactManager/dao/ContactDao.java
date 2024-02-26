package com.contact.ContactManager.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contact.ContactManager.entity.Contact;
import com.contact.ContactManager.entity.User;

public interface ContactDao extends JpaRepository<Contact, Integer> {

	@Query("select c from Contact c where c.user.userId=:userId")
	public Page<Contact> getContactByUserName(@Param("userId") int userId, Pageable page);

	public List<Contact> findByContactNameContainingAndUser(String contactName, User user);

	public Contact getContactBycontactId(int contactId);
}
