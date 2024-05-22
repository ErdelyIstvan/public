package com.ierdely.elective_courses.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ierdely.elective_courses.entities.ElectiveCourse;

public interface ElectiveCourseRepository extends JpaRepository<ElectiveCourse, Integer> {

}
