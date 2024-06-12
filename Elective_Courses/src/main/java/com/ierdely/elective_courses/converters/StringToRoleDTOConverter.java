package com.ierdely.elective_courses.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ierdely.elective_courses.dto.RoleDTO;
import com.ierdely.elective_courses.dto.UserDTO;
import com.ierdely.elective_courses.services.ECUsersDetailedService;

@Component
public class StringToRoleDTOConverter implements Converter<String, RoleDTO> {

	@Autowired
	ECUsersDetailedService usersService;
    @Override
    public RoleDTO convert(String roleName) {
    	return usersService.getRole(roleName);
    }
}