package com.ierdely.elective_courses.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ierdely.elective_courses.entities.User;
import com.ierdely.elective_courses.entities.UserDTO;
import com.ierdely.elective_courses.repositories.UsersRepository;

@Service
public class UsersService {

	private UsersRepository usersRepository;
	
	public UsersService(UsersRepository usersRepository) {
		
		this.usersRepository = usersRepository;
	}
	
	public List<UserDTO> getUsers() {
		List<User> users = usersRepository.findAll();
		List<UserDTO> usersRet = new ArrayList<UserDTO>();
		for (User user : users) {
			usersRet.add(new UserDTO(user));
		}
		return usersRet;
	}
}
