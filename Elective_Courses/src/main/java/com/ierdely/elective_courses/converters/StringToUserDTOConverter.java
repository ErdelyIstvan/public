package com.ierdely.elective_courses.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ierdely.elective_courses.dto.UserDTO;
import com.ierdely.elective_courses.services.ECUsersDetailedService;

@Component
public class StringToUserDTOConverter implements Converter<String, UserDTO> {

	@Autowired
	ECUsersDetailedService usersService;
    @Override
    public UserDTO convert(String username) {
    	return usersService.getUser(username);
    }
}