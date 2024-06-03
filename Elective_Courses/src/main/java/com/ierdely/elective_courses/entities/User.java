package com.ierdely.elective_courses.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "ec_user",
		uniqueConstraints =
			@UniqueConstraint(columnNames = {"username"}))
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Integer id;
	

	@Column(name="username")
	private String username;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String roles;
	
	
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

	public String getRoles() {
		
		return roles;
	}

	public void setRoles(String roles) {

		this.roles = roles;
	}
}
