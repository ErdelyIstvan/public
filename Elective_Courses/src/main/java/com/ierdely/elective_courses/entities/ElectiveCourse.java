package com.ierdely.elective_courses.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="E_COURSES")
public class ElectiveCourse {
	
	@GeneratedValue
	Long id;

	byte max_allowed_students;
	
	byte year_of_study;
	
	@ManyToOne(fetch = FetchType.LAZY)
	CourseCathegory category;// (e.g. FE, BE, DevOps)
	
	long teacher_id;

	
}
