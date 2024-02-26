package com.contact.ContactManager.entity;




public class EmailReq {
	
	
	public int emailReqId;
	public String to;
	public String subject;
	public String message;
	public String from;
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
		
	}
	public int getEmailReqId() {
		return emailReqId;
	}
	public void setEmailReqId(int emailReqId) {
		this.emailReqId = emailReqId;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	@Override
	public String toString() {
		return "EmailReq [emailReqId=" + emailReqId + ", to=" + to + ", subject=" + subject + ", message=" + message
				+ ", from=" + from + "]";
	}
	public EmailReq(int emailReqId, String to, String subject, String message, String from) {
		super();
		this.emailReqId = emailReqId;
		this.to = to;
		this.subject = subject;
		this.message = message;
		this.from = from;
	}
	public EmailReq() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	

}
