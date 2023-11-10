package com.example.be.services;

import java.util.List;

import com.example.be.dto.PriceRequest;
import com.example.be.payload.DataResponse;

public interface PriceService {
	DataResponse addPricesClinic(String idClinic, List<PriceRequest> priceRequests);
	
	DataResponse getPricesClinic(String idClinic);
}
