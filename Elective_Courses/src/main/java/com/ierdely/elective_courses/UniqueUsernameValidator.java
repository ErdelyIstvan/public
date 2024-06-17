package com.ierdely.elective_courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ierdely.elective_courses.services.ECUsersDetailedService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {


    private ECUsersDetailedService usersService;
    
    public UniqueUsernameValidator() {
    	
    }
    
    @Autowired // Constructor injection is preferred for mandatory dependencies
    public void setStudentService(ECUsersDetailedService studentsService) {
        this.usersService = studentsService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null 
        		&& usersService.getUser(value) == null;
    }
    
}
