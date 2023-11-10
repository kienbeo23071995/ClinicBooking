package com.example.be.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.be.entities.PostType;
import com.example.be.payload.Data;
import com.example.be.payload.DataResponse;
import com.example.be.repository.PostTypeRepository;
import com.example.be.services.PostTypeService;

@Service
public class PostTypeServiceImpl implements PostTypeService{

	@Autowired
	PostTypeRepository postTypeRepository;
	
	@Override
	public DataResponse getAllPostType() {
		List<PostType> postTypes = postTypeRepository.findAll();
		return new DataResponse(true, new Data("Tạo bài viết thành công !",HttpStatus.OK.value(),postTypes));
	}

}
