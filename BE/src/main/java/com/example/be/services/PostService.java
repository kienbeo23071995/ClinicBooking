package com.example.be.services;

import com.example.be.dto.PostRequest;
import com.example.be.payload.DataResponse;
import com.example.be.security.UserPrincipal;

public interface PostService {
	
	DataResponse addPostForClinic(PostRequest postRequest, UserPrincipal currentUser) ;
	
	DataResponse getPostTypePostForClinic(String idClinic, String typePost);
}
