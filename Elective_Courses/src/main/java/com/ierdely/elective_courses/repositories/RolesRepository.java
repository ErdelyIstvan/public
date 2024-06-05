package com.ierdely.elective_courses.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ierdely.elective_courses.entities.Role;


public interface RolesRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
