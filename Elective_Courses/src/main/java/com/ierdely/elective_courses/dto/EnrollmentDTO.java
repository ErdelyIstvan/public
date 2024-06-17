package com.ierdely.elective_courses.dto;

import java.time.LocalDate;

import com.ierdely.elective_courses.enums.CourseEnrollmentStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDTO {
	

	private Integer id;
	
	@NotNull
	private ElectiveCourseDTO course;
	
	@NotNull
	private UserDTO user;
	
	Byte yearOfStudy;

	@NotNull
	private LocalDate enrollmentDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	private CourseEnrollmentStatus enrollmentStatus;
	
}
