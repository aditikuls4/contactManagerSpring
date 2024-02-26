package com.contact.ContactManager.entity;




import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;


@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	@NotBlank(message = "Name field must required")
	@Size(min = 2,max = 20,message = "min 2 and max 20 characters required!!!!")
	private String username;
	@Column(unique = true)
	private String email;
	private String role;
	private String password;
	@Column(length = 500)
	private String about;
	private String image;
	private boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	List<Contact> contactList=new ArrayList<>();
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public List<Contact> getContactList() {
		return contactList;
	}
	public void setContactList(List<Contact> contactList) {
		this.contactList = contactList;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", email=" + email + ", role=" + role
				+ ", password=" + password + ", about=" + about + ", image=" + image + ", enabled=" + enabled
				+ ", contactList=" + contactList + "]";
	}

	public User(int userId, String username, String email, String role, String password, String about, String image,
			boolean enabled, List<Contact> contactList) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.role = role;
		this.password = password;
		this.about = about;
		this.image = image;
		this.enabled = enabled;
		this.contactList = contactList;
	}
	public User() {

		// TODO Auto-generated constructor stub
	}
	
	
}
