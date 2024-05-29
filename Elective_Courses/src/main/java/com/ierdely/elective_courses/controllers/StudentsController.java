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

import com.ierdely.elective_courses.entities.Student;
import com.ierdely.elective_courses.repositories.StudentsRepository;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesCreate;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesDelete;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesRead;
import com.ierdely.elective_courses.security.annotations.students.IsStudentsUpdate;

@Controller
public class StudentsController {
	
	@Autowired
	private StudentsRepository studentsRepository;
	
	@IsElectiveCoursesRead
	@GetMapping("/students")
	public ModelAndView index() {

		List<Student> students = studentsRepository.findAll();
		ModelAndView mav = new ModelAndView("students");
		mav.addObject("students", students);
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
	

	@IsStudentsUpdate
    @GetMapping("/students/edit/{id}")
    public ModelAndView showUpdate(@PathVariable("id") Integer id) {

		Student student = studentsRepository.findById(id)
	    	      .orElseThrow(() -> new IllegalArgumentException("Invalid Student Id:" + id));
		
		ModelAndView mav = new ModelAndView("student-update", "student", student);
        return mav;
    }
	
	@IsStudentsUpdate
    @GetMapping("/students/update/{id}")
    public String update(@PathVariable("id") Integer id, 
    		@ModelAttribute @Validated Student student, 
    		BindingResult bindingResult, Model model) {
		
        if (bindingResult.hasErrors()) {
        
        	student.setId(id);
            return "student-update";
        } else {
        	
        	student.setId(id);
        	studentsRepository.save(student);
            return "redirect:/students";
        }
    }
}
