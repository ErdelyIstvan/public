package com.ierdely.elective_courses.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.ierdely.elective_courses.repositories.CourseCategoriesRepository;
import com.ierdely.elective_courses.repositories.ElectiveCoursesRepository;
import com.ierdely.elective_courses.repositories.TeachersRepository;
import com.ierdely.elective_courses.security.annotations.IsStudent;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesCreate;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesDelete;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesRead;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesUpdate;
import com.ierdely.elective_courses.services.ECUsersDetailedService;
import com.ierdely.elective_courses.services.ElectiveCoursesService;
import com.ierdely.elective_courses.services.EnrollmentService;

@Controller
public class ElectiveCoursesController {
	
	private ElectiveCoursesService coursesService;

	private CourseCategoriesRepository categoriesRepository;
	
	private TeachersRepository teachersRepository;
	
	private ECUsersDetailedService usersService;
	
	private EnrollmentService enrollmentService;
	
	@Autowired
	public ElectiveCoursesController(ElectiveCoursesService coursesService, 
			CourseCategoriesRepository categoryRepository,
			TeachersRepository teachersRepository,
			ECUsersDetailedService usersService,
			EnrollmentService enrollmentService) {
		
		this.coursesService = coursesService;
		this.categoriesRepository = categoryRepository;
		this.teachersRepository = teachersRepository;
		this.usersService = usersService;
		this.enrollmentService = enrollmentService;
	}
	
	@IsElectiveCoursesRead
	@GetMapping("/electivecourses")
	public ModelAndView index() {

		List<ElectiveCourseDTO> courses = coursesService.getAllElectiveCourses();
		ModelAndView mav = new ModelAndView("electivecourses");
		mav.addObject("electivecourses", courses);
		return mav;
	}
	
	@IsStudent
	@GetMapping("/eligibleelectivecourses")
	public ModelAndView index(Principal principal) {
		
		UserDTO loggedInUser = usersService.getUser(principal.getName());
		
		EligibleCoursesDTO data = coursesService.getEligibleCoursesData(loggedInUser);
		ModelAndView mav = new ModelAndView("eligibleelectivecourses");
		mav.addObject("data", data);
		return mav;
	}
	

	@IsElectiveCoursesDelete
    @GetMapping("electivecourses/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
		
    	coursesService.deleteElectiveCourse(id);

        return "redirect:/electivecourses";
    }
	
	@IsStudent
    @GetMapping("/electivecourses/enroll/{id}")
    public String enroll(@PathVariable("id") Integer id, Principal principal) {
		
		UserDTO loggedInUser = usersService.getUser(principal.getName());
		ElectiveCourseDTO electiveCourseDTO = coursesService.getElecticeCourse(id);
		LocalDate date = LocalDate.now();
		EnrollmentDTO enrollment = new EnrollmentDTO(null, electiveCourseDTO, loggedInUser, date, CourseEnrollmentStatus.PREFERENCE);
        enrollmentService.saveEnrollment(enrollment);
        return "redirect:/eligibleelectivecourses";
    
    }
	
	
	@IsStudent
    @GetMapping("/electivecourses/withdrawn/{id}")
    public String withdrawn(@PathVariable("id") Integer id, Principal principal) {
		
        enrollmentService.deleteEnrollment(id);
        return "redirect:/eligibleelectivecourses";
    
    }
	
	@IsElectiveCoursesCreate
    @GetMapping("/electivecourses/create")
    public ModelAndView create() {
		
		ModelAndView mav = new ModelAndView("electivecourse-create", "electiveCourse", new ElectiveCourseDTO());
		mav.addObject("categories", categoriesRepository.findAll());
		mav.addObject("teachers", teachersRepository.findAll());
        return mav;
    }
	
	@IsElectiveCoursesCreate
    @PostMapping("/electivecourses/create")
    public String create(@ModelAttribute @Validated ElectiveCourseDTO newElectiveCourse, BindingResult bindingResult, Model model) {
		
        if (bindingResult.hasErrors()) {
        	model.addAttribute("categories", categoriesRepository.findAll());
    		model.addAttribute("teachers", teachersRepository.findAll());
            return "electivecourse-create";
        } else {
        	coursesService.saveElectiveCourse(newElectiveCourse);
            return "redirect:/electivecourses";
        }
        
    }
    
	@IsElectiveCoursesUpdate
    @GetMapping("/electivecourses/edit/{id}")
    public ModelAndView showUpdate(@PathVariable("id") Integer id) {
	    
		ElectiveCourseDTO userelectiveCourse = coursesService.getElecticeCourse(id);
	    
		ModelAndView mav = new ModelAndView("electivecourse-update", "electiveCourse", userelectiveCourse);
		mav.addObject("categories", categoriesRepository.findAll());
		mav.addObject("teachers", teachersRepository.findAll());
        return mav;
    }
	
	@IsElectiveCoursesUpdate
    @GetMapping("/electivecourses/update/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute @Validated ElectiveCourseDTO electiveCourse, BindingResult bindingResult, Model model) {
		
        if (bindingResult.hasErrors()) {
        	
        	electiveCourse.setId(id);
        	model.addAttribute("categories", categoriesRepository.findAll());
    		model.addAttribute("teachers", teachersRepository.findAll());
            return "electivecourse-update";
        } else {
        	
        	electiveCourse.setId(id);
        	coursesService.updateElectiveCourse(id,electiveCourse);
            return "redirect:/electivecourses";
        }
    }
}
