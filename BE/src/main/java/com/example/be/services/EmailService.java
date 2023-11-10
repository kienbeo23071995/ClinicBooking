package com.example.be.services;

import javax.servlet.http.HttpServletRequest;

import com.example.be.dto.BookingRequestUpdate;
import com.example.be.entities.User;

public interface EmailService {
	
	void sendEmail(String email, HttpServletRequest httpRequest, User user);
	
	void sendEmailBookingClient(BookingRequestUpdate bookingUpdate);
	
	void sendEmailBookingDoctor(BookingRequestUpdate bookingUpdate);
	
	void sendEmailBookingBussy(String idDoctor, String idBooked);
}
