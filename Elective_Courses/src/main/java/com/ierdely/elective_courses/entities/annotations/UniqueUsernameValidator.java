package com.ierdely.elective_courses.entities.annotations;

import org.springframework.beans.factory.annotation.Autowired;
import com.ierdely.elective_courses.repositories.UsersRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null 
        		&& usersRepository.findByUsername(value) == null;
    }
    
}
