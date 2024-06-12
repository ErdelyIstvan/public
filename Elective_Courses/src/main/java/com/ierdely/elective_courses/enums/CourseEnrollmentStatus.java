package com.ierdely.elective_courses.enums;

public enum CourseEnrollmentStatus { 

	PRELIMINARY,
	PREFERENCE,
	ALTERNATIVE_PREFERENCE,
	FINAL,
	UNKNOWN;
	
	public String toString() {

	    // this will refer to the object SMALL
	    switch(this) {
	      case PRELIMINARY:
	        return "PRELIMINARY";

	      case PREFERENCE:
	        return "PREFERENCE";

	      case ALTERNATIVE_PREFERENCE:
	        return "ALTERNATIVE_PREFERENCE";

	      case FINAL:
	        return "FINAL";

	      default:
	        return "UNKNOWN";
	      }
	   }

}
