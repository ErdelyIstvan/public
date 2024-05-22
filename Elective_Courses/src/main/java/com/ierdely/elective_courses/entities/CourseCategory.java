package com.ierdely.elective_courses.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="T_C_CATEG")
public class CourseCategory {

	@Id
	@Column(name="ID")
	private String category;
	
	@Column(name="DESCR")
	private String description;
	
	public String getCategory() {
		
		return category;
	}
	
	public String getDescription() {
		
		return description;
	}
	
	public void setDescription( String description) {
		
		this.description = description;
	}
}
