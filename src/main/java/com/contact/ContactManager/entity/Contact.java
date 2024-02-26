package com.contact.ContactManager.entity;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int contactId;
	private String contactName;
	private String nickName;
	private String work;
	@Column(unique = true)
	private String email;
	@Column(length = 500)
	private String contactDesc;
	private String contactNumber;
	private String image;
	@ManyToOne
	@JsonIgnore
	private User user;
	
	@Override
	public String toString() {
		return "Contact [contactId=" + contactId + ", contactName=" + contactName + ", nickName=" + nickName + ", work="
				+ work + ", email=" + email + ", contactDesc=" + contactDesc + ", contactNumber=" + contactNumber
				+ ", image=" + image + "]";
	}
	
	
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactDesc() {
		return contactDesc;
	}
	public void setContactDesc(String contactDesc) {
		this.contactDesc = contactDesc;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Contact(int contactId, String contactName, String nickName, String work, String email, String contactDesc,
			String contactNumber, String image, User user) {
		super();
		this.contactId = contactId;
		this.contactName = contactName;
		this.nickName = nickName;
		this.work = work;
		this.email = email;
		this.contactDesc = contactDesc;
		this.contactNumber = contactNumber;
		this.image = image;
		this.user = user;
	}
	public Contact() {
		
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.contactId==((Contact)obj).getContactId();
	}
	
	

}
