package com.ierdely.elective_courses.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ierdely.elective_courses.dto.TeacherDTO;
import com.ierdely.elective_courses.entities.Teacher;
import com.ierdely.elective_courses.repositories.TeachersRepository;
import com.ierdely.elective_courses.security.annotations.teachers.IsTeachersCreate;
import com.ierdely.elective_courses.security.annotations.teachers.IsTeachersDelete;
import com.ierdely.elective_courses.security.annotations.teachers.IsTeachersRead;
import com.ierdely.elective_courses.security.annotations.teachers.IsTeachersUpdate;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TeachersService {

	private TeachersRepository teachersRepository;
	
	private ModelMapper modelMapper;
	
	public TeachersService(TeachersRepository teachersRepository,
			ModelMapper modelMapper) {
		
		this.teachersRepository = teachersRepository;
		
		this.modelMapper = modelMapper;
	}
	
	@IsTeachersRead
	public List<TeacherDTO> getAllTeachers() {
		return teachersRepository.findAll().stream().map(teacher -> modelMapper.map(teacher, TeacherDTO.class))
				.collect(Collectors.toList());
	}
	
	@IsTeachersRead
	public TeacherDTO getTeacher(Integer teacherId) {
		
		Teacher persistentTeacher = teachersRepository.findById(teacherId)
				.orElseThrow(() -> new EntityNotFoundException("Teacher with id: " + teacherId + " not found."));

		return modelMapper.map(persistentTeacher, TeacherDTO.class);
	}
	
	@IsTeachersRead
	public TeacherDTO getTeacher(String teacherId) {
		
		return getTeacher(Integer.parseInt(teacherId));
	}
	
	@IsTeachersCreate
	public TeacherDTO saveTeacher(TeacherDTO teacherDTO) {

		return modelMapper.map(
				teachersRepository.save(
						modelMapper.map(teacherDTO,  Teacher.class)),
				TeacherDTO.class);
		
	}
	
	@IsTeachersUpdate
	public TeacherDTO updateTeacher(Integer id, TeacherDTO teacherDTO) {

		Teacher persistentTeacher = teachersRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Teacher entity with id: " + id + " not found."));

		persistentTeacher.setFirstName(teacherDTO.getFirstName());
		persistentTeacher.setSurname(teacherDTO.getSurname());
		persistentTeacher.setEmail(teacherDTO.getEmail());

		return modelMapper.map(
					teachersRepository.save(persistentTeacher), 
				TeacherDTO.class);
	}
	
	@IsTeachersDelete
	public void deleteTeacher(Integer id) {
		
		Teacher persistentTeacher = teachersRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Teacher entity with id: " + id + " not found."));
		
		teachersRepository.delete(persistentTeacher);
	}
}
