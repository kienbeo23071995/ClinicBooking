package com.example.be.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.be.dto.PostRequest;
import com.example.be.payload.DataResponse;
import com.example.be.security.CurrentUser;
import com.example.be.security.UserPrincipal;
import com.example.be.services.PostService;

@RestController
@RequestMapping("api/posts")
public class PostController {

	@Autowired
	PostService postService;
	
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public DataResponse addPostForClinic(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody PostRequest postRequest){
		return postService.addPostForClinic(postRequest, currentUser);
	}
	
}
