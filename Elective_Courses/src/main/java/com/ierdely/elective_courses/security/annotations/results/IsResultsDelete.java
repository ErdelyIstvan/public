package com.ierdely.elective_courses.security.annotations.results;

import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.ierdely.elective_courses.security.SecurityRoles.RESULTS_DELETE;
import static com.ierdely.elective_courses.security.SecurityRoles.ROLE_PREFIX;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Secured(ROLE_PREFIX + RESULTS_DELETE)
public @interface IsResultsDelete {
}
