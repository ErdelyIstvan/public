package com.ierdely.elective_courses.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ierdely.elective_courses.dto.CourseCategoryDTO;
import com.ierdely.elective_courses.entities.CourseCategory;
import com.ierdely.elective_courses.repositories.CourseCategoriesRepository;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesCreate;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesDelete;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesRead;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesUpdate;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CourseCategoriesService {

	private CourseCategoriesRepository categoriesRepository;
	
	private ModelMapper modelMapper;
	
	public CourseCategoriesService(CourseCategoriesRepository categoriesRepository,
			ModelMapper modelMapper) {
		
		this.categoriesRepository = categoriesRepository;
		
		this.modelMapper = modelMapper;
	}
	
	
	@IsElectiveCoursesRead
	public List<CourseCategoryDTO> getAllCourseCategories() {
		return categoriesRepository.findAll().stream().map(category -> modelMapper.map(category, CourseCategoryDTO.class))
				.collect(Collectors.toList());
	}
	
	@IsElectiveCoursesRead
	public CourseCategoryDTO getCourseCategory(String categoryName) {
		CourseCategory persistentCourseCategory = categoriesRepository.findByCategory(categoryName)
				.orElseThrow(() -> new EntityNotFoundException("'Course catgory' entity with id: " + categoryName + " not found."));
		
		return modelMapper.map(persistentCourseCategory, CourseCategoryDTO.class);
	}
	
	@IsElectiveCoursesRead
	public boolean doesCourseCategoryExists(String categoryName) {
		Optional<CourseCategory> persistentCourseCategory = categoriesRepository.findByCategory(categoryName);
		
		return persistentCourseCategory.isPresent();
	}
	
	

	@IsElectiveCoursesCreate
	public CourseCategoryDTO saveCourseCategory(CourseCategoryDTO courseCategoryDTO) {
		Optional<CourseCategory> persistentCourseCategory = categoriesRepository.findById(courseCategoryDTO.getCategory());
		if (persistentCourseCategory.isPresent()) {
			throw new DataIntegrityViolationException("'Course catgory' entity with id: " + courseCategoryDTO.getCategory() + " alredy exists.");
		}

		return modelMapper.map(
				categoriesRepository.save(
						modelMapper.map(courseCategoryDTO,  CourseCategory.class)),
				CourseCategoryDTO.class);
	}
	
	@IsElectiveCoursesUpdate
	public CourseCategoryDTO updateCourseCategory(String categoryID, CourseCategoryDTO courseCategoryDTO) {

		CourseCategory persistentCourseCategory = categoriesRepository.findById(categoryID)
				.orElseThrow(() -> new EntityNotFoundException("'Course catgory' entity with id: " + categoryID + " not found."));

		persistentCourseCategory.setDescription(courseCategoryDTO.getDescription());

		return modelMapper.map(
					categoriesRepository.save(persistentCourseCategory), 
				CourseCategoryDTO.class);
	}
	
	@IsElectiveCoursesDelete
	public void deleteCourseCategory(String categoryID) {
		
		CourseCategory persistentCourseCategory = categoriesRepository.findById(categoryID)
				.orElseThrow(() -> new EntityNotFoundException("'Course Category' entity with id: " + categoryID+ " not found."));
		
		categoriesRepository.delete(persistentCourseCategory);
	}
}
