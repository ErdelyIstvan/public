package com.ierdely.elective_courses.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ierdely.elective_courses.dto.ElectiveCourseAndErollmentsDTO;
import com.ierdely.elective_courses.dto.ElectiveCourseDTO;
import com.ierdely.elective_courses.dto.EligibleCoursesDTO;
import com.ierdely.elective_courses.dto.EnrollmentDTO;
import com.ierdely.elective_courses.dto.UserDTO;
import com.ierdely.elective_courses.entities.CourseCategory;
import com.ierdely.elective_courses.entities.ElectiveCourse;
import com.ierdely.elective_courses.entities.Enrollment;
import com.ierdely.elective_courses.entities.Teacher;
import com.ierdely.elective_courses.repositories.ElectiveCoursesRepository;
import com.ierdely.elective_courses.repositories.EnrollmentsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ElectiveCoursesService {

	private ElectiveCoursesRepository electiveCoursesRepository;
	
	private EnrollmentsRepository enrollmentsRepository;
	
	private ApplicationPropertiesService appPropService;
	
	private ModelMapper modelMapper;
	
	@Autowired
	public ElectiveCoursesService(ElectiveCoursesRepository electiveCoursesRepository, 
			EnrollmentsRepository enrollmentsRepository, 
			ApplicationPropertiesService appPropService,
			ModelMapper modelMapper) {
		
		this.electiveCoursesRepository = electiveCoursesRepository;
		
		this.enrollmentsRepository = enrollmentsRepository;
		
		this.appPropService = appPropService;
		
		this.modelMapper = modelMapper;
	}
	
	public ElectiveCourseDTO getElecticeCourse(Integer electiveCourseId) {
		
		ElectiveCourse persistentCourse = electiveCoursesRepository.findById(electiveCourseId)
				.orElseThrow(() -> new EntityNotFoundException("Elective Course entity with id: " + electiveCourseId + " not found."));

		return modelMapper.map(persistentCourse, ElectiveCourseDTO.class);
	}

	public List<ElectiveCourseDTO> getAllElectiveCourses() {
		
		return electiveCoursesRepository.findAll()
				.stream().map(electiveCourse -> modelMapper.map(electiveCourse, ElectiveCourseDTO.class))
				.collect(Collectors.toList());
	}
	
	public List<ElectiveCourseDTO> getAllByStudyYear(byte studyYear) {
	
		return electiveCoursesRepository.findAllByStudyYear(studyYear)
				.stream().map(electiveCourse -> modelMapper.map(electiveCourse, ElectiveCourseDTO.class))
				.collect(Collectors.toList());
	}
	
	public EligibleCoursesDTO getEligibleCoursesData(UserDTO loggedInUser) {
		
		List<ElectiveCourse> enrolledCourses = new ArrayList<>();
		List<CourseCategory> enrolledCategories = new ArrayList<>();
		
		EligibleCoursesDTO ret = new EligibleCoursesDTO();
		
		List<ElectiveCourseAndErollmentsDTO> eligibleCourseDTOs = new ArrayList<>();
		for (ElectiveCourse course : electiveCoursesRepository.findAllByStudyYear(loggedInUser.getStudyYear())) {
			EnrollmentDTO userEnrollment = null;
			List<Enrollment> courseEnrollments = enrollmentsRepository.findByCourse(course);
			for (Enrollment enrollment :  courseEnrollments) {
				if (enrollment.getUser().getUsername().equals(loggedInUser.getUsername())) {
					userEnrollment = modelMapper.map(enrollment,  EnrollmentDTO.class);
					enrolledCourses.add(course);
					if (!enrolledCategories.contains(course.getCategory())) {
						enrolledCategories.add(course.getCategory());
					}
				}
			}
			ElectiveCourseAndErollmentsDTO eligibleCourseDTO = new ElectiveCourseAndErollmentsDTO(
					modelMapper.map(course, ElectiveCourseDTO.class), 
					courseEnrollments.size(), 
					userEnrollment);
			
			eligibleCourseDTOs.add(eligibleCourseDTO);
		}
		ret.setCourses(eligibleCourseDTOs);
		
		//read the minimum enrollments for student's year
		byte minimumCoursesToEnroll = appPropService.getMinimumElectivesForYearOfStudy(loggedInUser.getStudyYear());
		ret.setMinimumNumberOfEnrollments(minimumCoursesToEnroll);
		ret.setNumberOfDistincCategoryEnrollments((byte)enrolledCategories.size());
				
		return ret;
	}
	public ElectiveCourseDTO saveElectiveCourse(ElectiveCourseDTO electiveCourseDTO) {
		
		ElectiveCourse courseToSave = modelMapper.map(electiveCourseDTO, ElectiveCourse.class);
		ElectiveCourse savedCourse = electiveCoursesRepository.save(courseToSave);
		
		
		return modelMapper.map(
				savedCourse, ElectiveCourseDTO.class);
	}
	
	@Transactional
	public ElectiveCourseDTO updateElectiveCourse(Integer id, ElectiveCourseDTO electiveCourseDTO) {

		ElectiveCourse persistentCourse = electiveCoursesRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Elective Course entity with id: " + id + " not found."));

		persistentCourse.setTitle(electiveCourseDTO.getTitle());
		persistentCourse.setCategory(modelMapper.map(electiveCourseDTO.getCategory(), CourseCategory.class));
		persistentCourse.setMaxAllowedStudents(electiveCourseDTO.getMaxAllowedStudents());
		persistentCourse.setStudyYear(electiveCourseDTO.getStudyYear());
		persistentCourse.setTeacher(modelMapper.map(electiveCourseDTO.getTeacher(),  Teacher.class));
		
		return modelMapper.map(electiveCoursesRepository.save(persistentCourse), ElectiveCourseDTO.class);
	}
	
	public void deleteElectiveCourse(Integer id) {

		ElectiveCourse persistentCourse = electiveCoursesRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Elective Course entity with id: " + id + " not found."));

		electiveCoursesRepository.delete(persistentCourse);
	}
}
