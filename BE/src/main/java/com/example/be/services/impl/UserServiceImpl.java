package com.example.be.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.be.dto.ClinicResponse;
import com.example.be.dto.DoctorRegisterRequest;
import com.example.be.dto.DoctorResponse;
import com.example.be.dto.UserResponse;
import com.example.be.dto.UserUpdate;
import com.example.be.entities.Attachment;
import com.example.be.entities.AttachmentType;
import com.example.be.entities.Booking;
import com.example.be.entities.Clinic;
import com.example.be.entities.Comment;
import com.example.be.entities.Degree;
import com.example.be.entities.ExpertCode;
import com.example.be.entities.Faculty;
import com.example.be.entities.Rate;
import com.example.be.entities.Role;
import com.example.be.entities.User;
import com.example.be.payload.Data;
import com.example.be.payload.DataResponse;
import com.example.be.repository.DegreeRepository;
import com.example.be.repository.ExpertCodeRepository;
import com.example.be.repository.FacultyRepository;
import com.example.be.repository.UserRepository;
import com.example.be.repository.ClinicRepository;
import com.example.be.repository.RateRepository;
import com.example.be.repository.CommentRepositiry;
import com.example.be.repository.BookingRepository;
import com.example.be.services.RoleService;
import com.example.be.services.UserService;
import com.example.be.utils.AttacchmetFunction;
import com.example.be.utils.Constant;
import com.example.be.utils.RateFunction;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ExpertCodeRepository expertCodeRepository;
	
	@Autowired
	FacultyRepository facultyRepository;
	
	@Autowired
	DegreeRepository degreeRepository;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	ClinicRepository clinicRepository;
	
	@Autowired
	CommentRepositiry commentRepositiry;
	
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	RateRepository rateRepository;
	
	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Optional<User> findByUsernameOrEmail(String username, String email) {
		return userRepository.findByUsernameOrEmail(username, email);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> findByIdIn(List<String> userIds) {
		return userRepository.findByIdIn(userIds);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findByCode(String code) {
		User user = userRepository.findByCode(code);
		if(user != null) {
			return user;
		}
		return null;
	}

	@Override
	public List<User> findByIds(List<String> ids) {
		List<User> users = userRepository.findByIdIn(ids);
		if(users != null) {
			return users;
		}
		return null;
	}

	@Override
	public DataResponse updateUser(String id, DoctorRegisterRequest doctorRegisterRequest) {
//		String token = doctorRegisterRequest.getTokenCode();
		
//		ExpertCode checkToken = expertCodeRepository.getExpertCode(token,true);
//
//		if(checkToken == null) {
//            return new DataResponse(false, new Data(Constant.TOKEN_NO_FIND_ID,HttpStatus.BAD_REQUEST.value()));
//		}
		
		if(!"".equals(id)) {
			List<String> ids = new ArrayList<String>() ;
			ids.add(id);
			List<User> users = findByIds(ids);
			if(users.size() != 0) {
				User user = users.get(0);
				
				user.setFullName(doctorRegisterRequest.getFullName());
				user.setBirthday(doctorRegisterRequest.getBirthday());
				user.setGender(doctorRegisterRequest.getGender());
				user.setAge(doctorRegisterRequest.getAge());
				user.setAddress(doctorRegisterRequest.getAddress());
				user.setEmail(user.getEmail());
				user.setMobile(doctorRegisterRequest.getMobile());
				user.setAbout(doctorRegisterRequest.getAbout());
				
				List<String> idFaculties = new ArrayList<String>();
				for (Faculty faculty : doctorRegisterRequest.getFaculties()) {
					idFaculties.add(faculty.getId());
				}
				Set<Faculty>  faculties = facultyRepository.findByIdIn(idFaculties);
				user.setFaculties(faculties);
				
				List<String> idDegrees = new ArrayList<String>();
				for (Degree degree : doctorRegisterRequest.getDegrees()) {
					idDegrees.add(degree.getId());
				}
				Set<Degree>  degrees = degreeRepository.findByIdIn(idDegrees);
				user.setDegrees(degrees);
				
				Set<Role> roles = new HashSet<>();
				Role userRole = roleService.getRoleByName(Constant.USER);
				Role expertRole = roleService.getRoleByName(Constant.EXPERT);
				roles.add(userRole);
				roles.add(expertRole);
				user.setRoles(roles);
				
				userRepository.save(user);
				
//				ExpertCode expertCode = expertCodeRepository.getExpertCode(token,true);
//				expertCode.setActive(false);
//				expertCodeRepository.save(expertCode);

                return new DataResponse(true, new Data(Constant.REGISTER_DOCTOR_SUCCESS,HttpStatus.OK.value(),user));
			        
			}else {
                return new DataResponse(false, new Data(Constant.USER_NO_FIND_ID,HttpStatus.BAD_REQUEST.value()));
			}
		}
        return new DataResponse(false, new Data(Constant.REGISTER_DOCTOR_UNSUCCESS,HttpStatus.BAD_REQUEST.value()));
	}
	
	@Override
	public UserResponse getUserByIdAndCheckRole(String id_user) {
		List<Clinic> clinincs = clinicRepository.showClinicWithIdUser(id_user);
		User user = userRepository.getUserAndRole(id_user, true);
		return new UserResponse(user,clinincs);
	}

	@SuppressWarnings("unused")
	@Override
	public DataResponse getAllDoctor() {
		List<DoctorResponse> doctorResponses = new ArrayList<DoctorResponse>();
		List<Clinic> clinics = clinicRepository.findAll();
		for (Clinic clinic : clinics) {
			for (User item : clinic.getUsers()) {
				Attachment attachmentp = new Attachment();
				int flag = 0;
				for (Attachment attachment : item.getAttachments()) {
					for (AttachmentType attachmentType : attachment.getAttachmentTypes()) {
						if(attachmentType.getName().equals("DAIDIEN")) {
							attachmentp.setId(attachment.getId());
							attachmentp.setData(attachment.getData());
							attachmentp.setCreatedBy(attachment.getCreatedBy());
							attachmentp.setFileName(attachment.getFileName());
							attachmentp.setAttachmentTypes(attachment.getAttachmentTypes());
							flag++;
							break;
						}
						if(flag>0) {
							break;
						}
					}
				}
				
				List<Comment> commentExperts = commentRepositiry.getCommnetsByIdClincAndIdExpert(clinic.getId(),item.getId());
				
				List<Booking> bookingExperts = bookingRepository.getBookedsByIdClincAndIdExpert(clinic.getId(),item.getId(),true);
				
				Set<Rate> rateExperts = rateRepository.getRatesByIdClincAndIdExpert(clinic.getId(),item.getId());
				Double countRate = RateFunction.getRateDoctor(rateExperts);
				
				DoctorResponse doctorResponse = new DoctorResponse(item.getId(), item.getCreateAt(), 
						item.getUpdateAt(), item.getCreatedBy(), item.getUpdatedBy(), item.getDeletedBy(),
						item.getFullName(), item.getBirthday(), item.getGender(), item.getAge(), 
						item.getEmail(), item.getAddress(), item.getMobile(), item.getAbout(), 
						item.getFacebook(), new ClinicResponse(clinic), item.getFaculties(), item.getDegrees(),attachmentp,commentExperts.size(),bookingExperts.size(),countRate);
				doctorResponses.add(doctorResponse);
			}
		}
		
		if(clinics == null) {
			return new DataResponse(false, new Data("Không tìm thấy bác sỹ !!",HttpStatus.BAD_REQUEST.value()));
		}
		return new DataResponse(true, new Data("lấy danh sách bác sỹ thành công !!",HttpStatus.OK.value(),doctorResponses));
	}

	@Override
	public DataResponse reportUser(String idUser, String idDoctor,String idBooked) {
		Booking booking  = bookingRepository.getOne(idBooked);
		if(booking.isActive() == true) {
			User doctor = userRepository.getOne(idDoctor);
			int countRole = doctor.getRoles().size();
			if(countRole > 1) {
				User user =  userRepository.getOne(idUser);
				Integer point = user.getBadPoint();
				if(point == null) {
					point = 0;
				}
				Integer pointAfter = point + 1;
				user.setBadPoint(pointAfter);
				booking.setActive(false);
				if(pointAfter >=3) {
					user.setActive(false);
				}
				userRepository.save(user);
				bookingRepository.save(booking);
				return new DataResponse(true, new Data("Report thành công !!",HttpStatus.OK.value()));
			}
			return new DataResponse(false, new Data("Bạn không phải bác sỹ !!",HttpStatus.BAD_REQUEST.value()));
		}
		
		return new DataResponse(false, new Data("Bạn đã report rồi !!",HttpStatus.BAD_REQUEST.value()));
	}

	@Override
	public DataResponse geUserprofile(String idUser) {
		User user = userRepository.getOne(idUser);
		if(user != null) {
			Attachment attachment = AttacchmetFunction.getAttachmentPerson(user.getAttachments(), "DAIDIEN");
			DoctorResponse doctorResponse = new DoctorResponse(user.getId(),user.getCreateAt(),user.getUpdateAt(),user.getCreatedBy(),user.getUpdatedBy(),user.getDeletedBy(),user.getFullName(),user.getBirthday(),user.getGender(),user.getEmail(),user.getAddress(),user.getMobile(),user.getAbout(),user.getFacebook(),attachment);
			return new DataResponse(true, new Data("Lấy Thông tin thành công !!",HttpStatus.OK.value(),doctorResponse));
		}
		return new DataResponse(false, new Data("Lấy Thông tin không thành công !!",HttpStatus.BAD_REQUEST.value()));
	}

	@Override
	public DataResponse updateUserUpdate(UserUpdate userUpdate) {
		User user = userRepository.getOne(userUpdate.getId());
		
		if(user != null) {
			user.setAbout(userUpdate.getAbout());
			user.setAddress(userUpdate.getAddress());
			user.setBirthday(userUpdate.getBirthday());
			user.setFacebook(userUpdate.getFacebook());
			user.setFullName(userUpdate.getFullName());
			user.setMobile(userUpdate.getMobile());
			userRepository.save(user);
			return new DataResponse(true, new Data("Cập nhật thông tin thành công !!",HttpStatus.OK.value(),user));
		}
		return new DataResponse(false, new Data("Cập nhật thông tin không thành công !!",HttpStatus.BAD_REQUEST.value()));
	}

	@Override
	public DataResponse searchClinic(String addressQuery) {
		List<DoctorResponse> doctorResponses = new ArrayList<DoctorResponse>();
		List<Clinic> clinics = clinicRepository.searchClinic(addressQuery);
		for (Clinic clinic : clinics) {
			for (User item : clinic.getUsers()) {
				Attachment attachmentp = new Attachment();
				int flag = 0;
				for (Attachment attachment : item.getAttachments()) {
					for (AttachmentType attachmentType : attachment.getAttachmentTypes()) {
						if(attachmentType.getName().equals("DAIDIEN")) {
							attachmentp.setId(attachment.getId());
							attachmentp.setData(attachment.getData());
							attachmentp.setCreatedBy(attachment.getCreatedBy());
							attachmentp.setFileName(attachment.getFileName());
							attachmentp.setAttachmentTypes(attachment.getAttachmentTypes());
							flag++;
							break;
						}
						if(flag>0) {
							break;
						}
					}
				}
				
				List<Comment> commentExperts = commentRepositiry.getCommnetsByIdClincAndIdExpert(clinic.getId(),item.getId());
				
				List<Booking> bookingExperts = bookingRepository.getBookedsByIdClincAndIdExpert(clinic.getId(),item.getId(),true);
				
				Set<Rate> rateExperts = rateRepository.getRatesByIdClincAndIdExpert(clinic.getId(),item.getId());
				Double countRate = RateFunction.getRateDoctor(rateExperts);
				
				DoctorResponse doctorResponse = new DoctorResponse(item.getId(), item.getCreateAt(), 
						item.getUpdateAt(), item.getCreatedBy(), item.getUpdatedBy(), item.getDeletedBy(),
						item.getFullName(), item.getBirthday(), item.getGender(), item.getAge(), 
						item.getEmail(), item.getAddress(), item.getMobile(), item.getAbout(), 
						item.getFacebook(), new ClinicResponse(clinic), item.getFaculties(), item.getDegrees(),attachmentp,commentExperts.size(),bookingExperts.size(),countRate);
				doctorResponses.add(doctorResponse);
			}
		}
		
		if(clinics == null) {
			return new DataResponse(false, new Data("Không tìm thấy bác sỹ !!",HttpStatus.BAD_REQUEST.value()));
		}
		return new DataResponse(true, new Data("lấy danh sách bác sỹ thành công !!",HttpStatus.OK.value(),doctorResponses));
	}

	@Override
	public DataResponse getAllUser() {
		List<User> users = userRepository.findAllByRole();
		if(users.size() > 0 ) {
			return new DataResponse(true, new Data("lấy danh sách user thành công!!",HttpStatus.OK.value(),users));
		}
		return new DataResponse(false, new Data("lấy danh sách user không thành công!!",HttpStatus.BAD_REQUEST.value()));
	}
}
