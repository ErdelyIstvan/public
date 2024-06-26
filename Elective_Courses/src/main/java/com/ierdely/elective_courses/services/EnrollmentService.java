package com.ierdely.elective_courses.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ierdely.elective_courses.dto.ElectiveCourseDTO;
import com.ierdely.elective_courses.dto.EnrollmentDTO;
import com.ierdely.elective_courses.dto.UserDTO;
import com.ierdely.elective_courses.entities.ElectiveCourse;
import com.ierdely.elective_courses.entities.Enrollment;
import com.ierdely.elective_courses.entities.User;
import com.ierdely.elective_courses.repositories.ElectiveCoursesRepository;
import com.ierdely.elective_courses.repositories.EnrollmentsRepository;
import com.ierdely.elective_courses.repositories.UsersRepository;
import com.ierdely.elective_courses.security.annotations.enrollments.IsEnrollmentsCreate;
import com.ierdely.elective_courses.security.annotations.enrollments.IsEnrollmentsDelete;
import com.ierdely.elective_courses.security.annotations.enrollments.IsEnrollmentsUpdate;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EnrollmentService {

	private EnrollmentsRepository enrollmentsRepository;
	
	private ElectiveCoursesRepository electiveCoursesRepository;
	
	private UsersRepository usersRepository;
	
	private ModelMapper modelMapper;
	
	@Autowired
	public EnrollmentService(EnrollmentsRepository enrollmentsRepository, 
			ElectiveCoursesRepository electiveCoursesRepository,
			UsersRepository usersRepository,
			ModelMapper modelMapper) {
		
		this.enrollmentsRepository = enrollmentsRepository;
		this.electiveCoursesRepository = electiveCoursesRepository;
		this.usersRepository = usersRepository;
		this.modelMapper = modelMapper;
	}
	
	public List<EnrollmentDTO> getEnrollments(UserDTO userDTO) {
		return enrollmentsRepository.findByUser(modelMapper.map(userDTO, User.class))
				.stream().map(enrollment -> modelMapper.map(enrollment, EnrollmentDTO.class))
				.collect(Collectors.toList());
		
	}
	public List<EnrollmentDTO> getEnrollments(ElectiveCourseDTO courseDTO) {
		return enrollmentsRepository.findByCourse(modelMapper.map(courseDTO, ElectiveCourse.class))
				.stream().map(enrollment -> modelMapper.map(enrollment, EnrollmentDTO.class))
				.collect(Collectors.toList());	
	}
	
	public List<EnrollmentDTO> getEnrollments(Integer courseId, Integer userID, Byte year) {
		User persistentUser = null;
		if (userID != null) {
			persistentUser = usersRepository.findById(userID)
					.orElseThrow(() -> new EntityNotFoundException("User entity with id: " + userID + " not found."));
		}
		ElectiveCourse persistenCourse = null;
		if (courseId != null) {
			persistenCourse = electiveCoursesRepository.findById(courseId)
					.orElseThrow(() -> new EntityNotFoundException("UElective Course entity with id: " + courseId + " not found."));
		}
		return enrollmentsRepository.findByCourseAndUserAndYearOfStudy(persistenCourse, persistentUser, year)
				.stream().map(enrollment -> modelMapper.map(enrollment, EnrollmentDTO.class))
				.collect(Collectors.toList());	
	}
	
	public List<EnrollmentDTO> getEnrollments(Integer courseId, Integer userID) {
		User persistentUser = null;
		if (userID != null) {
			persistentUser = usersRepository.findById(userID)
					.orElseThrow(() -> new EntityNotFoundException("User entity with id: " + userID + " not found."));
		}
		ElectiveCourse persistenCourse = null;
		if (courseId != null) {
			persistenCourse = electiveCoursesRepository.findById(courseId)
					.orElseThrow(() -> new EntityNotFoundException("UElective Course entity with id: " + courseId + " not found."));
		}
		return enrollmentsRepository.findByCourseAndUser(persistenCourse, persistentUser)
				.stream().map(enrollment -> modelMapper.map(enrollment, EnrollmentDTO.class))
				.collect(Collectors.toList());	
	}
	
	public List<UserDTO> getPears(UserDTO userDTO) {
		List<UserDTO> usersRet = new ArrayList<UserDTO>();
		for (Enrollment enrollment : enrollmentsRepository.findByUser(modelMapper.map(userDTO, User.class))) {
			for (Enrollment e2 : enrollmentsRepository.findByCourse(enrollment.getCourse())) {
				usersRet.add(modelMapper.map(e2.getUser(), UserDTO.class));
			}
		}
		return usersRet;
	}
	
	public Set<UserDTO> getPears2(UserDTO userDTO) {
	    Set<UserDTO> uniqueUsers = new HashSet<>();
	    for (Enrollment enrollment : enrollmentsRepository.findByUser(modelMapper.map(userDTO, User.class))) {
	        uniqueUsers.addAll(
	            enrollmentsRepository.findByCourse(enrollment.getCourse())
	                .stream()
	                .map(e -> modelMapper.map(e.getUser(), UserDTO.class)) 
	                .collect(Collectors.toSet()) // Collect as a Set to maintain uniqueness
	        );
	    }
	    return uniqueUsers; 
	}
	
	public List<EnrollmentDTO> getAllEnrollments() {
		
		return enrollmentsRepository.findAllByOrderByCourseAsc()
				.stream().map(enrollment -> modelMapper.map(enrollment, EnrollmentDTO.class))
				.collect(Collectors.toList());
	}
	
	@IsEnrollmentsCreate
	public EnrollmentDTO saveEnrollment(EnrollmentDTO enrollmentDTO) {
		
		Enrollment enrollmentToSave = modelMapper.map(enrollmentDTO, Enrollment.class);
		
		if (enrollmentsRepository.findByCourseAndUser(enrollmentToSave.getCourse(), enrollmentToSave.getUser()).isPresent()) {
			throw new IllegalArgumentException("User: " + enrollmentToSave.getUser().getFirstName() 
					+ " " + enrollmentToSave.getUser().getSurname() 
					+ " is already enrolled in the course: " + enrollmentToSave.getCourse().getTitle());
		}
		Enrollment savedEnrollment = enrollmentsRepository.save(enrollmentToSave);
		
		
		return modelMapper.map(
				savedEnrollment, EnrollmentDTO.class);
	}
	
	@IsEnrollmentsUpdate
	@Transactional
	public EnrollmentDTO updateEnrollment(Integer id, EnrollmentDTO enrollmentDTO) {

		Enrollment persistentEnrollment = enrollmentsRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Enrollment entity with id: " + id + " not found."));

		persistentEnrollment.setEnrollmentStatus(enrollmentDTO.getEnrollmentStatus());
		persistentEnrollment.setEnrollmentDate(enrollmentDTO.getEnrollmentDate());
		persistentEnrollment.setUser(modelMapper.map(enrollmentDTO.getUser(), User.class));
		persistentEnrollment.setCourse(modelMapper.map(enrollmentDTO.getCourse(), ElectiveCourse.class));
		
		return modelMapper.map(enrollmentsRepository.save(persistentEnrollment), EnrollmentDTO.class);
	}
	
	@IsEnrollmentsDelete
	public void deleteEnrollment(Integer id) {

		Enrollment persistentEnrollment = enrollmentsRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Enrollment entity with id: " + id + " not found."));

		enrollmentsRepository.delete(persistentEnrollment);
	}
}
