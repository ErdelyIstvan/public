package com.ierdely.elective_courses.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ierdely.elective_courses.entities.Role;
import com.ierdely.elective_courses.entities.User;


public interface UsersRepository extends JpaRepository<User, Long> {
	
    User findByUsername(String username);
    
    Optional<User> findById(Integer id);
    
    List<User> findAllByRolesContaining(Role role);  
    
    List<User> findAllByOrderByRolesAscUsernameAsc();

	//@Query("SELECT u FROM User u WHERE (:studyYear IS NULL OR :studyYear IS 0 OR u.studyYear = :studyYear) order by u.roles, u.username")
    List<User> findAllByStudyYearOrderByRolesAscUsernameAsc(@Param("studyYear") Integer studyYear);
}
