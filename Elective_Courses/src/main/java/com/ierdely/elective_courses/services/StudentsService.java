package com.ierdely.elective_courses.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ierdely.elective_courses.entities.Student;
import com.ierdely.elective_courses.entities.StudentDTO;
import com.ierdely.elective_courses.entities.User;
import com.ierdely.elective_courses.repositories.StudentsRepository;

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
	
	public Optional<StudentDTO> getStudent(Integer id) {
		
		Optional<Student> studentOp = studentsRepository.findById(id);
		if (studentOp.isPresent()) {
			return Optional.of(new StudentDTO(studentOp.get()));
		}
		return Optional.empty();
		
	}
	
	public void save(StudentDTO studentDTO) {
		
		Student toSave;
		if (studentDTO.getId() == null) {
			toSave = new Student(studentDTO);
		} else {
			Optional<Student> toSaveOptional = studentsRepository.findById(studentDTO.getId());
			if (toSaveOptional.isEmpty()) {
				throw new RuntimeException("Student with id: " + studentDTO.getId() + " can not be found!");
			} else {
				toSave = toSaveOptional.get();
				toSave.copyFrom(studentDTO);
			}
		}
		studentsRepository.save(toSave);
	}
	

	public List<StudentDTO> getAllStudents() {
		
		List<Student>  students = studentsRepository.findAll();
		List<StudentDTO> studentsRet = new ArrayList<StudentDTO>();
		for (Student student : students) {
			studentsRet.add(new StudentDTO(student));
		}
		return studentsRet;
	}
	
	public void delete(StudentDTO studentDTO) {
		
		if (studentDTO != null) {
			studentsRepository.deleteById(studentDTO.getId());			
		}
	}
}
