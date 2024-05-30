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

import com.ierdely.elective_courses.entities.Teacher;
import com.ierdely.elective_courses.repositories.TeachersRepository;
import com.ierdely.elective_courses.security.annotations.teachers.IsTeachersCreate;
import com.ierdely.elective_courses.security.annotations.teachers.IsTeachersDelete;
import com.ierdely.elective_courses.security.annotations.teachers.IsTeachersRead;
import com.ierdely.elective_courses.security.annotations.teachers.IsTeachersUpdate;

@Controller
public class TeachersController {
	
	@Autowired
	private TeachersRepository teachersRepository;
	
	@IsTeachersRead
	@GetMapping("/teachers")
	public ModelAndView index() {

		List<Teacher> teachers = teachersRepository.findAll();
		ModelAndView mav = new ModelAndView("teachers");
		mav.addObject("teachers", teachers);
		return mav;
	}
	
	@IsTeachersCreate
    @GetMapping("/teachers/create")
    public ModelAndView create() {
		
		ModelAndView mav = new ModelAndView("teacher-create", "teacher", new Teacher());
		              
        return mav;
    }
	
	@IsTeachersCreate
    @PostMapping("/teachers/create")
    public String create(@ModelAttribute @Validated Teacher newTeacher, BindingResult bindingResult) {
		
        if (bindingResult.hasErrors()) {
  
            return "teacher-create";
        } else {
        	teachersRepository.save(newTeacher);
    		List<Teacher> teachers = teachersRepository.findAll();
    		ModelAndView mav = new ModelAndView("teachers");
    		mav.addObject("teachers", teachers);
    		//return mav;
            return "redirect:/teachers";
        }
        
    }
    

	@IsTeachersDelete
    @GetMapping("teachers/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
		
    	teachersRepository.deleteById(id);

        return "redirect:/teachers";
    }
	

	@IsTeachersUpdate
    @GetMapping("/teachers/edit/{id}")
    public ModelAndView showUpdate(@PathVariable("id") Integer id) {

		Teacher teacher = teachersRepository.findById(id)
	    	      .orElseThrow(() -> new IllegalArgumentException("Invalid Teacher Id:" + id));
		
		ModelAndView mav = new ModelAndView("teacher-update", "teacher", teacher);
        return mav;
    }
	
	@IsTeachersUpdate
    @GetMapping("/teachers/update/{id}")
    public String update(@PathVariable("id") Integer id, 
    		@ModelAttribute @Validated Teacher teacher, 
    		BindingResult bindingResult, Model model) {
		
        if (bindingResult.hasErrors()) {
        
        	teacher.setId(id);
            return "teacher-update";
        } else {
        	
        	teacher.setId(id);
        	teachersRepository.save(teacher);
            return "redirect:/teachers";
        }
    }
}
