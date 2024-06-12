package com.ierdely.elective_courses.dto;

import com.ierdely.elective_courses.entities.Role;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)

public class RoleDTO {

    private Long id;

    private String name;

	
}
