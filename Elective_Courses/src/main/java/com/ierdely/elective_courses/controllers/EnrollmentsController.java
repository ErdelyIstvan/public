package com.ierdely.elective_courses.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ierdely.elective_courses.dto.EnrollmentDTO;
import com.ierdely.elective_courses.security.CustomSecurityExpression;
import com.ierdely.elective_courses.security.annotations.IsStudent;
import com.ierdely.elective_courses.security.annotations.enrollments.IsEnrollmentsDelete;
import com.ierdely.elective_courses.services.ECUsersDetailedService;
import com.ierdely.elective_courses.services.ElectiveCoursesService;
import com.ierdely.elective_courses.services.EnrollmentService;

@Controller
public class EnrollmentsController {
	
	@Autowired
	EnrollmentService enrollmentsService;
	
	@Autowired
	ECUsersDetailedService usersService;
	
	@Autowired
	private ElectiveCoursesService electiveCoursesService;
	
	@Autowired
	private CustomSecurityExpression myCustomSecurityExpression;
	
	
	@IsStudent
	@GetMapping("/enrollments")
	public ModelAndView index(@RequestParam(name = "courseID", required = false) Integer courseID,
			@RequestParam(name = "userID", required = false) Integer userID,
			@RequestParam(name = "year", required = false) Byte year) {
		if (year != null && (year < 1 || year > 5)) {
			year = null;
		}
		List<EnrollmentDTO> enrollments = enrollmentsService.getEnrollments(courseID, userID, year);
		ModelAndView mav = new ModelAndView("enrollments");
		mav.addObject("courses", electiveCoursesService.getAllElectiveCourses());
		mav.addObject("users", usersService.getAllStudentUsers());
		mav.addObject("enrollments", enrollments);
		mav.addObject("myCustomSecurity", myCustomSecurityExpression);
		
		//lets add the params, maybe they get remembered
		mav.addObject("year", year);
		return mav;
	}
	

	
	@IsEnrollmentsDelete
    @GetMapping("enrollments/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
		
    	enrollmentsService.deleteEnrollment(id);

        return "redirect:/enrollments";
    }
	
	

}
