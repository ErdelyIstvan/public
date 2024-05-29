package com.ierdely.elective_courses.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.ierdely.elective_courses.entities.ApplicationProperty;
import com.ierdely.elective_courses.entities.Student;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesRead;
import com.ierdely.elective_courses.security.annotations.students.IsStudentsUpdate;
import com.ierdely.elective_courses.services.ApplicationPropertiesService;
import com.ierdely.elective_courses.services.ApplicationPropertiesService.EnrollmentStatus;

@Controller
public class StatusController {

	@Autowired
	private ApplicationPropertiesService apService;
	
	@IsElectiveCoursesRead
	@GetMapping("/status")
	public ModelAndView index() {

		ModelAndView mav = new ModelAndView("status");
		mav.addObject("enrollment_status", apService.get("enrollment_status"));
		mav.addObject("statusOptions", EnrollmentStatus.values());
		mav.addObject("enrollment_end_date", apService.get("enrollment_end_date"));

		return mav;
	}
	
	@IsStudentsUpdate
    @GetMapping("/status/update/{key}")
    public String update(@PathVariable("key") String key, 
    		@ModelAttribute ApplicationProperty property, 
    		BindingResult bindingResult, Model model) {
		
        if (bindingResult.hasErrors()) {
        
            return "student-update";
        } else {
        	apService.set(key, property.getValue());
            return "redirect:/status";
        }
    }
}
