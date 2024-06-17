package com.ierdely.elective_courses.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ierdely.elective_courses.services.ApplicationPropertiesService;

@Component("myCustomSecurity")
public class CustomSecurityExpression {
	
	
	private ApplicationPropertiesService applicationPropertiesService;
	
	@Autowired
	public CustomSecurityExpression(ApplicationPropertiesService applicationPropertiesService) {
		
		this.applicationPropertiesService = applicationPropertiesService;
	}
	
    public boolean isEnrollmentOpen() {
  
        return applicationPropertiesService.isEnrollmentOpen();
    }
}