package com.ierdely.elective_courses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectiveCourseAndErollmentsDTO {
	
	private ElectiveCourseDTO course;
	
	private Integer numberOfEnrollments;
	
	private EnrollmentDTO currentUserEnrollment;

}
