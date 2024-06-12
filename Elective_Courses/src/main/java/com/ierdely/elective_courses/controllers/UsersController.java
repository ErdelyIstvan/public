package com.ierdely.elective_courses.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.ierdely.elective_courses.dto.UserDTO;
import com.ierdely.elective_courses.entities.User;
import com.ierdely.elective_courses.security.annotations.IsAdmin;
import com.ierdely.elective_courses.services.ECUsersDetailedService;
import com.ierdely.elective_courses.services.EnrollmentService;


@Controller
public class UsersController {
	
	@Autowired
	private ECUsersDetailedService usersService;

	@Autowired
	private EnrollmentService  enrollmentsService;
	
	@GetMapping("/users")
	public ModelAndView index(Principal principal) {

		UserDTO loggedInUser = usersService.getUser(principal.getName());
		
		List<UserDTO> users = usersService.getAllUsers();
		ModelAndView mav = new ModelAndView("users");
		mav.addObject("users", users);
		return mav;
	}
	
	@GetMapping("/pears")
	public ModelAndView getPears(Principal principal) {

		UserDTO loggedInUser = usersService.getUser(principal.getName());
		
		List<UserDTO> users = enrollmentsService.getPears(loggedInUser);
		ModelAndView mav = new ModelAndView("users");
		mav.addObject("users", users);
		return mav;
	}
	

	
	@IsAdmin
    @GetMapping("/users/create")
    public ModelAndView create() {
		
		UserDTO userDto = new UserDTO();
		ModelAndView mav = new ModelAndView("user-create", "userDto", userDto);
		mav.addObject("allroles", usersService.getAllRoles());
		              
        return mav;
    }
	
	@IsAdmin
    @PostMapping("/users/create")
    public String create(@ModelAttribute("userDto") @Validated UserDTO userDto, BindingResult bindingResult, Model model) {
		
        if (bindingResult.hasErrors()) {
        	model.addAttribute("allroles", usersService.getAllRoles());
            return "user-create";
        } else {
        	
        	try {
				usersService.saveUserAccount(userDto);
			} catch (DataIntegrityViolationException e) {
				// Add an error message about the username being taken
	            bindingResult.rejectValue("username", "error.username", "An account already exists with this username.");
	            model.addAttribute("allroles", usersService.getAllRoles());
	            return "user-create";
			}
			return "redirect:/users";
        }
        
    }
    
	@IsAdmin
    @GetMapping("/users/edit/{id}")
    public ModelAndView showUpdate(@PathVariable("id") Long id) {

		UserDTO user = usersService.getUser(id)
	    	      .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
		
		ModelAndView mav = new ModelAndView("user-update", "user", user);
		mav.addObject("allroles", usersService.getAllRoles());
        return mav;
    }
	
	@IsAdmin
    @GetMapping("/users/update/{id}")
    public String update(@PathVariable("id") Integer id, 
    		@ModelAttribute @Validated UserDTO user, 
    		BindingResult bindingResult, Model model) {
		

		if (bindingResult.hasErrors()) {
        	//user.setId(id);
        	model.addAttribute("allroles", usersService.getAllRoles());
        	model.addAttribute("user", user);
            return "user-update";
        } else {
//        	user.setId(id);
        	try {
				usersService.saveUserAccount(user);
			} catch (DataIntegrityViolationException e) {
				// Add an error message about the username being taken
	            bindingResult.rejectValue("username", "error.username", "An account already exists with this username.");
	            model.addAttribute("allroles", usersService.getAllRoles());
	            model.addAttribute("user", user);
	            return "user-update";
			}
	

            return "redirect:/users";
        }
    }

	@IsAdmin
    @GetMapping("users/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
		
    	usersService.deleteUserById(id);

        return "redirect:/users";
    }
}
