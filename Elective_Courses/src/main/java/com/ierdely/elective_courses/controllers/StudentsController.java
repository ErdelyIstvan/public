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

import com.ierdely.elective_courses.entities.Student;
import com.ierdely.elective_courses.repositories.StudentsRepository;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesCreate;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesDelete;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesRead;

@Controller
public class StudentsController {
	
	@Autowired
	private StudentsRepository studentsRepository;
	
	@IsElectiveCoursesRead
	@GetMapping("/students")
	public ModelAndView index() {

		List<Student> courses = studentsRepository.findAll();
		ModelAndView mav = new ModelAndView("students");
		mav.addObject("students", courses);
		return mav;
	}
	
	@IsElectiveCoursesCreate
    @GetMapping("/students/create")
    public ModelAndView create() {
		
		ModelAndView mav = new ModelAndView("student-create", "student", new Student());
		              
        return mav;
    }
	
	@IsElectiveCoursesCreate
    @PostMapping("/students/create")
    public String create(@ModelAttribute @Validated Student newStudent, BindingResult bindingResult) {
		
        if (bindingResult.hasErrors()) {
  
            return "student-create";
        } else {
        	studentsRepository.save(newStudent);
    		List<Student> students = studentsRepository.findAll();
    		ModelAndView mav = new ModelAndView("students");
    		mav.addObject("students", students);
    		//return mav;
            return "redirect:/students";
        }
        
    }
    

	@IsElectiveCoursesDelete
    @GetMapping("students/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
		
    	studentsRepository.deleteById(id);

        return "redirect:/students";
    }
}
