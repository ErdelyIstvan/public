package com.ierdely.elective_courses.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class ElectiveCourse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	int id;
	
	@NotBlank(message = "'Title' can not be blank.")
	private String title;

	@Min(value = 1, message = "'Max Allowed Students' has to be greater then or equal to 1.")
	@Max(value = 999, message = "'Max Allowed Students' as to be less then or equal to 999.")
	private byte maxAllowedStudents;
	
	@Min(value = 1, message = "'Year of study' has to be greater than or equal to 1.")
	@Max(value = 5, message = "'Year of study' has to be smaller than or equal to 5.")
	private byte studyYear;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private CourseCategory category;
	
	@NotBlank(message = "'Teacher' can not be blank.")
	private String teacherName;

	public ElectiveCourse() {
		
	}
	
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte getMaxAllowedStudents() {
		return maxAllowedStudents;
	}

	public void setMaxAllowedStudents(byte maxAllowedStudents) {
		this.maxAllowedStudents = maxAllowedStudents;
	}

	public byte getStudyYear() {
		return studyYear;
	}

	public void setStudyYear(byte studyYear) {
		this.studyYear = studyYear;
	}

	public CourseCategory getCategory() {
		return category;
	}

	public void setCategory(CourseCategory category) {
		this.category = category;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	
}
