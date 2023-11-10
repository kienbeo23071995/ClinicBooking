package com.example.be.services.impl;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.be.dto.ClinicsRequest;
import com.example.be.dto.RateRequest;
import com.example.be.entities.Clinic;
import com.example.be.entities.Rate;
import com.example.be.entities.User;
import com.example.be.payload.Data;
import com.example.be.payload.DataResponse;
import com.example.be.repository.RateRepository;
import com.example.be.repository.UserRepository;
import com.example.be.repository.BookingRepository;
import com.example.be.repository.ClinicRepository;
import com.example.be.repository.CommentRepositiry;
import com.example.be.security.UserPrincipal;
import com.example.be.services.RateService;
import com.example.be.utils.Constant;
import com.example.be.utils.RateFunction;

@Service
public class RateServiceImpl implements RateService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ClinicRepository clinicRepository;
	
	@Autowired
	ClinicServiceImpl clinicServiceImpl;
	@Autowired
	CommentRepositiry commentRepositiry;
	
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	RateRepository rateRepository;
	
	@Override
	public DataResponse addRate(UserPrincipal currentUser, RateRequest rateRequest) {
		
		Rate checkRate = rateRepository.checkUserAndDoctorForRate(currentUser.getId(), rateRequest.getExpert().getId());
		User user = userRepository.getOne(currentUser.getId());
		
		if(checkRate !=null) {
			rateRepository.delete(checkRate);
			if(user != null) {
				Rate rate = new Rate();
				rate.setUser(user);
				
				rate.setNumberStar(rateRequest.getNumberStar());
				
				User expert = userRepository.getOne(rateRequest.getExpert().getId());
				rate.setExpert(expert);
				
				Clinic clinic = clinicRepository.getOne(rateRequest.getClinic().getId());
				rate.setClinic(clinic);
				
				Rate ratedata =rateRepository.save(rate);
				
				return new DataResponse(true, new Data(Constant.ADD_RATE_SUCCESS,HttpStatus.OK.value(),ratedata));
			}
		}else {
			if(user != null) {
				Rate rate = new Rate();
				rate.setUser(user);
				
				rate.setNumberStar(rateRequest.getNumberStar());
				
				User expert = userRepository.getOne(rateRequest.getExpert().getId());
				rate.setExpert(expert);
				
				Clinic clinic = clinicRepository.getOne(rateRequest.getClinic().getId());
				rate.setClinic(clinic);
				
				Rate ratedata =rateRepository.save(rate);
					
				return new DataResponse(true, new Data(Constant.ADD_RATE_SUCCESS,HttpStatus.OK.value(),ratedata));
			}
		}
		
		return new DataResponse(false, new Data(Constant.ADD_RATE_UNSUCCESS,HttpStatus.BAD_REQUEST.value()));
		
	}
	
	@Override
	public DataResponse getRatetDoctor(ClinicsRequest clinicsRequest) {
		Set<Rate> rateExperts = rateRepository.getRatesByIdClincAndIdExpert(clinicsRequest.getIdClinic(),clinicsRequest.getIdDoctor());
		Double countRate = RateFunction.getRateDoctor(rateExperts);
		return new DataResponse(true, new Data("get rate success !",HttpStatus.OK.value(),countRate));
	}
}
