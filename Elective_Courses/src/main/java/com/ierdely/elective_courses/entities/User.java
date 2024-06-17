package com.ierdely.elective_courses.entities;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Table(name = "ec_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Integer id;
	
	@Column(name="username", nullable = false, unique = true)
//	@UniqueUsername
    private String username;
	
	@NotBlank
	private String password;
	
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles", 
        joinColumns = @JoinColumn(
           name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
           name = "role_id", referencedColumnName = "id")) 
	private Collection<Role> roles;
    
    @NotBlank(message="First name can not be blank.")
    @Pattern(regexp = "[A-Za-z0-9 ]*", message = "First name contains illegal characters")
	private String firstName;
	
	@NotBlank(message="Surname can not be blank.")
    @Pattern(regexp = "[A-Za-z0-9 ]*", message = "Surname contains illegal characters")
	private String surname;
	
	@Min(value = 0, message = "Grade has to greater or equal than 0.")
	@Max(value = 10, message = "Grade has to be smaller or equal than 10.")
	private float grade;
	
	@Min(value = 1, message = "'Year of study' has to be greater than or equal to 1.")
	@Max(value = 5, message = "'Year of study' has to be smaller than or equal to 5.")
	private byte studyYear;
	
	@Pattern(regexp = "[A-Za-z0-9 ]*", message = "Faculty Section contains illegal characters")
	private String facultySection;
    
}
