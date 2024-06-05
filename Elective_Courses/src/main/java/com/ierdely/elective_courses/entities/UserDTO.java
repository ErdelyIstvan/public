package com.ierdely.elective_courses.entities;

import java.util.ArrayList;
import java.util.Collection;

import com.ierdely.elective_courses.entities.annotations.UniqueUsername;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


public class UserDTO {


	private Integer id;

    private String username;
	
	private String password;
	
    private Collection<RoleDTO> roles;
	
    public UserDTO (User user) {
    	setId(user.getId());
    	username = user.getUsername();
    	password = user.getPassword();

    	this.roles = new ArrayList<RoleDTO>();
    	for (Role role : user.getRoles()) {
			addRole( new RoleDTO(role));
		}

    }
	
	public Integer getId() {
		
		return id;
	}

	public void setId(Integer id) {
		
		this.id = id;
	}

	public String getUsername() {
		
		return username;
	}

	public void setUsername(String username) {
		
		this.username = username;
	}

	public String getPassword() {
		
		return password;
	}

	public void setPassword(String password) {
		
		this.password = password;
	}

	public Collection<RoleDTO> getRoles() {
		
		return roles;
	}

	public void setRoles(Collection<RoleDTO> roleDTOs) {

		this.roles = roleDTOs;
	}
	
	public void addRole(RoleDTO roleDTO) {

		this.roles.add(roleDTO);
	}
}
