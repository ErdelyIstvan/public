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

import com.ierdely.elective_courses.dto.StudentDTO;
import com.ierdely.elective_courses.dto.UserDTO;
import com.ierdely.elective_courses.security.annotations.electivecourses.IsElectiveCoursesDelete;
import com.ierdely.elective_courses.security.annotations.students.IsStudentsCreate;
import com.ierdely.elective_courses.security.annotations.students.IsStudentsRead;
import com.ierdely.elective_courses.security.annotations.students.IsStudentsUpdate;
import com.ierdely.elective_courses.services.ECUsersDetailedService;
import com.ierdely.elective_courses.services.StudentsService;

@Controller
public class StudentsController {
	
	private StudentsService studentsService;
	private ECUsersDetailedService usersService;

	
	@Autowired
	public StudentsController(StudentsService studentsService, 
			ECUsersDetailedService usersService) {
		
		this.studentsService = studentsService;
		this.usersService = usersService;

	}
	
	@IsStudentsRead
	@GetMapping("/students")
	public ModelAndView index() {

		List<StudentDTO> students = studentsService.getAllStudents();
		ModelAndView mav = new ModelAndView("students");
		mav.addObject("students", students);
		return mav;
	}
	

	  
	@IsStudentsCreate
    @GetMapping("/students/create")
    public ModelAndView studentForm() {

		StudentDTO student = new StudentDTO();
//		UserDTO userDto = new UserDTO();
//		student.setUserDto(userDto);
		ModelAndView mav = new ModelAndView("student-create", "student", student);
		//mav.addObject("userDtos", usersService.getUsersWithoutAssignedStudent());
        return mav;
    }
	
	@IsStudentsCreate
    @PostMapping("/students/create")
    public String create(@ModelAttribute("student") @Validated StudentDTO student, BindingResult bindingResult, Model model) {
		
        if (bindingResult.hasErrors()) {
        	//model.addAttribute("userDtos", usersService.getUsersWithoutAssignedStudent());
            return "student-create";
        } else {
        	studentsService.save(student);
            return "redirect:/students";
        }
        
    }
    

	@IsElectiveCoursesDelete
    @GetMapping("students/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
		
    	studentsService.delete(id);

        return "redirect:/students";
    }
	
	@IsStudentsUpdate
    @GetMapping("/students/edit/{id}")
    public ModelAndView showUpdate(@PathVariable("id") Integer id) {
	    StudentDTO student = studentsService.getStudent(id);
	    
		ModelAndView mav = new ModelAndView("student-update", "student", student);
		mav.addObject("users", usersService.getAllUsers());
        return mav;
    }
	
	@IsStudentsUpdate
    @GetMapping("/students/update/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute @Validated StudentDTO student, BindingResult bindingResult, Model model) {
		
        if (bindingResult.hasErrors()) {
        	
        	student.setId(id);
        	model.addAttribute("users", usersService.getAllUsers());
            return "student-update";
        } else {
        	
        	studentsService.save(student);
            return "redirect:/students";
        }
    }
}
