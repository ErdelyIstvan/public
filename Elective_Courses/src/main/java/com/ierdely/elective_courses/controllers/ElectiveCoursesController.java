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

import com.ierdely.elective_courses.entities.ElectiveCourse;
import com.ierdely.elective_courses.repositories.CourseCategoriesRepository;
import com.ierdely.elective_courses.repositories.ElectiveCoursesRepository;
import com.ierdely.elective_courses.repositories.TeachersRepository;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesCreate;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesDelete;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesRead;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesUpdate;

@Controller
public class ElectiveCoursesController {
	
	private ElectiveCoursesRepository coursesRepository;

	private CourseCategoriesRepository categoriesRepository;
	
	private TeachersRepository teachersRepository;
	
	@Autowired
	public ElectiveCoursesController(ElectiveCoursesRepository courseRepository, 
			CourseCategoriesRepository categoryRepository,
			TeachersRepository teachersRepository) {
		
		this.coursesRepository = courseRepository;
		this.categoriesRepository = categoryRepository;
		this.teachersRepository = teachersRepository;
	}
	
	@IsElectiveCoursesRead
	@GetMapping("/electivecourses")
	public ModelAndView index() {

		List<ElectiveCourse> courses = coursesRepository.findAll();
		ModelAndView mav = new ModelAndView("electivecourses");
		mav.addObject("electivecourses", courses);
		return mav;
	}
	
	@IsElectiveCoursesCreate
    @GetMapping("/electivecourses/create")
    public ModelAndView create() {
		
		ModelAndView mav = new ModelAndView("electivecourse-create", "electiveCourse", new ElectiveCourse());
		mav.addObject("categories", categoriesRepository.findAll());
		mav.addObject("teachers", teachersRepository.findAll());
        return mav;
    }
	
	@IsElectiveCoursesCreate
    @PostMapping("/electivecourses/create")
    public String create(@ModelAttribute @Validated ElectiveCourse newElectiveCourse, BindingResult bindingResult, Model model) {
		
        if (bindingResult.hasErrors()) {
        	model.addAttribute("categories", categoriesRepository.findAll());
    		model.addAttribute("teachers", teachersRepository.findAll());
            return "electivecourse-create";
        } else {
        	coursesRepository.save(newElectiveCourse);
            return "redirect:/electivecourses";
        }
        
    }
    

	@IsElectiveCoursesDelete
    @GetMapping("electivecourses/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
		
    	coursesRepository.deleteById(id);

        return "redirect:/electivecourses";
    }
	
	@IsElectiveCoursesUpdate
    @GetMapping("/electivecourses/edit/{id}")
    public ModelAndView showUpdate(@PathVariable("id") Integer id) {
	    ElectiveCourse userelectiveCourse = coursesRepository.findById(id)
	    	      .orElseThrow(() -> new IllegalArgumentException("Invalid Elective Course Id:" + id));
		ModelAndView mav = new ModelAndView("electivecourse-update", "electiveCourse", userelectiveCourse);
		mav.addObject("categories", categoriesRepository.findAll());
		mav.addObject("teachers", teachersRepository.findAll());
        return mav;
    }
	
	@IsElectiveCoursesCreate
    @GetMapping("/electivecourses/update/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute @Validated ElectiveCourse electiveCourse, BindingResult bindingResult, Model model) {
		
        if (bindingResult.hasErrors()) {
        	
        	electiveCourse.setId(id);
        	model.addAttribute("categories", categoriesRepository.findAll());
    		model.addAttribute("teachers", teachersRepository.findAll());
            return "electivecourse-update";
        } else {
        	
        	electiveCourse.setId(id);
        	coursesRepository.save(electiveCourse);
            return "redirect:/electivecourses";
        }
    }
}
