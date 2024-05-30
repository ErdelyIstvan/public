package com.ierdely.elective_courses.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ierdely.elective_courses.entities.Enrollment;

@Repository
public interface EnrollmentsRepository extends JpaRepository<Enrollment, Integer> {

}
