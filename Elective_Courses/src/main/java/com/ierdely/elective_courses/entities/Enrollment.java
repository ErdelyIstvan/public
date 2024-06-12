package com.ierdely.elective_courses.entities;

import java.time.LocalDate;

import com.ierdely.elective_courses.enums.CourseEnrollmentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Integer id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "elective_course_id")
	private ElectiveCourse course;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@NotNull
	private LocalDate enrollmentDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	private CourseEnrollmentStatus enrollmentStatus;
	
}
