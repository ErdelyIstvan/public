package com.ierdely.elective_courses.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ierdely.elective_courses.dto.CourseCategoryDTO;
import com.ierdely.elective_courses.services.CourseCategoriesService;

@Component
public class StringToCourseCategoryDTOConverter implements Converter<String, CourseCategoryDTO> {

	@Autowired
	CourseCategoriesService categoriesService;
	
    @Override
    public CourseCategoryDTO convert(String categoryName) {
    	return categoriesService.getCourseCategory(categoryName);
    }
}