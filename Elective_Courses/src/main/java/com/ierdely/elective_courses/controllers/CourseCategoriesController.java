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

import com.ierdely.elective_courses.dto.CourseCategoryDTO;
import com.ierdely.elective_courses.security.CustomSecurityExpression;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesCreate;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesDelete;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesRead;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesUpdate;
import com.ierdely.elective_courses.services.CourseCategoriesService;

@Controller
public class CourseCategoriesController {
	
	@Autowired
	private CourseCategoriesService courseCategoriesService;
	
	@Autowired
	CustomSecurityExpression myCustomSecurity;
	
	@IsElectiveCoursesRead
	@GetMapping("/coursecategories")
	public ModelAndView index() {

		List<CourseCategoryDTO> courses = courseCategoriesService.getAllCourseCategories();
		ModelAndView mav = new ModelAndView("coursecategories");
		mav.addObject("coursecategories", courses);
		mav.addObject("myCustomSecurity", myCustomSecurity);
		return mav;
	}
	
	@IsElectiveCoursesCreate
    @GetMapping("/coursecategories/create")
    public ModelAndView create() {
		
		ModelAndView mav = new ModelAndView("coursecategory-create", "courseCategory", new CourseCategoryDTO("b", "b"));
		mav.addObject("myCustomSecurity", myCustomSecurity);
		              
        return mav;
    }
	
	@IsElectiveCoursesCreate
    @PostMapping("/coursecategories/create")
    public String create(@ModelAttribute("courseCategory") @Validated CourseCategoryDTO courseCategory, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
  
            return "coursecategory-create";
        } else {
        	
        	if(courseCategoriesService.doesCourseCategoryExists(courseCategory.getCategory())) {
 
        		bindingResult.rejectValue("category", "error.category",
                        "A 'Course Category' entity already exists with this identifier.");
        		model.addAttribute("myCustomSecurity", myCustomSecurity);
                return "coursecategory-create";
        	}
        	
        	courseCategoriesService.saveCourseCategory(courseCategory);
        	
            return "redirect:/coursecategories";
        }
        
    }
    
	@IsElectiveCoursesDelete
    @GetMapping("coursecategories/delete/{category}")
    public String delete(@PathVariable("category") String category) {
		
    	courseCategoriesService.deleteCourseCategory(category);

        return "redirect:/coursecategories";
    }
	
	@IsElectiveCoursesUpdate
    @GetMapping("/coursecategories/edit/{category}")
    public ModelAndView showUpdate(@PathVariable("category") String category) {

		CourseCategoryDTO courseCategory = courseCategoriesService.getCourseCategory(category);
		
		ModelAndView mav = new ModelAndView("coursecategory-update", "courseCategory", courseCategory);
		mav.addObject("myCustomSecurity", myCustomSecurity);
        return mav;
    }
	
	@IsElectiveCoursesUpdate
    @GetMapping("/coursecategories/update/{category}")
    public String update(@PathVariable("category") String categoryId, 
    		@ModelAttribute @Validated CourseCategoryDTO courseCategory, 
    		BindingResult bindingResult, Model model) {
		
		courseCategory.setCategory(categoryId);

		if (bindingResult.hasErrors()) {
        
            return "coursecategory-update";
        } else {
        	
        	courseCategoriesService.saveCourseCategory(courseCategory);
            return "redirect:/coursecategories";
        }
    }
	
}
