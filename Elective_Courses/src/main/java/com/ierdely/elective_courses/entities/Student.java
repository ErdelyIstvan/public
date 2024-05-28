package com.ierdely.elective_courses.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Entity
@Data
public class Student {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	int id;
	
	@NotBlank(message="Name can not be blank.")
    @Pattern(regexp = "[A-Za-z ]*", message = "Name contains illegal characters")
	private String name;
	
	@Min(value = 1, message = "Grade has to greater or equal than 1.")
	@Max(value = 10, message = "Grade has to be smaller or equal than 10.")
	private float grade;
	
	@Min(value = 1, message = "'Year of study' has to be greater than or equal to 1.")
	@Max(value = 5, message = "'Year of study' has to be smaller than or equal to 5.")
	private byte studyYear;
	
	@Pattern(regexp = "[A-Za-z ]*", message = "Faculty Section contains illegal characters")
	private String facultySection;

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

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	

}
