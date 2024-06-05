package com.ierdely.elective_courses.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ierdely.elective_courses.entities.User;
import com.ierdely.elective_courses.security.annotations.IsAdmin;
import com.ierdely.elective_courses.services.ECUsersDetailedService;


@Controller
public class UsersController {
	
	@Autowired
	private ECUsersDetailedService usersService;
	
	@IsAdmin
	@GetMapping("/users")
	public ModelAndView index() {

		List<User> users = usersService.getAllUsers();
		ModelAndView mav = new ModelAndView("users");
		mav.addObject("users", users);
		return mav;
	}
	
	@IsAdmin
    @GetMapping("/users/create")
    public ModelAndView create() {
		
		ModelAndView mav = new ModelAndView("user-create", "user", new User());
		mav.addObject("allroles", usersService.getAllRoles());
		              
        return mav;
    }
	
	@IsAdmin
    @PostMapping("/users/create")
    public String create(@ModelAttribute @Validated User newUser, BindingResult bindingResult, Model model) {
		
        if (bindingResult.hasErrors()) {
        	model.addAttribute("allroles", usersService.getAllRoles());
            return "user-create";
        } else {
        	try {
				usersService.saveUserAccount(newUser);
			} catch (RuntimeException e) {
				bindingResult.addError(new ObjectError("username", "Username taken!"));
				model.addAttribute("allroles", usersService.getAllRoles());
	            return "user-create";
			}
    		List<User> users = usersService.getAllUsers();
            return "redirect:/users";
        }
        
    }
    

	@IsAdmin
    @GetMapping("users/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
		
    	usersService.deleteUserById(id);

        return "redirect:/users";
    }
	

	@IsAdmin
    @GetMapping("/users/edit/{id}")
    public ModelAndView showUpdate(@PathVariable("id") Long id) {

		User user = usersService.getUser(id)
	    	      .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
		
		ModelAndView mav = new ModelAndView("user-update", "user", user);
		mav.addObject("allroles", usersService.getAllRoles());
        return mav;
    }
	
	@IsAdmin
    @GetMapping("/users/update/{id}")
    public String update(@PathVariable("id") Long id, 
    		@ModelAttribute @Validated User user, 
    		BindingResult bindingResult, Model model) {
		
		bindingResult.recordSuppressedField("username");

		if (bindingResult.hasErrors()) {
        	user.setId(id);
        	model.addAttribute("allroles", usersService.getAllRoles());
            return "user-update";
        } else {
        	user.setId(id);
           	try {
				usersService.saveUserAccount(user);
			} catch (RuntimeException e) {
				model.addAttribute("allroles", usersService.getAllRoles());
	            return "user-update";
			}
            return "redirect:/users";
        }
    }
}
