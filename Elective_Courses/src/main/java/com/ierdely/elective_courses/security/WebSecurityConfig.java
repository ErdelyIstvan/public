package com.ierdely.elective_courses.security;

import static com.ierdely.elective_courses.security.SecurityRoles.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private RoleHierarchy roleHierarchy;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
        http.authorizeHttpRequests(authz -> authz
        		
        		.requestMatchers("/", "/home").permitAll()
        		.requestMatchers("/electivecourses/**").hasRole(COURSES_PAG_VIEW)
        		.requestMatchers("/students/**").hasRole(STUDENTS_PAG_VIEW)
        		.requestMatchers("/results/**").hasRole(RESULTS_PAG_VIEW)
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
 
    
    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        
    	InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("student")
                .password(bCryptPasswordEncoder.encode("stud"))
                .roles(STUDENT)
                .build());
        manager.createUser(User.withUsername("admin")
                .password(bCryptPasswordEncoder.encode("adminPass"))
                .roles(STUDENT, ADMIN)
                .build());
        return manager;
    }
    
    @Bean
    SecurityExpressionHandler<FilterInvocation> expressionHandler() {
        
    	DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }
}
