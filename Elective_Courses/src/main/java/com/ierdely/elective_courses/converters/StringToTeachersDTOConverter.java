package com.ierdely.elective_courses.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ierdely.elective_courses.dto.TeacherDTO;
import com.ierdely.elective_courses.services.TeachersService;

@Component
public class StringToTeachersDTOConverter implements Converter<String, TeacherDTO> {

	@Autowired
	TeachersService teachersService;
	
    @Override
    public TeacherDTO convert(String teacherId) {
    	return teachersService.getTeacher(teacherId);
    }
}