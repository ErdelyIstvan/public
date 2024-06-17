package com.ierdely.elective_courses.security;

import static com.ierdely.elective_courses.security.SecurityRoles.ADMIN;
import static com.ierdely.elective_courses.security.SecurityRoles.COURSES_ADMIN;
import static com.ierdely.elective_courses.security.SecurityRoles.COURSES_CREATE;
import static com.ierdely.elective_courses.security.SecurityRoles.COURSES_DELETE;
import static com.ierdely.elective_courses.security.SecurityRoles.COURSES_PAG_VIEW;
import static com.ierdely.elective_courses.security.SecurityRoles.COURSES_READ;
import static com.ierdely.elective_courses.security.SecurityRoles.COURSES_UPDATE;
import static com.ierdely.elective_courses.security.SecurityRoles.ENROLLMENTS_ADMIN;
import static com.ierdely.elective_courses.security.SecurityRoles.ENROLLMENTS_CREATE;
import static com.ierdely.elective_courses.security.SecurityRoles.ENROLLMENTS_DELETE;
import static com.ierdely.elective_courses.security.SecurityRoles.ENROLLMENTS_PAG_VIEW;
import static com.ierdely.elective_courses.security.SecurityRoles.ENROLLMENTS_READ;
import static com.ierdely.elective_courses.security.SecurityRoles.ENROLLMENTS_UPDATE;
import static com.ierdely.elective_courses.security.SecurityRoles.STUDENT;
import static com.ierdely.elective_courses.security.SecurityRoles.USERS_ADMIN;
import static com.ierdely.elective_courses.security.SecurityRoles.USERS_CREATE;
import static com.ierdely.elective_courses.security.SecurityRoles.USERS_DELETE;
import static com.ierdely.elective_courses.security.SecurityRoles.USERS_PAG_VIEW;
import static com.ierdely.elective_courses.security.SecurityRoles.USERS_READ;
import static com.ierdely.elective_courses.security.SecurityRoles.USERS_UPDATE;
import static com.ierdely.elective_courses.security.SecurityRoles.TEACHERS_ADMIN;
import static com.ierdely.elective_courses.security.SecurityRoles.TEACHERS_CREATE;
import static com.ierdely.elective_courses.security.SecurityRoles.TEACHERS_DELETE;
import static com.ierdely.elective_courses.security.SecurityRoles.TEACHERS_PAG_VIEW;
import static com.ierdely.elective_courses.security.SecurityRoles.TEACHERS_READ;
import static com.ierdely.elective_courses.security.SecurityRoles.TEACHERS_UPDATE;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

import com.ierdely.elective_courses.security.utils.RolesHierarchyBuilder;

@Configuration
public class RoleHierarchyConfiguration {

    @Bean
    public RoleHierarchy roleHierarchy() {
    	
    	String Hierarchy = new RolesHierarchyBuilder()
        .append(ADMIN, COURSES_ADMIN)
        .append(COURSES_ADMIN, COURSES_CREATE)
        .append(COURSES_ADMIN, COURSES_READ)
        .append(COURSES_ADMIN, COURSES_UPDATE)
        .append(COURSES_ADMIN, COURSES_DELETE)
        .append(COURSES_ADMIN, COURSES_PAG_VIEW)

        .append(ADMIN, USERS_ADMIN)
        .append(USERS_ADMIN, USERS_CREATE)
        .append(USERS_ADMIN, USERS_READ)
        .append(USERS_ADMIN, USERS_UPDATE)
        .append(USERS_ADMIN, USERS_DELETE)
        .append(USERS_ADMIN, USERS_PAG_VIEW)
        
        .append(ADMIN, TEACHERS_ADMIN)
        .append(TEACHERS_ADMIN, TEACHERS_CREATE)
        .append(TEACHERS_ADMIN, TEACHERS_READ)
        .append(TEACHERS_ADMIN, TEACHERS_UPDATE)
        .append(TEACHERS_ADMIN, TEACHERS_DELETE)
        .append(TEACHERS_ADMIN, TEACHERS_PAG_VIEW)
        
        .append(ADMIN, ENROLLMENTS_ADMIN)
        .append(ENROLLMENTS_ADMIN, ENROLLMENTS_CREATE)
        .append(ENROLLMENTS_ADMIN, ENROLLMENTS_READ)
        .append(ENROLLMENTS_ADMIN, ENROLLMENTS_UPDATE)
        .append(ENROLLMENTS_ADMIN, ENROLLMENTS_DELETE)
        .append(ENROLLMENTS_ADMIN, ENROLLMENTS_PAG_VIEW)

        .append(STUDENT, USERS_READ)
        .append(STUDENT, COURSES_READ)
        .append(STUDENT, COURSES_PAG_VIEW)
        .append(STUDENT, USERS_PAG_VIEW)
        .append(STUDENT, ENROLLMENTS_PAG_VIEW)
        .append(STUDENT, ENROLLMENTS_CREATE)
        .append(STUDENT, ENROLLMENTS_READ)
        .append(STUDENT, ENROLLMENTS_UPDATE)
        .append(STUDENT, ENROLLMENTS_DELETE)
        
        .build();
      
        return RoleHierarchyImpl.fromHierarchy(Hierarchy);
    }
}
