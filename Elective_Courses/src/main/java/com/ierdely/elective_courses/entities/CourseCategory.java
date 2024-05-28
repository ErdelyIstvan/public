package com.ierdely.elective_courses.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class CourseCategory {

	@Id
	@NotBlank(message="Name can not be blank.")
	private String category;
	
	@NotBlank(message="Description can not be blank.")
	private String description;
	
	public CourseCategory() {
		
	}

	public String getCategory() {
		
		return category;
	}
	
	public void setCategory(String category) {
		
		this.category = category;
	}
	
	public String getDescription() {
		
		return description;
	}
	
	public void setDescription( String description) {
		
		this.description = description;
	}
	
}
