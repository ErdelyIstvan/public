package com.ierdely.elective_courses.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ierdely.elective_courses.entities.ElectiveCourse;
import com.ierdely.elective_courses.entities.Student;
import com.ierdely.elective_courses.repositories.StudentRepository;
import com.ierdely.elective_courses.security.annotations.students.IsStudentsCreate;
import com.ierdely.elective_courses.security.annotations.students.IsStudentsRead;

@RestController
public class StudentController {
	
	@Autowired
	private StudentRepository repository;
	
	@GetMapping("students/{studentName}")
	@ResponseBody
	public Student getStudent(@PathVariable String studentName) {
		Optional<Student> response = repository.findById(studentName);
		if(response.isPresent()) {
			return response.get();
			
		}
		return null;
		
		
	}
	@IsStudentsRead
	@GetMapping("students")
	public ModelAndView index() {
		
		List<Student> students = repository.findAll();
		ModelAndView mav = new ModelAndView("students");
		mav.addObject("students", students);
		return mav;
	}
	
    @IsStudentsCreate
    @GetMapping("/students/create")
    public ModelAndView create() {
        return new ModelAndView("student-create", "newStudent", new Student());
    }

    @IsStudentsCreate
    @PostMapping("/students/create")
    public String create(@ModelAttribute @Validated Student newStudent, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "student-create";
        } else {
        	repository.save(newStudent);

            return "redirect:/students";
        }
    }
}
