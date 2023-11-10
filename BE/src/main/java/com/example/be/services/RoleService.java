package com.example.be.services;

import java.util.List;

import com.example.be.dto.RoleRequest;
import com.example.be.entities.Role;
import com.example.be.payload.DataResponse;

public interface RoleService {
	
	DataResponse createRole(RoleRequest roleRequest);
	
	Role getRoleByName(String name);
	
	Role getRoleById(String id);
	
	DataResponse deleteRole(String id);
	
	DataResponse deleteRoles(List<String> ids);
	
	DataResponse getRoleAll();
	
	DataResponse updateRole(String id, RoleRequest roleRequest);
}
