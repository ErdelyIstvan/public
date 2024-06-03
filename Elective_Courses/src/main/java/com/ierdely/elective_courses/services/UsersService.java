package com.ierdely.elective_courses.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ierdely.elective_courses.entities.Student;
import com.ierdely.elective_courses.entities.User;
import com.ierdely.elective_courses.repositories.UsersRepository;

@Service
public class UsersService {

	private UsersRepository usersRepository;
	
	//@Autowired //not needed, it is the only consctructor, Spring does the autowireing anyway
	public UsersService(UsersRepository usersRepository) {
		
		this.usersRepository = usersRepository;
	}
	
//	public Optional<User> getUser(Student student) {
//		
//		if (student == null) {
//		
//			throw new IllegalArgumentException("Can not ask for the user of a null student object.");
//		}
//		
//		return usersRepository.findByStudent(student);
//	}
	
	public Optional<User> getUser(Integer id) {
		
		return usersRepository.findById(id);
	}
	
	public List<User> getAllUsers() {
		
		return usersRepository.findAll();
	}
}
