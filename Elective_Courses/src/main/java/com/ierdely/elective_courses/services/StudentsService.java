package com.ierdely.elective_courses.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ierdely.elective_courses.dto.StudentDTO;
import com.ierdely.elective_courses.dto.UserDTO;
import com.ierdely.elective_courses.entities.Role;
import com.ierdely.elective_courses.entities.Student;
import com.ierdely.elective_courses.entities.User;
import com.ierdely.elective_courses.repositories.RolesRepository;
import com.ierdely.elective_courses.repositories.StudentsRepository;
import com.ierdely.elective_courses.repositories.UsersRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class StudentsService {

	private StudentsRepository studentsRepository;
	
	private UsersRepository usersRepository;
	
	private RolesRepository rolesRepository;
	
	private ModelMapper modelMapper;
	
    private BCryptPasswordEncoder passwordEncoder;
    
	
	//@Autowired //not needed, it is the only consctructor, Spring does the autowireing anyway
	public StudentsService(StudentsRepository studentsRepository, 
			UsersRepository usersRepository, 
			RolesRepository roleRepository,
			ModelMapper modelMapper, 
			BCryptPasswordEncoder passwordEncoder) {
		
		this.studentsRepository = studentsRepository;
		this.usersRepository = usersRepository;
		this.rolesRepository = roleRepository;
		
		this.modelMapper = modelMapper;
		
		this.passwordEncoder = passwordEncoder;
	}
	
	
	public User getUserByUsername(String username) {
		return usersRepository.findByUsername(username);
	}
	
	
	public StudentDTO getStudent(Integer studentId) {
		
		Student student = studentsRepository.findById(studentId)
				.orElseThrow(() -> new EntityNotFoundException("Student entity with id: " + studentId + " not found."));

		return modelMapper.map(student, StudentDTO.class);
	}
	
	@Transactional
	public StudentDTO save(StudentDTO studentDTO) {
		
		Student studentToSave = modelMapper.map(studentDTO, Student.class);
		Student savedStudent = studentsRepository.save(studentToSave);
		
		
		return modelMapper.map(
				savedStudent, StudentDTO.class);
	}
	
	
	public StudentDTO updateStudent(Integer id, StudentDTO studentDTO) {

		Student persistentStudent = studentsRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Student entity with id: " + id + " not found."));
//
//		persistentStudent.setFirstName(studentDTO.getFirstName());
//		persistentStudent.setSurname(studentDTO.getSurname());
//		persistentStudent.setFacultySection(studentDTO.getFacultySection());
//		persistentStudent.setGrade(studentDTO.getGrade());
//		persistentStudent.setStudyYear(studentDTO.getStudyYear());
//		
//		
//		//User user = usersRepository.findByUsername(studentDTO.getUsername());
//		persistentStudent.setUser(modelMapper.map(studentDTO.getUserDto(), User.class));
//		

		return modelMapper.map(studentsRepository.save(persistentStudent), StudentDTO.class);
	}
	
	

	public List<StudentDTO> getAllStudents() {
		
		return studentsRepository.findAll().stream().map(student -> modelMapper.map(student, StudentDTO.class))
				.collect(Collectors.toList());
	}
	
	public void delete(Integer studentId) {
	
		Student student = studentsRepository.findById(studentId)
				.orElseThrow(() -> new EntityNotFoundException("Student entity with id: " + studentId + " not found."));
		studentsRepository.delete(student);			
	}
}
