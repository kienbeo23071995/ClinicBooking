package com.example.be.services;

import com.example.be.dto.ClinicsRequest;
import com.example.be.dto.CommentRequest;
import com.example.be.payload.DataResponse;
import com.example.be.security.UserPrincipal;

public interface CommentService {
	
	DataResponse addComment(UserPrincipal currentUser, CommentRequest commentRequest);
	
	DataResponse getListCommentDoctor(ClinicsRequest clinicsRequest);
}
