package com.ierdely.elective_courses.security.annotations.teachers;

import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.ierdely.elective_courses.security.SecurityRoles.TEACHERS_ADMIN;
import static com.ierdely.elective_courses.security.SecurityRoles.ROLE_PREFIX;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Secured(ROLE_PREFIX + TEACHERS_ADMIN)
public @interface IsTeacherAdmin {
}
