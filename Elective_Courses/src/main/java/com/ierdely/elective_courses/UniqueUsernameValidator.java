package com.ierdely.elective_courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ierdely.elective_courses.services.StudentsService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {


    private StudentsService studentsService;
    
    public UniqueUsernameValidator() {
    	
    }
    
    @Autowired // Constructor injection is preferred for mandatory dependencies
    public void setStudentService(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null 
        		&& studentsService.getUserByUsername(value) == null;
    }
    
}
