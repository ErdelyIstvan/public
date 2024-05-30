package com.ierdely.elective_courses.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="ec_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Integer id;
	
	private String username;
	
	private String password;
	
	@OneToOne
	private Student student;
	
}
