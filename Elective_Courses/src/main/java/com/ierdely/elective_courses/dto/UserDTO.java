package com.ierdely.elective_courses.dto;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {


	private Integer id;

	@Column(name="username", nullable = false, unique = true)
	@NotBlank
    private String username;
	
	@NotBlank
	private String password;
	
	
    private Collection<RoleDTO> roles;
    
    @NotBlank(message="First name can not be blank.")
    @Pattern(regexp = "[A-Za-z0-9 ]*", message = "First name contains illegal characters")
	private String firstName;
	
	@NotBlank(message="Surname can not be blank.")
    @Pattern(regexp = "[A-Za-z0-9 ]*", message = "Surname contains illegal characters")
	private String surname;
	
	@Min(value = 1, message = "Grade has to greater or equal than 1.")
	@Max(value = 10, message = "Grade has to be smaller or equal than 10.")
	private float grade;
	
	@Min(value = 1, message = "'Year of study' has to be greater than or equal to 1.")
	@Max(value = 5, message = "'Year of study' has to be smaller than or equal to 5.")
	private Byte studyYear;
	
	@Pattern(regexp = "[A-Za-z0-9 ]*", message = "Faculty Section contains illegal characters")
	private String facultySection;
	
}
