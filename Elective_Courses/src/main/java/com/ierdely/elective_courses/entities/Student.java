package com.ierdely.elective_courses.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="T_STUDENT")
public class Student {
	
	
	@Id
	@Column(name="NAME")
	private String name;
	
	@Column(name="GRADE")
	private float grade;
	
	@Column(name="S_YEAR")
	private byte studyYear;
	
	@Column(name="F_SECTION")
	private String facultySection;

}
