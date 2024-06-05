package com.ierdely.elective_courses.entities;

import java.time.LocalDate;

import com.ierdely.elective_courses.enums.CourseEnrollmentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor

public class Enrollment {
	
	@Id
	private Integer id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "elective_course_id")
	private ElectiveCourse course;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;

	@NotNull
	private LocalDate enrollmentDate;

	@NotBlank
	@Enumerated(EnumType.STRING)
	private CourseEnrollmentStatus enrollment_status;
	
}
