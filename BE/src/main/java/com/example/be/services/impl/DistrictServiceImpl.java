package com.example.be.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.be.entities.District;
import com.example.be.payload.Data;
import com.example.be.payload.DataResponse;
import com.example.be.repository.DistrictRepository;
import com.example.be.services.DistrictService;

@Service
public class DistrictServiceImpl implements DistrictService{

	@Autowired
	DistrictRepository districtRepository;
	
	@Override
	public DataResponse getDistricts(String id_province) {
		Set<District> districts = districtRepository.getDistricts(id_province);
		return new DataResponse(true, new Data("Lấy thành công !!",HttpStatus.OK.value(),districts));
	}

}
