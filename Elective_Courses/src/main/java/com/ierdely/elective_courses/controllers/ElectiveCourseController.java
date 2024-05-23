package com.ierdely.elective_courses.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ierdely.elective_courses.entities.CourseCategory;
import com.ierdely.elective_courses.entities.ElectiveCourse;
import com.ierdely.elective_courses.repositories.CourseCategoryRepository;
import com.ierdely.elective_courses.repositories.ElectiveCourseRepository;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesCreate;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesDelete;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesRead;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesUpdate;

import jakarta.websocket.server.PathParam;

@Controller
public class ElectiveCourseController {
	
	@Autowired
	private ElectiveCourseRepository coursRepository;	
	
	@Autowired
	private CourseCategoryRepository categoryRepository;


	@RequestMapping("/electivecourses/{courseId}")
	public ModelAndView getElectiveCourse(@PathVariable Integer courseId) {
		ModelAndView mav = new ModelAndView("electiveCourse");
		Optional<ElectiveCourse> response = coursRepository.findById(courseId);
		if(response.isPresent()) {
			mav.addObject("electiveCourse", response.get());
			
		}
		return mav;

	}
	
	@IsElectiveCoursesRead
	@RequestMapping("/electivecourses")
	public ModelAndView index() {

		List<ElectiveCourse> courses = coursRepository.findAll();
		ModelAndView mav = new ModelAndView("electivecourses");
		mav.addObject("electivecourses", courses);
		return mav;
	}
	
	@IsElectiveCoursesCreate
    @GetMapping("/electivecourses/create")
    public ModelAndView create() {

        return new ModelAndView("electivecourses-create", "newElectiveCourse", new ElectiveCourse());
    }
	
	@IsElectiveCoursesCreate
    @PostMapping("/electivecourses/create")
    public String create(@ModelAttribute @Validated ElectiveCourse customer, BindingResult bindingResult) {
		
        if (bindingResult.hasErrors()) {
            return "electivecourses-create";
        } else {
        	coursRepository.save(customer);
            return "redirect:/electivecourses";
        }
    }
    
	
//	@RequestMapping("/electivecoursesform")
//	public ModelAndView displayElectiveCourseForm() {
//		
//		List<CourseCategory> courseCategories = categoryRepository.findAll();
//		
//		ModelAndView mav = new ModelAndView("electiveCourseForm");
//		ElectiveCourse electiveCourse = new ElectiveCourse();
//		mav.addObject("courseCategories", courseCategories);
//		mav.addObject("newElectiveCourse", electiveCourse);
//		return mav;
//	}
//	
//	@RequestMapping("/saveElectiveCourse")
//	public String saveElectiveCourse(@ModelAttribute ElectiveCourse electiveCourse) {
//		
////		Optional<CourseCategory> category = categoryepository.findById(electiveCourse.getCategory().getCategory());
////		if (category.isPresent()) {
////			ModelAndView mav = new ModelAndView("badCategory");
////			mav.addObject("category", electiveCourse.getCategory().getCategory());
////			//return mav;
////		}
////		try {
//		//electiveCourse.setCategory(null);
//			coursRepository.save(electiveCourse);
////		} catch (Exception e) {
////			
////			ModelAndView mav = new ModelAndView("exception");
////			mav.addObject("exception", e);
////			return mav;
////		}
//		ModelAndView mav = new ModelAndView("result");
//		System.out.println(electiveCourse.getTeacherName());
//		return "redirect:/";
//	}

	@IsElectiveCoursesDelete
    @GetMapping("electivecourses/delete/id")
    public String delete(@PathParam("id") int id) {
		
    	coursRepository.deleteById(id);

        return "redirect:/electivecourses";
    }
}
