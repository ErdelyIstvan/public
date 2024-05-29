package com.ierdely.elective_courses.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

import com.ierdely.elective_courses.security.utils.RolesHierarchyBuilder;

import static com.ierdely.elective_courses.security.SecurityRoles.*;

@Configuration
public class RoleHierarchyConfiguration {

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(
                new RolesHierarchyBuilder()
                        .append(ADMIN, COURSES_ADMIN)
                        .append(COURSES_ADMIN, COURSES_CREATE)
                        .append(COURSES_ADMIN, COURSES_READ)
                        .append(COURSES_ADMIN, COURSES_UPDATE)
                        .append(COURSES_ADMIN, COURSES_DELETE)
                        .append(COURSES_ADMIN, COURSES_PAG_VIEW)

                        .append(ADMIN, STUDENTS_ADMIN)
                        .append(STUDENTS_ADMIN, STUDENTS_CREATE)
                        .append(STUDENTS_ADMIN, STUDENTS_READ)
                        .append(STUDENTS_ADMIN, STUDENTS_UPDATE)
                        .append(STUDENTS_ADMIN, STUDENTS_DELETE)
                        .append(STUDENTS_ADMIN, STUDENTS_PAG_VIEW)
                        
                        .append(ADMIN, ENROLLMENTS_ADMIN)
                        .append(ENROLLMENTS_ADMIN, ENROLLMENTS_CREATE)
                        .append(ENROLLMENTS_ADMIN, ENROLLMENTS_READ)
                        .append(ENROLLMENTS_ADMIN, ENROLLMENTS_UPDATE)
                        .append(ENROLLMENTS_ADMIN, ENROLLMENTS_DELETE)
                        .append(ENROLLMENTS_ADMIN, ENROLLMENTS_PAG_VIEW)

                        .append(STUDENT, STUDENTS_READ)
                        .append(STUDENT, COURSES_READ)
                        .append(STUDENT, COURSES_PAG_VIEW)
                        .append(STUDENT, STUDENTS_PAG_VIEW)
                        .append(STUDENT, ENROLLMENTS_PAG_VIEW)
                        .append(STUDENT, ENROLLMENTS_CREATE)
                        .append(STUDENT, ENROLLMENTS_READ)
                        .append(STUDENT, ENROLLMENTS_UPDATE)
                        .append(STUDENT, ENROLLMENTS_DELETE)
                        
                        

                        .build()
        );

        return roleHierarchy;
    }
}
