package com.ierdely.elective_courses.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ierdely.elective_courses.entities.Role;
import com.ierdely.elective_courses.entities.User;


public interface UsersRepository extends JpaRepository<User, Long> {
	
    User findByUsername(String username);
    
    Optional<User> findById(Integer id);
    
    List<User> findAllByRolesContaining(Role role);    

}
