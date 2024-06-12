package com.ierdely.elective_courses.dto;

import com.ierdely.elective_courses.entities.User;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Integer id;
	
	@NotBlank(message="First name can not be blank.")
    @Pattern(regexp = "[A-Za-z ]*", message = "First name contains illegal characters")
	private String firstName;
	
	@NotBlank(message="Surname can not be blank.")
    @Pattern(regexp = "[A-Za-z ]*", message = "Surname contains illegal characters")
	private String surname;
	
	@Min(value = 1, message = "Grade has to greater or equal than 1.")
	@Max(value = 10, message = "Grade has to be smaller or equal than 10.")
	private float grade;
	
	@Max(value = 5, message = "'Year of study' has to be smaller than or equal to 5.")
	private byte studyYear;
	
	@Pattern(regexp = "[A-Za-z ]*", message = "Faculty Section contains illegal characters")
	private String facultySection;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="user_id")
	private UserDTO userDto;

}
