package com.example.be.services;

import com.example.be.payload.DataResponse;

public interface ExpertCodeService {
	DataResponse saveTokenCode(String lenValue);
	
	DataResponse getTokenCode(String id);
	
	DataResponse deleteCode(String idCode);
}
