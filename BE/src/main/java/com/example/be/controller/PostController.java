package com.example.be.controller;

import javax.validation.Valid;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

	@RequestMapping(value = "edit",method = RequestMethod.POST, produces = "application/json")
	public DataResponse editPostForClinic(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody PostRequest postRequest){
		return postService.editPostForClinic(postRequest, currentUser);
	}

	@RequestMapping(value = "delete/{id}",method = RequestMethod.GET, produces = "application/json")
	public DataResponse deletePostForClinic(@PathVariable("id") final String id){
		return postService.deletePost(id);
	}
}
