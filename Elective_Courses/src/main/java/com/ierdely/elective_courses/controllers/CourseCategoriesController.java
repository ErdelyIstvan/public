package com.ierdely.elective_courses.controllers;

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

import com.ierdely.elective_courses.entities.CourseCategory;
import com.ierdely.elective_courses.repositories.CourseCategoriesRepository;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesCreate;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesDelete;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesRead;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesUpdate;

@Controller
public class CourseCategoriesController {
	
	@Autowired
	private CourseCategoriesRepository courseCategoriesRepository;
	
	@IsElectiveCoursesRead
	@GetMapping("/coursecategories")
	public ModelAndView index() {

		List<CourseCategory> courses = courseCategoriesRepository.findAll();
		ModelAndView mav = new ModelAndView("coursecategories");
		mav.addObject("coursecategories", courses);
		return mav;
	}
	
	@IsElectiveCoursesCreate
    @GetMapping("/coursecategories/create")
    public ModelAndView create() {
		
		ModelAndView mav = new ModelAndView("coursecategory-create", "courseCategory", new CourseCategory());
		              
        return mav;
    }
	
	@IsElectiveCoursesCreate
    @PostMapping("/coursecategories/create")
    public String create(@ModelAttribute @Validated CourseCategory newCourseCategory, BindingResult bindingResult) {
		
        if (bindingResult.hasErrors()) {
  
            return "coursecategory-create";
        } else {
        	courseCategoriesRepository.save(newCourseCategory);
            return "redirect:/coursecategories";
        }
        
    }
    
	@IsElectiveCoursesDelete
    @GetMapping("coursecategories/delete/{category}")
    public String delete(@PathVariable("category") String category) {
		
    	courseCategoriesRepository.deleteById(category);

        return "redirect:/coursecategories";
    }
	
	@IsElectiveCoursesUpdate
    @GetMapping("/coursecategories/edit/{category}")
    public ModelAndView showUpdate(@PathVariable("category") String category) {

		CourseCategory courseCategory = courseCategoriesRepository.findById(category)
	    	      .orElseThrow(() -> new IllegalArgumentException("Invalid Course Category: " + category));
		
		ModelAndView mav = new ModelAndView("coursecategory-update", "courseCategory", courseCategory);
        return mav;
    }
	
	@IsElectiveCoursesUpdate
    @GetMapping("/coursecategories/update/{category}")
    public String update(@PathVariable("category") String categoryId, 
    		@ModelAttribute @Validated CourseCategory category, 
    		BindingResult bindingResult, Model model) {
		
		category.setCategory(categoryId);

		if (bindingResult.hasErrors()) {
        
            return "coursecategory-update";
        } else {
        	
        	courseCategoriesRepository.save(category);
            return "redirect:/coursecategories";
        }
    }
	
}
