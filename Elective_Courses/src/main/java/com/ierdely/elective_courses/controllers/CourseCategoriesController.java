package com.ierdely.elective_courses.controllers;

import java.util.List;

import org.hibernate.grammars.hql.HqlParser.CastTargetContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

@Controller
public class CourseCategoriesController {
	
	@Autowired
	private CourseCategoriesRepository categoryRepository;
	
	@IsElectiveCoursesRead
	@GetMapping("/coursecategories")
	public ModelAndView index() {

		List<CourseCategory> courses = categoryRepository.findAll();
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
        	categoryRepository.save(newCourseCategory);
            return "redirect:/coursecategories";
        }
        
    }
    

	@IsElectiveCoursesDelete
    @GetMapping("coursecategories/delete/{category}")
    public String delete(@PathVariable("category") String category) {
		
    	categoryRepository.deleteById(category);

        return "redirect:/coursecategories";
    }
}
