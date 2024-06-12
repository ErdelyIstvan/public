package com.ierdely.elective_courses.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseCategoryDTO {

	@NotBlank(message="Name can not be blank.")
	private String category;
	
	@NotBlank(message="Description can not be blank.")
	private String description;
	
	
}
