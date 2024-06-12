package com.ierdely.elective_courses.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ierdely.elective_courses.entities.ApplicationProperty;
import com.ierdely.elective_courses.enums.ApplicationEnrollmentStatus;
import com.ierdely.elective_courses.repositories.ApplicationPropertiesRepository;

@Service
public class ApplicationPropertiesService {

	private ApplicationPropertiesRepository applicationPropertiesRepository;
	
	@Autowired
	public ApplicationPropertiesService(ApplicationPropertiesRepository applicationPropertiesRepository) {
		this.applicationPropertiesRepository = applicationPropertiesRepository;
	}
	
	@Value("${ec.min_mandatory_electives.1}")
	private String minimumElectivesForYear1;

	@Value("${ec.min_mandatory_electives.2}")
	private String minimumElectivesForYear2;

	@Value("${ec.min_mandatory_electives.3}")
	private String minimumElectivesForYear3;

	@Value("${ec.min_mandatory_electives.4}")
	private String minimumElectivesForYear4;

	@Value("${ec.min_mandatory_electives.5}")
	private String minimumElectivesForYear5;

	public byte getMinimumElectivesForYear1() {
		
		return Byte.parseByte(minimumElectivesForYear1);
	}

	public byte getMinimumElectivesForYear2() {
		
		return Byte.parseByte(minimumElectivesForYear2);
	}

	public byte getMinimumElectivesForYear3() {
		
		return Byte.parseByte(minimumElectivesForYear3);
	}

	public byte getMinimumElectivesForYear4() {
		
		return Byte.parseByte(minimumElectivesForYear4);
	}

	public byte getMinimumElectivesForYear5() {
		
		return Byte.parseByte(minimumElectivesForYear5);
	}
	
	public byte getMinimumElectivesForYearOfStudy(byte yearOfStudy) {
		
		switch (yearOfStudy) {
		
			case 1:
				return getMinimumElectivesForYear1();
			case 2:
				return getMinimumElectivesForYear2();
			case 3:
				return getMinimumElectivesForYear3();
			case 4:
				return getMinimumElectivesForYear4();
			case 5:
				return getMinimumElectivesForYear5();
			default:
					return 0;
		}
	}
	
	public Optional<Date> getEnrollmentEndDate() {

		Optional<ApplicationProperty> optionalProp = applicationPropertiesRepository.findById(ApplicationProperty.KEY_ENROLLMENT_END_DATE);
		
		if (optionalProp.isPresent()) {
			try {
				return Optional.of(DateFormat.getInstance().parse(optionalProp.get().getValue()));
			} catch (ParseException e) {
				return Optional.empty();
			}	
		}
		
		return Optional.empty();	
	}

	public void setEnrollmentEndDate(Date newEnrollmentEndDate) {
		
		ApplicationProperty newProp = new ApplicationProperty(
				ApplicationProperty.KEY_ENROLLMENT_END_DATE, 
				newEnrollmentEndDate.toString());
		applicationPropertiesRepository.save(newProp);
	}
	
	public ApplicationEnrollmentStatus getEnrollmentStatus() {
		
		Optional<ApplicationProperty> optionalProp = applicationPropertiesRepository.findById(ApplicationProperty.KEY_ENROLLMENT_STATUS);
		if (optionalProp.isPresent()) {
			return ApplicationEnrollmentStatus.valueOf(optionalProp.get().getValue());			
		}
		
		return ApplicationEnrollmentStatus.UNKNOWN;
	}
	
	public void setEnrollmentStatus(ApplicationEnrollmentStatus newStatus) {
		ApplicationProperty newProp = new ApplicationProperty(ApplicationProperty.KEY_ENROLLMENT_STATUS, newStatus.toString());
		applicationPropertiesRepository.save(newProp);
	}
	
	public void set(String key, String value) {
		if (!key.equals(ApplicationProperty.KEY_ENROLLMENT_END_DATE)
				&& !key.equals(ApplicationProperty.KEY_ENROLLMENT_STATUS)) {
			throw new IllegalArgumentException("Unknown key for Elective Courses Application property: " + key + "!");
		}
		ApplicationProperty newProp = new ApplicationProperty(key, value);
		applicationPropertiesRepository.save(newProp);
	}
	
	public ApplicationProperty get(String key) {
		if (!key.equals(ApplicationProperty.KEY_ENROLLMENT_END_DATE)
				&& !key.equals(ApplicationProperty.KEY_ENROLLMENT_STATUS)) {
			throw new IllegalArgumentException("Unknown key for Elective Courses Application property: " + key + "!");
		}
		Optional<ApplicationProperty> saved = applicationPropertiesRepository.findById(key);
		if (saved.isPresent()) {
			return saved.get();
		} 
		return new ApplicationProperty(key, "");
		
	}
	
}
