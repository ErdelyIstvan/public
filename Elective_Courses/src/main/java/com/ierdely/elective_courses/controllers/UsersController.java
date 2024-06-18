package com.ierdely.elective_courses.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.datatype.jdk8.OptionalDoubleSerializer;
import com.ierdely.elective_courses.dto.UserDTO;
import com.ierdely.elective_courses.security.CustomSecurityExpression;
import com.ierdely.elective_courses.security.annotations.users.IsUsersCreate;
import com.ierdely.elective_courses.security.annotations.users.IsUsersDelete;
import com.ierdely.elective_courses.security.annotations.users.IsUsersRead;
import com.ierdely.elective_courses.security.annotations.users.IsUsersUpdate;
import com.ierdely.elective_courses.services.ECUsersDetailedService;
import com.ierdely.elective_courses.services.EnrollmentService;


@Controller
public class UsersController {
	
	@Autowired
	private ECUsersDetailedService usersService;

	@Autowired
	private EnrollmentService  enrollmentsService;
	
	@Autowired
	CustomSecurityExpression myCustomSecurityExpression;
	
	
	@GetMapping("/users")
	public ModelAndView index(Principal principal,
            @RequestParam(name="page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name="year", required = false) Integer year) {
		
		UserDTO loggedInUser = usersService.getUser(principal.getName());
		if(page == null) {
			page = 0;
		}
		
		int pageSize = 10;

		Page<UserDTO> users = usersService.getAllUsers(PageRequest.of(page, pageSize), year);
		ModelAndView mav = new ModelAndView("users");
		mav.addObject("userPage", users);
		mav.addObject("myCustomSecurity", myCustomSecurityExpression);
		mav.addObject("page", page);
		mav.addObject("year", year);
		return mav;
	}
	
	@IsUsersRead
	@GetMapping("/pears")
	public ModelAndView getPears(Principal principal) {

		UserDTO loggedInUser = usersService.getUser(principal.getName());
		
		List<UserDTO> pears = enrollmentsService.getPears(loggedInUser);
		ModelAndView mav = new ModelAndView("pears");
		mav.addObject("pears", pears);
		return mav;
	}
	

	
	@IsUsersCreate
    @GetMapping("/users/create")
    public ModelAndView create(@RequestParam(name="page", required = false) Integer page,
    		@RequestParam(name="year", required = false) Integer year) {
		
		UserDTO userDto = new UserDTO();
		ModelAndView mav = new ModelAndView("user-create", "userDto", userDto);
		mav.addObject("allroles", usersService.getAllRoles());
		mav.addObject("page", page);
		mav.addObject("year", year);
		              
        return mav;
    }
	
	@IsUsersCreate
    @PostMapping("/users/create")
    public String create(@ModelAttribute("userDto") @Validated UserDTO userDto, 
    		BindingResult bindingResult, 
    		Model model,
    		@RequestParam(name= "page", required = false) Integer page,
    		@RequestParam(name= "year", required = false) Integer year) {
		
        if (bindingResult.hasErrors()) {
        	model.addAttribute("allroles", usersService.getAllRoles());
        	model.addAttribute("page", page);        	
        	model.addAttribute("year", year);
            return "user-create";
        } else {
        	
        	try {
				usersService.saveUserAccount(userDto);
			} catch (DataIntegrityViolationException e) {
				// Add an error message about the username being taken
	            bindingResult.rejectValue("username", "error.username", "An account already exists with this username.");
	            model.addAttribute("allroles", usersService.getAllRoles());
	            model.addAttribute("page", page);
	            model.addAttribute("year", year);
	            return "user-create";
			}
        	return formUrlToUserList(page, year);
        }
        
    }
    
	@IsUsersUpdate
    @GetMapping("/users/edit/{id}")
    public ModelAndView showUpdate(@PathVariable("id") Long id, @RequestParam(name="page", required = false ) Integer page,
    		@RequestParam(name= "year", required = false) Integer year) {

		UserDTO user = usersService.getUser(id)
	    	      .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
		
		ModelAndView mav = new ModelAndView("user-update", "user", user);
		mav.addObject("allroles", usersService.getAllRoles());
		mav.addObject("page", page);
		mav.addObject("year", year);
        return mav;
    }
	
	@IsUsersUpdate
    @GetMapping("/users/update/{id}")
    public String update(@PathVariable("id") Integer id, 
    		@ModelAttribute @Validated UserDTO user, 
    		BindingResult bindingResult, Model model,
            @RequestParam(name= "page", required = false) Integer page,
            @RequestParam(name= "year", required = false) Integer year) {
		

		if (bindingResult.hasErrors()) {
        	//user.setId(id);
        	model.addAttribute("allroles", usersService.getAllRoles());
        	model.addAttribute("user", user);
        	model.addAttribute("page", page);
        	model.addAttribute("year", year);
            return "user-update";
        } else {

        	try {
				usersService.saveUserAccount(user);
			} catch (DataIntegrityViolationException e) {
				// Add an error message about the username being taken
	            bindingResult.rejectValue("username", "error.username", "An account already exists with this username.");
	            model.addAttribute("allroles", usersService.getAllRoles());
	            model.addAttribute("user", user);
	        	model.addAttribute("page", page);
	        	model.addAttribute("year", year);
	            return "user-update";
			}
	
        	return formUrlToUserList(page, year);   
        }
    }

	@IsUsersDelete
    @GetMapping("users/delete/{id}")
    public String delete(@PathVariable("id") Long id,  
    		@RequestParam(name="page", required = false) Integer page,
    		@RequestParam(name= "year", required = false) Integer year) {
		
    	usersService.deleteUserById(id);

    	return formUrlToUserList(page, year);
    }
	
	@IsUsersUpdate
    @GetMapping("users/resetpassword/{id}")
    public String resetPassword(@PathVariable("id") Long id,
    		@RequestParam(name="page", required = false) Integer page,
    		@RequestParam(name= "year", required = false) Integer year) {
		
    	usersService.resetPassword(id);

    	return formUrlToUserList(page, year);    
	}
	
	private String formUrlToUserList(Integer page, Integer year) {
		
		String redirect = "redirect:/users";
		char link = '?';
		
		if (page != null) {
		    redirect += "?page=" + page;
		    link = '&';
		}
		if (year != null) {
			redirect += link + "year=" + year;
		}
		return redirect;
	}
}
