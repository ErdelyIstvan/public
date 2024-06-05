package com.ierdely.elective_courses;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ierdely.elective_courses.entities.Role;
import com.ierdely.elective_courses.entities.User;
import com.ierdely.elective_courses.repositories.RolesRepository;
import com.ierdely.elective_courses.repositories.UsersRepository;

import jakarta.transaction.Transactional;

@Component
public class SetupDataLoader implements
  ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UsersRepository userRepository;
 
    @Autowired
    private RolesRepository roleRepository;
 
 
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
 
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
 
        if (alreadySetup)
            return;
        
        User admin = userRepository.findByUsername("ADMIN");
        if(admin != null) {
        	return;
        }
      
 
       
        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_STUDENT");

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setUsername("ADMIN");
        user.setPassword(passwordEncoder.encode("A"));
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user);

        alreadySetup = true;
    }


    @Transactional
    Role createRoleIfNotFound(
      String name) {
 
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            
            roleRepository.save(role);
        }
        return role;
    }
}