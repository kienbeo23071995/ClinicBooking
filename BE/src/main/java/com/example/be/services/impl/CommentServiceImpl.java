package com.example.be.services.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.be.dto.ClinicsRequest;
import com.example.be.dto.CommentRequest;
import com.example.be.dto.CommentResponse;
import com.example.be.entities.Attachment;
import com.example.be.entities.Clinic;
import com.example.be.entities.Comment;
import com.example.be.entities.User;
import com.example.be.payload.Data;
import com.example.be.payload.DataResponse;
import com.example.be.repository.BookingRepository;
import com.example.be.repository.ClinicRepository;
import com.example.be.repository.CommentRepositiry;
import com.example.be.repository.RateRepository;
import com.example.be.repository.UserRepository;
import com.example.be.security.UserPrincipal;
import com.example.be.services.CommentService;
import com.example.be.utils.AttacchmetFunction;
import com.example.be.utils.CommentFunction;
import com.example.be.utils.Constant;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CommentRepositiry commentRepositiry;
	
	@Autowired
	ClinicRepository clinicRepository;
	
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	RateRepository rateRepository;
	
	@Override
	public DataResponse addComment(UserPrincipal currentUser, CommentRequest commentRequest) {
		User userComment = userRepository.getOne(currentUser.getId());
		
		if(userComment != null) {
			Comment comment = new Comment();
			comment.setContent(commentRequest.getContent());
			
			User expert = userRepository.getOne(commentRequest.getExpert().getId());
			comment.setExpert(expert);
			
			Clinic clinic = clinicRepository.getOne(commentRequest.getClinic().getId());
			comment.setClinic(clinic);
			
			comment.setUser(userComment);
			Comment commnet =  commentRepositiry.save(comment);
			
			CommentResponse response = new CommentResponse();
			
			response.setId(commnet.getId());
			response.setContent(commnet.getContent());
			response.setCreateAt(commnet.getCreateAt());
			response.setUpdateAt(commnet.getUpdateAt());
			response.setCreatedBy(commnet.getCreatedBy());
			response.setUpdatedBy(commnet.getUpdatedBy());
			Attachment attachment = AttacchmetFunction.getAttachmentPerson(commnet.getUser().getAttachments(), "DAIDIEN");
			response.setAttachment(attachment);
			return new DataResponse(true, new Data(Constant.REGISTER_COMMENT_SUCCESS,HttpStatus.OK.value(),response));
		}
		return new DataResponse(false, new Data(Constant.REGISTER_COMMENT_UNSUCCESS,HttpStatus.BAD_REQUEST.value()));
	}

	@Override
	public DataResponse getListCommentDoctor(ClinicsRequest clinicsRequest) {
		
		List<Comment> commentExperts = commentRepositiry.getCommnetsByIdClincAndIdExpert(clinicsRequest.getIdClinic(),clinicsRequest.getIdDoctor());
		List<CommentResponse> commentResponses = CommentFunction.getCommentDoctor(commentExperts);
		return new DataResponse(true, new Data("get comment success !",HttpStatus.OK.value(),commentResponses));
	}

}
