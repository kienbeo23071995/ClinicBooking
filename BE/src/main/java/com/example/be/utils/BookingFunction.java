package com.example.be.utils;


import com.example.be.dto.BookingResponse;
import com.example.be.entities.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingFunction {
	public static List<BookingResponse> getBookingDoctor(List<Booking> bookingExperts) {
		List<BookingResponse> bookingResponses = new ArrayList<BookingResponse>();
		
		for (Booking item : bookingExperts) {
			BookingResponse response = new BookingResponse();
			response.setId(item.getId());
			response.setDateBooking(item.getDateBooking());
			response.setTimeBooking(item.getTimeBooking());
			response.setIsExit(item.getIsExit());
			
			bookingResponses.add(response);
		}
		return bookingResponses;
	}
}
