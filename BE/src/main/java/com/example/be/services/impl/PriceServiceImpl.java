package com.example.be.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.be.dto.PriceRequest;
import com.example.be.entities.Clinic;
import com.example.be.entities.Price;
import com.example.be.payload.Data;
import com.example.be.payload.DataResponse;
import com.example.be.repository.ClinicRepository;
import com.example.be.repository.PriceRepository;
import com.example.be.services.PriceService;

@Service
public class PriceServiceImpl implements PriceService{

	@Autowired
	ClinicRepository clinicRepository;
	
	@Autowired
	PriceRepository priceRepository;
	
	@Override
	public DataResponse addPricesClinic(String idClinic, List<PriceRequest> priceRequests) {
		Clinic clinic = clinicRepository.getOne(idClinic);
		if(clinic != null && priceRequests.size()!= 0) {
			List<Price> prices = new ArrayList<Price>();
			for (PriceRequest item : priceRequests) {
				Price price = new Price();
				price.setTotalPrice(item.getTotalPrice());
				price.setDescription(item.getDescription());
				price.setClinic(clinic);
				prices.add(price);
			}
			priceRepository.saveAll(prices);
			return new DataResponse(true, new Data("Thêm thành công !!",HttpStatus.OK.value()));
		}
		return  new DataResponse(false, new Data("Phòng khám không tồn tại !",HttpStatus.BAD_REQUEST.value()));
	}

	@Override
	public DataResponse getPricesClinic(String idClinic) {
		Clinic clinic = clinicRepository.getOne(idClinic);
		if(clinic != null) {
			List<Price> prices = priceRepository.getPricesClinic(idClinic);
			if(prices.size() != 0) {
				return new DataResponse(true, new Data("Lấy thành công !!",HttpStatus.OK.value(),prices));
			}else {
				return  new DataResponse(false, new Data("Lấy không tồn tại !",HttpStatus.BAD_REQUEST.value()));
			}
		}
		
		return  new DataResponse(false, new Data("Phòng khám không tồn tại !",HttpStatus.BAD_REQUEST.value()));
	}

}
