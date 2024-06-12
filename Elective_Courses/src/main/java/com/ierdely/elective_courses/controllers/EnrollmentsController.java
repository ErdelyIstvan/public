package com.ierdely.elective_courses.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.ierdely.elective_courses.dto.EnrollmentDTO;
import com.ierdely.elective_courses.security.annotations.IsStudent;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesDelete;
import com.ierdely.elective_courses.security.annotations.enrollments.IsEnrollmentsAdmin;
import com.ierdely.elective_courses.security.annotations.enrollments.IsEnrollmentsDelete;
import com.ierdely.elective_courses.services.EnrollmentService;

@Controller
public class EnrollmentsController {
	
	@Autowired
	EnrollmentService enrollmentsService;
	
	@IsStudent
	@GetMapping("/enrollments")
	public ModelAndView index() {

		List<EnrollmentDTO> enrollments = enrollmentsService.getAllEnrollments();
		ModelAndView mav = new ModelAndView("enrollments");
		mav.addObject("enrollments", enrollments);
		return mav;
	}
	
	@IsEnrollmentsDelete
    @GetMapping("enrollments/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
		
    	enrollmentsService.deleteEnrollment(id);

        return "redirect:/enrollments";
    }
	
	

}
