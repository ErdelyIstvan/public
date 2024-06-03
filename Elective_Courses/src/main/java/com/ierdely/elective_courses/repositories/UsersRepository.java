package com.ierdely.elective_courses.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ierdely.elective_courses.entities.Student;
import com.ierdely.elective_courses.entities.User;

public interface UsersRepository extends JpaRepository<User, Integer> {
	//public Optional<User> findByStudent(Student student);
}
