package com.example.be.dto;

import java.io.Serializable;

import com.example.be.entities.Role;

public class RoleRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public RoleRequest() {
		
	}
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Role setRole(RoleRequest roleRequest) {
		return new Role(roleRequest.getName());
	}

}
