package com.ierdely.elective_courses.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="C_CAT")
public class CourseCathegory {

	@Id
	String cathegory;
	
	String description;
}
