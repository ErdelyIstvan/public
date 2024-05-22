package com.ierdely.elective_courses.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ierdely.elective_courses.entities.CourseCategory;
import com.ierdely.elective_courses.entities.ElectiveCourse;
import com.ierdely.elective_courses.repositories.CourseCategoryRepository;
import com.ierdely.elective_courses.repositories.ElectiveCourseRepository;

@Controller
public class IndexController {
	
	
	
	@Autowired
	private ElectiveCourseRepository coursRepository;	
	
	@Autowired
	private CourseCategoryRepository categoryepository;
	
    @GetMapping
    public String index(Model model) {
    	
		List<ElectiveCourse> courses = coursRepository.findAll();
		List<CourseCategory> courseCategories = categoryepository.findAll();

		model.addAttribute("electiveCourses", courses);
		model.addAttribute("courseCategories", courseCategories);
		model.addAttribute("newElectiveCourse", new ElectiveCourse());

        return "index";
    }

}