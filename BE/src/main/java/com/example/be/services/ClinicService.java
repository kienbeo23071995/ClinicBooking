package com.example.be.services;

import com.example.be.dto.ClinicRequest;
import com.example.be.dto.ClinicsRequest;
import com.example.be.payload.DataResponse;
import com.example.be.security.UserPrincipal;

public interface ClinicService {

	DataResponse addClinicCurrentDoctor(UserPrincipal currentUser,ClinicRequest clinicRequest);

	DataResponse addDoctorIntoClinic(UserPrincipal currentUser, String idClinic, String usernameOrEmail);
	
	DataResponse getDoctorInClinic(ClinicsRequest clinicsRequest) ;
	
	DataResponse getClinicAll() ;
}
