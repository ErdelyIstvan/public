package com.ierdely.elective_courses.entities;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class ApplicationProperty {

	public static String KEY_ENROLLMENT_STATUS = "enrollment_status";
	public static String KEY_ENROLLMENT_END_DATE = "enrollment_end_date";
	
	@jakarta.persistence.Id
	@NotBlank
	String key;
	
	@NotBlank
	String value;
	
	public ApplicationProperty() {
	}
	
	public ApplicationProperty(String key, String value) {
		
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
	
		return key;
	}
	
	public void setKey(String key) {
	
		this.key = key;
	}
	
	public String getValue() {
		
		return value;
	}
	
	public void setValue(String value) {
		
		this.value = value;
	}
	
	
}
