package com.ierdely.elective_courses.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ierdely.elective_courses.entities.CourseCategory;

public interface CourseCategoriesRepository extends JpaRepository<CourseCategory, String> {

	Optional<CourseCategory> findByCategory(String categoryName);
}
