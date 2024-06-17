package com.ierdely.elective_courses.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ierdely.elective_courses.dto.ElectiveCourseDTO;
import com.ierdely.elective_courses.dto.EligibleCoursesDTO;
import com.ierdely.elective_courses.dto.EnrollmentDTO;
import com.ierdely.elective_courses.dto.UserDTO;
import com.ierdely.elective_courses.enums.CourseEnrollmentStatus;
import com.ierdely.elective_courses.security.CustomSecurityExpression;
import com.ierdely.elective_courses.security.annotations.IsStudent;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesCreate;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesDelete;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesRead;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesUpdate;
import com.ierdely.elective_courses.security.annotations.enrollments.IsEnrollmentsCreate;
import com.ierdely.elective_courses.security.annotations.enrollments.IsEnrollmentsDelete;
import com.ierdely.elective_courses.services.CourseCategoriesService;
import com.ierdely.elective_courses.services.ECUsersDetailedService;
import com.ierdely.elective_courses.services.ElectiveCoursesService;
import com.ierdely.elective_courses.services.EnrollmentService;
import com.ierdely.elective_courses.services.TeachersService;

@Controller
public class ElectiveCoursesController {
	
	private ElectiveCoursesService coursesService;

	private CourseCategoriesService categoriesService;
	
	private TeachersService teachersService;
	
	private ECUsersDetailedService usersService;
	
	private EnrollmentService enrollmentService;
	
	private CustomSecurityExpression myCustomSecurityExpression;
	
	@Autowired
	public ElectiveCoursesController(ElectiveCoursesService coursesService, 
			CourseCategoriesService courseCategoriesService,
			TeachersService teachersService,
			ECUsersDetailedService usersService,
			EnrollmentService enrollmentService,
			CustomSecurityExpression customSecurityExpression) {
		
		this.coursesService = coursesService;
		this.categoriesService = courseCategoriesService;
		this.teachersService = teachersService;
		this.usersService = usersService;
		this.enrollmentService = enrollmentService;
		this.myCustomSecurityExpression = customSecurityExpression;
	}
	
	@IsElectiveCoursesRead
	@GetMapping("/electivecourses")
	public ModelAndView index() {

		List<ElectiveCourseDTO> courses = coursesService.getAllElectiveCourses();
		ModelAndView mav = new ModelAndView("electivecourses");
		mav.addObject("electivecourses", courses);
		mav.addObject("myCustomSecurity", myCustomSecurityExpression);
		return mav;
	}
	
	@IsStudent
	@GetMapping("/eligibleelectivecourses")
	public ModelAndView index(Principal principal) {
		
		UserDTO loggedInUser = usersService.getUser(principal.getName());
		
		EligibleCoursesDTO data = coursesService.getEligibleCoursesData(loggedInUser);
		ModelAndView mav = new ModelAndView("eligibleelectivecourses");
		mav.addObject("data", data);
		mav.addObject("myCustomSecurity", myCustomSecurityExpression);
		return mav;
	}
	

	@IsElectiveCoursesDelete
    @GetMapping("electivecourses/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
		
    	coursesService.deleteElectiveCourse(id);

        return "redirect:/electivecourses";
    }
	
	@IsEnrollmentsCreate
	@PreAuthorize("@myCustomSecurity.isEnrollmentOpen()")
    @GetMapping("/electivecourses/enroll/{id}")
    public String enroll(@PathVariable("id") Integer id, Principal principal) {
		
		UserDTO loggedInUser = usersService.getUser(principal.getName());
		ElectiveCourseDTO electiveCourseDTO = coursesService.getElecticeCourse(id);
		LocalDate date = LocalDate.now();
		EnrollmentDTO enrollment = new EnrollmentDTO(null, electiveCourseDTO, loggedInUser, loggedInUser.getStudyYear(), date, CourseEnrollmentStatus.PREFERENCE);
        enrollmentService.saveEnrollment(enrollment);
        return "redirect:/eligibleelectivecourses";
    
    }
	
	@IsEnrollmentsCreate
	@PreAuthorize("@myCustomSecurity.isEnrollmentOpen()")
    @GetMapping("/electivecourses/enroll-alternative/{id}")
    public String enrollAlternative(@PathVariable("id") Integer id, Principal principal) {
		
		UserDTO loggedInUser = usersService.getUser(principal.getName());
		ElectiveCourseDTO electiveCourseDTO = coursesService.getElecticeCourse(id);
		LocalDate date = LocalDate.now();
		EnrollmentDTO enrollment = new EnrollmentDTO(null, electiveCourseDTO, loggedInUser, loggedInUser.getStudyYear(), date, CourseEnrollmentStatus.ALTERNATIVE_PREFERENCE);
        enrollmentService.saveEnrollment(enrollment);
        return "redirect:/eligibleelectivecourses";
    
    }
	
    //@PreAuthorize("@myCustomSecurity.checkEnrollmentStatus()")
    @IsEnrollmentsDelete
	@PreAuthorize("@myCustomSecurity.isEnrollmentOpen()")
    @GetMapping("/electivecourses/withdrawn/{id}")
    public String withdrawn(@PathVariable("id") Integer id, Principal principal) {
		
        enrollmentService.deleteEnrollment(id);
        return "redirect:/eligibleelectivecourses";
    
    }
	
	@IsElectiveCoursesCreate
    @GetMapping("/electivecourses/create")
    public ModelAndView create() {
		
		ModelAndView mav = new ModelAndView("electivecourse-create", "electiveCourse", new ElectiveCourseDTO());
		mav.addObject("categories", categoriesService.getAllCourseCategories());
		mav.addObject("teachers", teachersService.getAllTeachers());
        return mav;
    }
	
	@IsElectiveCoursesCreate
    @PostMapping("/electivecourses/create")
    public String create(@ModelAttribute @Validated ElectiveCourseDTO electiveCourse, BindingResult bindingResult, Model model) {
		
        if (bindingResult.hasErrors()) {
        	model.addAttribute("categories", categoriesService.getAllCourseCategories());
    		model.addAttribute("teachers", teachersService.getAllTeachers());
            return "electivecourse-create";
        } else {
        	coursesService.saveElectiveCourse(electiveCourse);
            return "redirect:/electivecourses";
        }
        
    }
    
	@IsElectiveCoursesUpdate
    @GetMapping("/electivecourses/edit/{id}")
    public ModelAndView showUpdate(@PathVariable("id") Integer id) {
	    
		ElectiveCourseDTO userelectiveCourse = coursesService.getElecticeCourse(id);
	    
		ModelAndView mav = new ModelAndView("electivecourse-update", "electiveCourse", userelectiveCourse);
		mav.addObject("categories", categoriesService.getAllCourseCategories());
		mav.addObject("teachers", teachersService.getAllTeachers());
        return mav;
    }
	
	@IsElectiveCoursesUpdate
    @GetMapping("/electivecourses/update/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute @Validated ElectiveCourseDTO electiveCourse, BindingResult bindingResult, Model model) {
		
        if (bindingResult.hasErrors()) {
        	
        	electiveCourse.setId(id);
        	model.addAttribute("categories", categoriesService.getAllCourseCategories());
    		model.addAttribute("teachers", teachersService.getAllTeachers());
            return "electivecourse-update";
        } else {
        	
        	electiveCourse.setId(id);
        	coursesService.updateElectiveCourse(id,electiveCourse);
            return "redirect:/electivecourses";
        }
    }
}
