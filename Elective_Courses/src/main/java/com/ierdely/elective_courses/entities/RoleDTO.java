package com.ierdely.elective_courses.entities;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

public class RoleDTO {

    private Long id;

    private String name;

    
    public RoleDTO (String name) {
    	this.name = name;
    }
    
    public RoleDTO (Role role) {

    	this.id = role.getId();
    	this.name = role.getName();
    }

    public RoleDTO() {
    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
