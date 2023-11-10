package com.example.be.services;

import com.example.be.payload.DataResponse;

public interface DistrictService {
	DataResponse getDistricts(String id_province);
}
