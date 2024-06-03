package com.ierdely.elective_courses.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ierdely.elective_courses.entities.Student;
import com.ierdely.elective_courses.entities.User;

@Repository
public interface StudentsRepository extends JpaRepository<Student, Integer> {

	public Optional<Student> findByUser(User user);
	
}
