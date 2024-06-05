package com.ierdely.elective_courses.controllers;

import java.util.List;
import java.util.Optional;

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
import com.ierdely.elective_courses.entities.StudentDTO;
import com.ierdely.elective_courses.entities.User;
import com.ierdely.elective_courses.entities.UserDTO;
import com.ierdely.elective_courses.repositories.UsersRepository;
import com.ierdely.elective_courses.security.annotations.students.IsStudentsCreate;
import com.ierdely.elective_courses.security.annotations.students.IsStudentsDelete;
import com.ierdely.elective_courses.security.annotations.students.IsStudentsRead;
import com.ierdely.elective_courses.security.annotations.students.IsStudentsUpdate;
import com.ierdely.elective_courses.services.StudentsService;
import com.ierdely.elective_courses.services.UsersService;

@Controller
public class StudentsController {
	
	@Autowired
	private StudentsService studentsService;
	
	@Autowired
	private UsersService usersService;
	
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
    public ModelAndView create() {
		
		ModelAndView mav = new ModelAndView("student-create", "studentDTO", new StudentDTO());
		List<UserDTO> users = usersService.getUsers();
		mav.addObject("userDTOs", users);

		              
        return mav;
    }
	
	@IsStudentsCreate
    @PostMapping("/students/create")
    public String create(@ModelAttribute @Validated StudentDTO studentDTO, BindingResult bindingResult, Model model) {
		
        if (bindingResult.hasErrors()) {
        	List<UserDTO> users = usersService.getUsers();
    		model.addAttribute("userDTOs", users);
  
            return "student-create";
        } else {
        	studentsService.save(studentDTO);
            return "redirect:/students";
        }
        
    }
    

	@IsStudentsDelete
    @GetMapping("students/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
		
		Optional<StudentDTO> student = studentsService.getStudent(id);
		if (student.isPresent()) {
			studentsService.delete(student.get());			
		}

        return "redirect:/students";
    }
	

	@IsStudentsUpdate
    @GetMapping("/students/edit/{id}")
    public ModelAndView showUpdate(@PathVariable("id") Integer id) {

		StudentDTO student = studentsService.getStudent(id)
	    	      .orElseThrow(() -> new IllegalArgumentException("Invalid Student Id:" + id));
		
		ModelAndView mav = new ModelAndView("student-update", "student", student);
		List<UserDTO> users = usersService.getUsers();
		mav.addObject("userDTOs", users);

        return mav;
    }
	
	@IsStudentsUpdate
    @GetMapping("/students/update/{id}")
    public String update(@PathVariable("id") Integer id, 
    		@ModelAttribute @Validated StudentDTO student, 
    		BindingResult bindingResult, Model model) {
		
        if (bindingResult.hasErrors()) {
        
        	student.setId(id);
            return "student-update";
        } else {
        	
        	student.setId(id);
        	studentsService.save(student);
            return "redirect:/students";
        }
    }
}
