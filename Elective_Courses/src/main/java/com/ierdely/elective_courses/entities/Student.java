package com.ierdely.elective_courses.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
import lombok.Data;


@Entity
@Data
public class Student {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Integer id;
	
//	@NotBlank(message="First name can not be blank.")
//    @Pattern(regexp = "[A-Za-z ]*", message = "First name contains illegal characters")
//	private String firstName;
//	
//	@NotBlank(message="Surname can not be blank.")
//    @Pattern(regexp = "[A-Za-z ]*", message = "Surname contains illegal characters")
//	private String surname;
//	
//	@Min(value = 1, message = "Grade has to greater or equal than 1.")
//	@Max(value = 10, message = "Grade has to be smaller or equal than 10.")
//	private float grade;
//	
//	@Min(value = 1, message = "'Year of study' has to be greater than or equal to 1.")
//	@Max(value = 5, message = "'Year of study' has to be smaller than or equal to 5.")
//	private byte studyYear;
//	
//	@Pattern(regexp = "[A-Za-z ]*", message = "Faculty Section contains illegal characters")
//	private String facultySection;
//	
//	@NotNull
//	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinColumn(name ="user_id", referencedColumnName = "id")
//	private User user;
//	
}
