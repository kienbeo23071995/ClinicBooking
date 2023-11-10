package com.example.be.services;

import com.example.be.dto.ClinicsRequest;
import com.example.be.dto.RateRequest;
import com.example.be.payload.DataResponse;
import com.example.be.security.UserPrincipal;

public interface RateService {
	
	DataResponse addRate(UserPrincipal currentUser, RateRequest rateRequest);
	
	DataResponse getRatetDoctor(ClinicsRequest clinicsRequest);
}
