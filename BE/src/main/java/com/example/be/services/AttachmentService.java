package com.example.be.services;

import org.springframework.web.multipart.MultipartFile;

import com.example.be.entities.Attachment;
import com.example.be.security.UserPrincipal;

public interface AttachmentService {
	
	Attachment storeFile(UserPrincipal currentUser, MultipartFile file,String attachmentType);
	
	Attachment getFile(String fileId);
}
