package com.example.be.services.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.be.dto.ClinicRequest;
import com.example.be.dto.ClinicResponceDoctors;
import com.example.be.dto.ClinicResponse;
import com.example.be.dto.ClinicsRequest;
import com.example.be.dto.UserResponceClinic;
import com.example.be.entities.Attachment;
import com.example.be.entities.Booking;
import com.example.be.entities.Clinic;
import com.example.be.entities.Comment;
import com.example.be.entities.Rate;
import com.example.be.entities.User;
import com.example.be.payload.Data;
import com.example.be.payload.DataResponse;
import com.example.be.repository.BookingRepository;
import com.example.be.repository.ClinicRepository;
import com.example.be.repository.CommentRepositiry;
import com.example.be.repository.FacultyRepository;
import com.example.be.repository.RateRepository;
import com.example.be.repository.UserRepository;
import com.example.be.security.UserPrincipal;
import com.example.be.services.ClinicService;
import com.example.be.utils.AttacchmetFunction;
import com.example.be.utils.Constant;
import com.example.be.utils.DateBookingsFunction;
import com.example.be.utils.UserResponceClinicFunction;

@Service
public class ClinicServiceImpl implements ClinicService{

	@Autowired
	ClinicRepository clinicRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FacultyRepository facultyRepository;
	
	@Autowired
	CommentRepositiry commentRepositiry;
	
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	RateRepository rateRepository;
	
	@Override
	public DataResponse addClinicCurrentDoctor(UserPrincipal currentUser, ClinicRequest clinicRequest) {
		User user = userRepository.findByIdAndCheckRole(currentUser.getId(), true, Constant.EXPERT);
		if(user ==null) {
			return new DataResponse(false, new Data(Constant.NO_EXPERT,HttpStatus.BAD_REQUEST.value()));
		}
		
		if(!"".equals(currentUser.getId())) {
			Clinic clinic = new Clinic(clinicRequest.getName(), clinicRequest.getAddress(), clinicRequest.getLatitude(), clinicRequest.getLongitude(),clinicRequest.getAddressQuery());
			
			List<String> idFaculties = new ArrayList<String>();
			clinicRequest.getFaculties().stream().forEach( x -> idFaculties.add(x.getId()));
			
			clinic.setFaculties(facultyRepository.findByIdIn(idFaculties));
			
			Set<User> users = new HashSet<>();
			users.add(user);
			clinic.setUsers(users);
			
			clinicRepository.save(clinic);
			
			ClinicResponse clinicResponse = new ClinicResponse(clinic);
			return new DataResponse(true, new Data(Constant.REGISTER_CLINIC_SUCCESS,HttpStatus.OK.value(),clinicResponse));
		}else {
			return new DataResponse(false, new Data(Constant.USER_NO_FIND_ID,HttpStatus.BAD_REQUEST.value()));
		}
	}

	@Override
	public DataResponse addDoctorIntoClinic(UserPrincipal currentUser, String idClinic, String usernameOrEmail) {
		User user = userRepository.findByIdAndCheckRole(currentUser.getId(), true, "EXPERT");
		Clinic clinic = clinicRepository.findByIdClinicAndIdUser(idClinic,user.getId());
		User userAdd = userRepository.findByEmailAndCheckRole(usernameOrEmail, true, "EXPERT");
		
		if(clinic != null && userAdd != null){
			Set<User> users = userRepository.getUserByIdClinic(idClinic, true);
			users.add(userAdd);
			clinic.setUsers(users);
			
			clinicRepository.save(clinic);
			return new DataResponse(true, new Data(Constant.ADD_DOCTOR_SUCCESS,HttpStatus.OK.value(),clinic));
		}else {
			return new DataResponse(false, new Data(Constant.ADD_DOCTOR_UNSUCCESS,HttpStatus.BAD_REQUEST.value()));
		}
	}

