package com.ierdely.elective_courses.security;

import static com.ierdely.elective_courses.security.SecurityRoles.ADMIN;
import static com.ierdely.elective_courses.security.SecurityRoles.COURSES_PAG_VIEW;
import static com.ierdely.elective_courses.security.SecurityRoles.ENROLLMENTS_PAG_VIEW;
import static com.ierdely.elective_courses.security.SecurityRoles.USERS_PAG_VIEW;
import static com.ierdely.elective_courses.security.SecurityRoles.TEACHERS_PAG_VIEW;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private RoleHierarchy roleHierarchy;
    
//    @Autowired
//    private ECUsersDetailedService usersService;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
//    @Bean
//    ECUsersDetailedService usersService(BCryptPasswordEncoder passwordEncoder) {
//    	return new ECUsersDetailedService(passwordEncoder);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
        http.authorizeHttpRequests(authz -> authz
        		.requestMatchers("/static/favicon.ico").permitAll()
        		.requestMatchers("/", "/home").permitAll()
        		.requestMatchers("/electivecourses/**").hasRole(COURSES_PAG_VIEW)
        		.requestMatchers("/coursecategories/**").hasRole(COURSES_PAG_VIEW)
        		.requestMatchers("/students/**").hasRole(USERS_PAG_VIEW)
        		.requestMatchers("/teachers/**").hasRole(TEACHERS_PAG_VIEW)
        		.requestMatchers("/users/**").hasRole(ADMIN)
        		.requestMatchers("/enrollments/**").hasRole(ENROLLMENTS_PAG_VIEW) 
        		.anyRequest().authenticated()
        	).formLogin( formLogin -> formLogin
            	.loginPage("/login")
              	.failureUrl("/login-error")
            	.permitAll()
            ).logout(logout -> logout
              	.logoutUrl("/logout")
              	.logoutSuccessUrl("/login")
              	.permitAll());
     
        return http.build();
    }
 
    
//    @Bean
//    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
//        
//    	InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//    	//add a sample ADMIN user, so we are not left witout any
//        manager.createUser(User.withUsername("a")
//                .password(bCryptPasswordEncoder.encode("a"))
//                .roles(ADMIN, STUDENT)
//                .build());
        
        
//    	
//    	//add a sample ADMIN user, so we are not left witout any
//        usersService.createUser(User.withUsername("a")
//                .password(bCryptPasswordEncoder.encode("a"))
//                .roles(ADMIN, STUDENT)
//                .build());
//        
//        //add the users from the db
//        List<com.ierdely.elective_courses.entities.User> ecUsers = usersService.getAllUsers();
//        for (com.ierdely.elective_courses.entities.User ecUser : ecUsers) {
//            manager.createUser(User.withUsername(ecUser.getUsername())
//                    .password(bCryptPasswordEncoder.encode(ecUser.getPassword()))
//                    //.roles(ecUser.getRoles()
//                    .build());
//        }
//        
//        
//        return usersService;
//        
//        
//    }
    
    @Bean
    SecurityExpressionHandler<FilterInvocation> expressionHandler() {
        
    	DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }
}
