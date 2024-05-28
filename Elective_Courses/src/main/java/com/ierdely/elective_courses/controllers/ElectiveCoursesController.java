package com.ierdely.elective_courses.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mediatype.hal.CurieProvider;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ierdely.elective_courses.entities.CourseCategory;
import com.ierdely.elective_courses.entities.ElectiveCourse;
import com.ierdely.elective_courses.repositories.CourseCategoriesRepository;
import com.ierdely.elective_courses.repositories.ElectiveCoursesRepository;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesCreate;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesDelete;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesRead;

@Controller
public class ElectiveCoursesController {
	
	private ElectiveCoursesRepository courseRepository;

	private CourseCategoriesRepository categoryRepository;
	
	@Autowired
	public ElectiveCoursesController(ElectiveCoursesRepository courseRepository, CourseCategoriesRepository categoryRepository) {
		this.courseRepository = courseRepository;
		this.categoryRepository = categoryRepository;
	}
	
	@IsElectiveCoursesRead
	@GetMapping("/electivecourses")
	public ModelAndView index() {

		List<ElectiveCourse> courses = courseRepository.findAll();
		ModelAndView mav = new ModelAndView("electivecourses");
		mav.addObject("electivecourses", courses);
		return mav;
	}
	
	@IsElectiveCoursesCreate
    @GetMapping("/electivecourses/create")
    public ModelAndView create() {
		
		ModelAndView mav = new ModelAndView("electivecourse-create", "electiveCourse", new ElectiveCourse());
		mav.addObject("courseCategories", categoryRepository.findAll());
        return mav;
    }
	
	@IsElectiveCoursesCreate
    @PostMapping("/electivecourses/create")
    public String create(@ModelAttribute @Validated ElectiveCourse newElectiveCourse, BindingResult bindingResult) {
		
        if (bindingResult.hasErrors()) {
            return "electivecourse-create";
        } else {
        	courseRepository.save(newElectiveCourse);
            return "redirect:/electivecourses";
        }
        
    }
    

	@IsElectiveCoursesDelete
    @GetMapping("electivecourses/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
		
    	courseRepository.deleteById(id);

        return "redirect:/electivecourses";
    }
}