	@Override
	public DataResponse getDoctorInClinic(ClinicsRequest clinicsRequest) {
		User user = userRepository.findByIdAndCheckRole(clinicsRequest.getIdDoctor(), true, "EXPERT");
		Clinic clinic = clinicRepository.findByIdClinicAndIdUser(clinicsRequest.getIdClinic(),clinicsRequest.getIdDoctor());
		if(user != null || clinic != null) {
			ClinicResponceDoctors clinicResponceDoctors = new ClinicResponceDoctors();
			List<UserResponceClinic> userResponceClinics = new ArrayList<UserResponceClinic>();
			
			List<User> users = new ArrayList<> (clinic.getUsers());
			User userOwner = users.get(users.size()-1);
			Set<Attachment> attachmentClinics = AttacchmetFunction.getAttachmentClinicPhotos(userOwner.getAttachments(), "CLINIC");
			
			Attachment photoClinicLogo = AttacchmetFunction.getAttachmentPerson(userOwner.getAttachments(), "DAIDIENCLINIC");
			
			clinicResponceDoctors.setId(clinic.getId()); clinicResponceDoctors.setName(clinic.getName());
			clinicResponceDoctors.setAddress(clinic.getAddress()); clinicResponceDoctors.setLatitude(clinic.getLatitude());
			clinicResponceDoctors.setLongitude(clinic.getLongitude()); clinicResponceDoctors.setCreatedBy(clinic.getCreatedBy());
			clinicResponceDoctors.setCreateAt(clinic.getCreateAt()); clinicResponceDoctors.setFaculties(clinic.getFaculties());
			clinicResponceDoctors.setPrices(clinic.getPrices()); clinicResponceDoctors.setPhotoClinics(attachmentClinics);
			clinicResponceDoctors.setPhotoClinicLogo(photoClinicLogo);
			
			List<Comment> commentExperts = commentRepositiry.getCommnetsByIdClincAndIdExpert(clinic.getId(),user.getId());

			List<Booking> bookingDates = bookingRepository.getBookingsAllByIdClincAndIdExpert(clinic.getId(),user.getId(),clinicsRequest.getDateCurrent());
			
			List<Booking> bookingExperts = new ArrayList<Booking>();
			List<Date> listdate= DateBookingsFunction.getListDateBookings(bookingDates);
			
			boolean checkDateContain = DateBookingsFunction.checkDayQureyContaninDates(listdate, clinicsRequest.getDateQurey());
			if(checkDateContain == false && !listdate.isEmpty()) {
				bookingExperts = bookingRepository.getBookingsByIdClincAndIdExpert(clinic.getId(),user.getId(),listdate.get(0));
			}else {
				bookingExperts = bookingRepository.getBookingsByIdClincAndIdExpert(clinic.getId(),user.getId(),clinicsRequest.getDateQurey());
			}
			
			Set<Rate> rateExperts = rateRepository.getRatesByIdClincAndIdExpert(clinic.getId(),user.getId());
			
			Attachment attachmentp = AttacchmetFunction.getAttachmentPerson(user.getAttachments(), "DAIDIEN");
			
			UserResponceClinic userClinic = UserResponceClinicFunction.setUserResponceClinic(user, clinic.getId(),commentExperts,bookingExperts,bookingDates,rateExperts,attachmentp);
			
			userResponceClinics.add(userClinic);
			
			for (User userItem : clinic.getUsers()) {
				if(!userItem.getId().equals(user.getId())) {
					
					List<Comment> commentExpertsList =  commentRepositiry.getCommnetsByIdClincAndIdExpert(clinic.getId(),userItem.getId());
					
					List<Booking> bookingExpertDates = bookingRepository.getBookingsAllByIdClincAndIdExpert(clinic.getId(),userItem.getId(),clinicsRequest.getDateCurrent());
					
					List<Booking> bookingExpertsList = new ArrayList<Booking>();
					List<Date> listExpertdate= DateBookingsFunction.getListDateBookings(bookingExpertDates);
					boolean checkDateExpertContain = DateBookingsFunction.checkDayQureyContaninDates(listExpertdate, clinicsRequest.getDateQurey());
					if(checkDateExpertContain == false && !listExpertdate.isEmpty()) {
						bookingExpertsList = bookingRepository.getBookingsByIdClincAndIdExpert(clinic.getId(),userItem.getId(),listExpertdate.get(0));
					}else {
						bookingExpertsList = bookingRepository.getBookingsByIdClincAndIdExpert(clinic.getId(),userItem.getId(),clinicsRequest.getDateQurey());
					}

					Set<Rate> rateExpertsList = rateRepository.getRatesByIdClincAndIdExpert(clinic.getId(),userItem.getId());
					
					Attachment attachmentpList = AttacchmetFunction.getAttachmentPerson(userItem.getAttachments(), "DAIDIEN");
					
					UserResponceClinic userResponceClinic = UserResponceClinicFunction.setUserResponceClinic(userItem, clinic.getId(),commentExpertsList,bookingExpertsList,bookingExpertDates,rateExpertsList,attachmentpList);
					
					userResponceClinics.add(userResponceClinic);
				}
			}
			
			clinicResponceDoctors.setUserResponceClinics(userResponceClinics);
			return new DataResponse(true, new Data("Lấy danh sách bác sỹ trong phòng khám thành công !",HttpStatus.OK.value(),clinicResponceDoctors));
		}
		
		return new DataResponse(true, new Data("Lấy danh sách bác sỹ thất bại !",HttpStatus.BAD_REQUEST.value()));
		
	}

	@Override
	public DataResponse getClinicAll() {
		List<Clinic> clinics = clinicRepository.findAll();
		if(clinics.size() >0) {
			return new DataResponse(true, new Data("Lấy danh sách bác sỹ trong phòng khám thành công !",HttpStatus.OK.value(),clinics));
		}
		return new DataResponse(true, new Data("Lấy danh sách bác sỹ thất bại !",HttpStatus.BAD_REQUEST.value()));
	}
	
}
