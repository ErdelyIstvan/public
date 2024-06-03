package com.ierdely.elective_courses.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ierdely.elective_courses.entities.Student;
import com.ierdely.elective_courses.entities.User;
import com.ierdely.elective_courses.repositories.StudentsRepository;
import com.ierdely.elective_courses.repositories.UsersRepository;

@Service
public class StudentsService {

	private StudentsRepository studentsRepository;
	
	//@Autowired //not needed, it is the only consctructor, Spring does the autowireing anyway
	public StudentsService(StudentsRepository studentsRepository) {
		
		this.studentsRepository = studentsRepository;
	}
	
	public Optional<Student> getStudent(User user) {
		
		if (user == null) {
		
			throw new IllegalArgumentException("Can not ask for a Student of a null user object.");
		}
		
		return studentsRepository.findByUser(user);
	}
	
	public Optional<Student> getStudent(Integer id) {
		
		return studentsRepository.findById(id);
	}
	
	public void save(Student student) {
		
		studentsRepository.save(student);
	}

	public List<Student> getAllStudents() {
		
		return studentsRepository.findAll();
	}
	
	public void delete(Student student) {
		
		if (student != null) {
			studentsRepository.deleteById(student.getId());			
		}
	}
}
