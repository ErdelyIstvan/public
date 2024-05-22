package com.ierdely.elective_courses.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ierdely.elective_courses.entities.CourseCategory;
import com.ierdely.elective_courses.entities.ElectiveCourse;
import com.ierdely.elective_courses.repositories.CourseCategoryRepository;
import com.ierdely.elective_courses.repositories.ElectiveCourseRepository;

import jakarta.websocket.server.PathParam;

@Controller
public class ElectiveCourseController {
	
	@Autowired
	private ElectiveCourseRepository coursRepository;	
	
	@Autowired
	private CourseCategoryRepository categoryepository;
	
	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}

	@RequestMapping("/sendData")
	public ModelAndView sendData() {
		ModelAndView mav = new ModelAndView("data");
		mav.addObject("message", "Take up one idea and make it your life");
		return mav;
	}

	@RequestMapping("/electivecourses/{courseId}")
	public ModelAndView getElectiveCourse(@PathVariable Integer courseId) {
		ModelAndView mav = new ModelAndView("electiveCourse");
		Optional<ElectiveCourse> response = coursRepository.findById(courseId);
		if(response.isPresent()) {
			mav.addObject("electiveCourse", response.get());
			
		}
		return mav;

	}
	
	@RequestMapping("/electivecourses")
	public ModelAndView getElectiveCourses() {
		ModelAndView mav = new ModelAndView("electiveCourseList");

		List<ElectiveCourse> courses = coursRepository.findAll();

		mav.addObject("electiveCourses", courses);
		return mav;

	}
	
	@RequestMapping("/electivecoursesform")
	public ModelAndView displayElectiveCourseForm() {
		
		List<CourseCategory> courseCategories = categoryepository.findAll();
		
		ModelAndView mav = new ModelAndView("electiveCourseForm");
		ElectiveCourse electiveCourse = new ElectiveCourse();
		mav.addObject("courseCategories", courseCategories);
		mav.addObject("newElectiveCourse", electiveCourse);
		return mav;
	}
	
	@RequestMapping("/saveElectiveCourse")
	public String saveElectiveCourse(@ModelAttribute ElectiveCourse electiveCourse) {
		
//		Optional<CourseCategory> category = categoryepository.findById(electiveCourse.getCategory().getCategory());
//		if (category.isPresent()) {
//			ModelAndView mav = new ModelAndView("badCategory");
//			mav.addObject("category", electiveCourse.getCategory().getCategory());
//			//return mav;
//		}
//		try {
		//electiveCourse.setCategory(null);
			coursRepository.save(electiveCourse);
//		} catch (Exception e) {
//			
//			ModelAndView mav = new ModelAndView("exception");
//			mav.addObject("exception", e);
//			return mav;
//		}
		ModelAndView mav = new ModelAndView("result");
		System.out.println(electiveCourse.getTeacherName());
		return "redirect:/";
	}

    @PostMapping("delete-elective-course")
    public String deleteElectiveCourse(@PathParam("id") int id) {
    	coursRepository.deleteById(id);

        return "redirect:/";
    }
}
