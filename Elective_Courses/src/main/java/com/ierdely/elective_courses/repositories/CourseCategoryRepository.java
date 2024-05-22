package com.ierdely.elective_courses.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ierdely.elective_courses.entities.CourseCategory;

public interface CourseCategoryRepository extends JpaRepository<CourseCategory, String> {

}
