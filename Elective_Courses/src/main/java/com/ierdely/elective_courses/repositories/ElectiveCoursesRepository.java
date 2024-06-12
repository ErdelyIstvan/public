package com.ierdely.elective_courses.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ierdely.elective_courses.entities.ElectiveCourse;

public interface ElectiveCoursesRepository extends JpaRepository<ElectiveCourse, Integer> {
	List<ElectiveCourse> findAllByStudyYear(byte studyYear);

}
