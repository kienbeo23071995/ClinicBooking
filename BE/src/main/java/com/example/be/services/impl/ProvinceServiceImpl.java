package com.example.be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.be.entities.Province;
import com.example.be.payload.Data;
import com.example.be.payload.DataResponse;
import com.example.be.repository.ProvinceRepository;
import com.example.be.services.ProvinceService;

import java.util.HashSet;
import java.util.Set;
@Service
public class ProvinceServiceImpl implements ProvinceService{

	@Autowired
	ProvinceRepository provinceRepository;
	
	@Override
	public DataResponse getProvinces() {
		Set<Province>  provinces = new HashSet<Province>(provinceRepository.findAll());
		return new DataResponse(true, new Data("Lấy thành công !!",HttpStatus.OK.value(),provinces));
	}

}
