package com.ierdely.elective_courses.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ierdely.elective_courses.entities.ElectiveCourse;
import com.ierdely.elective_courses.entities.Enrollment;
import java.util.List;
import java.util.Optional;

import com.ierdely.elective_courses.entities.User;


@Repository
public interface EnrollmentsRepository extends JpaRepository<Enrollment, Integer> {

	public List<Enrollment> findByUser(User user);
	
	public List<Enrollment> findByCourse(ElectiveCourse course);
	
	public Optional<Enrollment> findByCourseAndUser(ElectiveCourse course, User user);
	
	@Query("SELECT e FROM Enrollment e WHERE (:course IS NULL OR e.course = :course) AND (:user IS NULL OR e.user = :user) AND (:yearOfStudy IS NULL OR e.yearOfStudy = :yearOfStudy) order by e.course.studyYear, e.course.title")
	public List<Enrollment> findByCourseAndUserAndYearOfStudy(@Param("course") ElectiveCourse course,  @Param("user") User user,  @Param("yearOfStudy") Byte yearOfStudy);
	
	public List<Enrollment> findAllByOrderByCourseAsc();
}
