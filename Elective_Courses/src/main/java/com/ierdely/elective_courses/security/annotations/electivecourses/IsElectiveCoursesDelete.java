package com.ierdely.elective_courses.security.annotations.electivecourses;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.ierdely.elective_courses.security.SecurityRoles.COURSES_DELETE;
import static com.ierdely.elective_courses.security.SecurityRoles.ROLE_PREFIX;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Secured(ROLE_PREFIX + COURSES_DELETE)
@PreAuthorize("@myCustomSecurity.isEnrollmentOpen()")
public @interface IsElectiveCoursesDelete {
}
