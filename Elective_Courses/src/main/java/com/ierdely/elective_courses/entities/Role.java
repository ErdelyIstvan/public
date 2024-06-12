package com.ierdely.elective_courses.entities;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
    
}
