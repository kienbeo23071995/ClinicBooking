package com.example.be.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.be.dto.PostRequest;
import com.example.be.entities.Clinic;
import com.example.be.entities.Post;
import com.example.be.entities.PostType;
import com.example.be.payload.Data;
import com.example.be.payload.DataResponse;
import com.example.be.repository.ClinicRepository;
import com.example.be.repository.PostRepository;
import com.example.be.repository.PostTypeRepository;
import com.example.be.security.UserPrincipal;
import com.example.be.services.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	PostRepository postRepository;
	
	@Autowired
	ClinicRepository clinicRepository;
	
	@Autowired
	PostTypeRepository postTypeRepository;
	
	@Override
	public DataResponse addPostForClinic(PostRequest postRequest, UserPrincipal currentUser) {
		Clinic clinic = clinicRepository.getOne(postRequest.getIdClinic());
		
		if(clinic != null) {
			PostType postType = postTypeRepository.getOne(postRequest.getIdTypePost());
			Post post = new Post();
			post.setContent(postRequest.getContent());
			post.setClinic(clinic);
			post.setPostTypes(postType);
			postRepository.save(post);
			
			return new DataResponse(true, new Data("Tạo bài viết thành công !",HttpStatus.OK.value()));
		}
		
		return new DataResponse(false, new Data("Phòng khám không tồn tại !",HttpStatus.BAD_REQUEST.value()));
	}

	@Override
	public DataResponse getPostTypePostForClinic(String idClinic, String typePost) {
		Set<Post> posts = postRepository.getPostFollowTypePost(idClinic, typePost);
		
		if(!posts.isEmpty()) {
			return new DataResponse(true, new Data("Lấy thành công !",HttpStatus.OK.value(),posts));
		}
		return new DataResponse(false, new Data("Lấy không thành công !",HttpStatus.BAD_REQUEST.value()));
	}

}
