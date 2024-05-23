package com.ierdely.elective_courses.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CourseCategory {

	@Id
	private String category;
	
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
