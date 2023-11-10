package com.example.be.services;

import com.example.be.dto.BookingRequest;
import com.example.be.dto.BookingRequestUpdate;
import com.example.be.payload.DataResponse;
import com.example.be.security.UserPrincipal;

public interface BookingService {
	DataResponse createBooking(BookingRequest bookingRequest);
	
	DataResponse updateBooking(BookingRequestUpdate bookingRequestUpdate, UserPrincipal currentUser);
	
	DataResponse getBookedBooking(UserPrincipal currentUser,String idClinic);
	
	DataResponse getBookedBooking(UserPrincipal currentUser);
	
	DataResponse deleteBooking(String id);
}
