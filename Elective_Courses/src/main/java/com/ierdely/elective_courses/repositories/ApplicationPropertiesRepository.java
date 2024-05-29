package com.ierdely.elective_courses.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ierdely.elective_courses.entities.ApplicationProperty;
import com.ierdely.elective_courses.entities.Student;

@Repository
public interface ApplicationPropertiesRepository extends JpaRepository<ApplicationProperty, String> {

}
