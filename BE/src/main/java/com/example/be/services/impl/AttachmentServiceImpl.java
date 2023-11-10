package com.example.be.services.impl;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import com.example.be.entities.Attachment;
import com.example.be.entities.AttachmentType;
import com.example.be.entities.User;
import com.example.be.repository.AttachmentTypeRepository;
import com.example.be.repository.AttachmentRepository;
import com.example.be.exception.FileStorageException;
import com.example.be.exception.MyFileNotFoundException;
import com.example.be.repository.UserRepository;
import com.example.be.security.UserPrincipal;
import com.example.be.services.AttachmentService;

@Service
public class AttachmentServiceImpl implements AttachmentService{

	@Autowired
	AttachmentRepository attachmentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AttachmentTypeRepository attachmentTypeRepository;
	
	@Override
	public Attachment storeFile(UserPrincipal currentUser, MultipartFile file, String attachmentType) {
		
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        User user = userRepository.getOne(currentUser.getId());
        AttachmentType attType =  attachmentTypeRepository.findByName(attachmentType);
        
        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Attachment dbFile = new Attachment(fileName, file.getContentType(), file.getBytes(), user, Collections.singleton(attType));

            return attachmentRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
	}

	@Override
	public Attachment getFile(String fileId) {
		 return attachmentRepository.findById(fileId)
	                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
	    }

}
