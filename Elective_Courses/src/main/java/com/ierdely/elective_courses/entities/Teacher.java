package com.ierdely.elective_courses.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Teacher {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	Integer id;
	
	@NotBlank(message="First name can not be blank.")
    @Pattern(regexp = "[A-Za-z ]*", message = "First name contains illegal characters")
	private String firstName;
	
	@NotBlank(message="Surname can not be blank.")
    @Pattern(regexp = "[A-Za-z ]*", message = "Surname contains illegal characters")
	private String surname;

	@Email(message = "EMail address must be valid.")
	private String email;

}
