package com.example.be.dto;

import org.hibernate.annotations.Type;

import com.example.be.entities.Clinic;
import com.example.be.entities.User;

public class CommentRequest {
	
	@Type(type="text")
	private String content;
	
	private User expert;

	private Clinic clinic;
	
	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getExpert() {
		return expert;
	}

	public void setExpert(User expert) {
		this.expert = expert;
	}
	
}
