package com.ierdely.elective_courses.entities;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Role {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
    
    public Role (String name) {
    	
    	this.name = name;
    }

    public Role() {
    	
    }

    public Role (RoleDTO roleDTO) {

    	this.id = roleDTO.getId();
    	this.name = roleDTO.getName();
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

	public Collection<User> getUsers() {
		
		return users;
	}

	public void setUsers(Collection<User> users) {
		
		this.users = users;
	}
}
