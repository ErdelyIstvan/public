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

@Entity
@Data
@Table(name = "ec_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Integer id;
	
	@Column(name="username", nullable = false, unique = true)
	@UniqueUsername
    private String username;
	
	@NotBlank
	private String password;
	
    @ManyToMany
    @JoinTable(
        name = "users_roles", 
        joinColumns = @JoinColumn(
           name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
           name = "role_id", referencedColumnName = "id")) 
	private Collection<Role> roles;
    
    public User() {
    	
    }
	
	public User (UserDTO userDTO) {
		this.id = userDTO.getId();
		this.username = userDTO.getUsername();
		this.password = userDTO.getPassword();
		this.roles = new ArrayList<Role>();
		for (RoleDTO roleDTO : userDTO.getRoles()) {
			addRole(new Role(roleDTO));
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

	public Collection<Role> getRoles() {
		
		return roles;
	}

	public void setRoles(Collection<Role> roles) {

		this.roles = roles;
	}
	
	public void addRole(Role role) {

		this.roles.add(role);
	}
}
