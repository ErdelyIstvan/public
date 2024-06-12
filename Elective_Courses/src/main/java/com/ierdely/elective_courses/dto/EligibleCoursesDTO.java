package com.ierdely.elective_courses.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EligibleCoursesDTO {
	
	private List<ElectiveCourseAndErollmentsDTO> courses;
	
	private byte minimumNumberOfEnrollments;

	private byte numberOfDistincCategoryEnrollments;

}
