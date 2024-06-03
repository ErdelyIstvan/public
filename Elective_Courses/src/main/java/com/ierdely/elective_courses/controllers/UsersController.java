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

import com.ierdely.elective_courses.entities.User;
import com.ierdely.elective_courses.repositories.UsersRepository;
import com.ierdely.elective_courses.security.annotations.IsAdmin;


@Controller
public class UsersController {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@IsAdmin
	@GetMapping("/users")
	public ModelAndView index() {

		List<User> users = usersRepository.findAll();
		ModelAndView mav = new ModelAndView("users");
		mav.addObject("users", users);
		return mav;
	}
	
	@IsAdmin
    @GetMapping("/users/create")
    public ModelAndView create() {
		
		ModelAndView mav = new ModelAndView("user-create", "user", new User());
		              
        return mav;
    }
	
	@IsAdmin
    @PostMapping("/users/create")
    public String create(@ModelAttribute @Validated User newUser, BindingResult bindingResult) {
		
        if (bindingResult.hasErrors()) {
  
            return "user-create";
        } else {
        	usersRepository.save(newUser);
    		List<User> users = usersRepository.findAll();
    		ModelAndView mav = new ModelAndView("users");
    		mav.addObject("users", users);
    		//return mav;
            return "redirect:/users";
        }
        
    }
    

	@IsAdmin
    @GetMapping("users/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
		
    	usersRepository.deleteById(id);

        return "redirect:/users";
    }
	

	@IsAdmin
    @GetMapping("/users/edit/{id}")
    public ModelAndView showUpdate(@PathVariable("id") Integer id) {

		User user = usersRepository.findById(id)
	    	      .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
		
		ModelAndView mav = new ModelAndView("user-update", "user", user);
        return mav;
    }
	
	@IsAdmin
    @GetMapping("/users/update/{id}")
    public String update(@PathVariable("id") Integer id, 
    		@ModelAttribute @Validated User user, 
    		BindingResult bindingResult, Model model) {
		
        if (bindingResult.hasErrors()) {
        
        	user.setId(id);
            return "user-update";
        } else {
        	
        	user.setId(id);
        	usersRepository.save(user);
            return "redirect:/users";
        }
    }
}
