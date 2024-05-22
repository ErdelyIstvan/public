package com.ierdely.elective_courses.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ierdely.elective_courses.entities.Student;
import com.ierdely.elective_courses.repositories.StudentRepository;

@RestController
public class StudentController {
	
	@Autowired
	private StudentRepository repository;
	
	@GetMapping("students/{studentName}")
	@ResponseBody
	public Student getStudent(@PathVariable String studentName) {
		Optional<Student> response = repository.findById(studentName);
		if(response.isPresent()) {
			return response.get();
			
		}
		return null;
		
		
	}
	@GetMapping("students")
	public List<Student> getStudents() {
		List<Student> responses = repository.findAll();
		if(!responses.isEmpty()) {
			return responses;
			
		}
		return null;
		
	}
}
