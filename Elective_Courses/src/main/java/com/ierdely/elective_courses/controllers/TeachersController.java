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

import com.ierdely.elective_courses.dto.TeacherDTO;
import com.ierdely.elective_courses.security.CustomSecurityExpression;
import com.ierdely.elective_courses.security.annotations.teachers.IsTeachersCreate;
import com.ierdely.elective_courses.security.annotations.teachers.IsTeachersDelete;
import com.ierdely.elective_courses.security.annotations.teachers.IsTeachersRead;
import com.ierdely.elective_courses.security.annotations.teachers.IsTeachersUpdate;
import com.ierdely.elective_courses.services.TeachersService;

@Controller
public class TeachersController {
	
	@Autowired
	private TeachersService teachersService;
	
	@Autowired
	CustomSecurityExpression myCustomSecurity;
	
	@IsTeachersRead
	@GetMapping("/teachers")
	public ModelAndView index() {

		List<TeacherDTO> teachers = teachersService.getAllTeachers();
		ModelAndView mav = new ModelAndView("teachers");
		mav.addObject("teachers", teachers);
		mav.addObject("myCustomSecurity", myCustomSecurity);
		return mav;
	}
	
	@IsTeachersCreate
    @GetMapping("/teachers/create")
    public ModelAndView create() {
		
		ModelAndView mav = new ModelAndView("teacher-create", "teacher", new TeacherDTO());
		              
        return mav;
    }
	
	@IsTeachersCreate
    @PostMapping("/teachers/create")
    public String create(@ModelAttribute @Validated TeacherDTO teacher, BindingResult bindingResult) {
		
        if (bindingResult.hasErrors()) {
  
            return "teacher-create";
        } else {

        	teachersService.saveTeacher(teacher);
    		List<TeacherDTO> teachers = teachersService.getAllTeachers();
    		ModelAndView mav = new ModelAndView("teachers");
    		mav.addObject("teachers", teachers);
            return "redirect:/teachers";
        }
        
    }
    

	@IsTeachersDelete
    @GetMapping("teachers/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
		
    	teachersService.deleteTeacher(id);

        return "redirect:/teachers";
    }
	

	@IsTeachersUpdate
    @GetMapping("/teachers/edit/{id}")
    public ModelAndView showUpdate(@PathVariable("id") Integer id) {

		TeacherDTO teacher = teachersService.getTeacher(id);

		ModelAndView mav = new ModelAndView("teacher-update", "teacher", teacher);
        return mav;
    }
	
	@IsTeachersUpdate
    @GetMapping("/teachers/update/{id}")
    public String update(@PathVariable("id") Integer id, 
    		@ModelAttribute @Validated TeacherDTO teacher, 
    		BindingResult bindingResult, Model model) {
		
        if (bindingResult.hasErrors()) {
        
        	teacher.setId(id);
            return "teacher-update";
        } else {
        	
        	teacher.setId(id);
        	teachersService.updateTeacher(id, teacher);
            return "redirect:/teachers";
        }
    }
}
