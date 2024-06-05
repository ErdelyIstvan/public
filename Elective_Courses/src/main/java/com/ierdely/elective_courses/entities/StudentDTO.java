package com.ierdely.elective_courses.entities;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public class StudentDTO {
	
	private Integer id;
	
	@NotBlank(message="First name can not be blank.")
    @Pattern(regexp = "[A-Za-z ]*", message = "First name contains illegal characters")
	private String firstName;
	
	@NotBlank(message="Surname can not be blank.")
    @Pattern(regexp = "[A-Za-z ]*", message = "Surname contains illegal characters")
	private String surname;
	
	@Min(value = 1, message = "Grade has to greater or equal than 1.")
	@Max(value = 10, message = "Grade has to be smaller or equal than 10.")
	private float grade;
	
	@Min(value = 1, message = "'Year of study' has to be greater than or equal to 1.")
	@Max(value = 5, message = "'Year of study' has to be smaller than or equal to 5.")
	private byte studyYear;
	
	@Pattern(regexp = "[A-Za-z ]*", message = "Faculty Section contains illegal characters")
	private String facultySection;
	
	@NotNull
	private UserDTO userDTO;
	
	public StudentDTO() {
		
	}
	
	public StudentDTO(Student student) {
		this.id = student.getId();
		this.firstName = student.getFirstName();
		this.surname = student.getSurname();
		this.grade = student.getGrade();
		this.studyYear = student.getStudyYear();
		this.facultySection = student.getFacultySection();
		this.userDTO = new UserDTO(student.getUser());
	}
	
	public Integer getId() {
		
		return id;
	}
	
	public void setId(Integer id) {
		
		this.id = id;
	}

	public String getFirstName() {

		return firstName;
	}
	
	public void setFirstName(String firstName) {
		
		this.firstName = firstName;
	}
	
	public String getSurname() {
	
		return surname;
	}
	
	public void setSurname(String surname) {
	
		this.surname = surname;
	}

	public float getGrade() {
		
		return grade;
	}

	public void setGrade(float grade) {
		
		this.grade = grade;
	}

	public byte getStudyYear() {
		
		return studyYear;
	}

	public void setStudyYear(byte studyYear) {
		
		this.studyYear = studyYear;
	}

	public String getFacultySection() {
		
		return facultySection;
	}

	public void setFacultySection(String facultySection) {
		
		this.facultySection = facultySection;
	}
	
	public UserDTO getUserDTO() {
		
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		
		this.userDTO = userDTO;
	}

}
