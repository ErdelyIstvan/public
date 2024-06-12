package com.ierdely.elective_courses.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
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
	
	public List<Enrollment> findAllByOrderByCourseAsc();
}
