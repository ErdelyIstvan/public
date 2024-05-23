package com.ierdely.elective_courses.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class ElectiveCourse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	int id;
	
	private String title;

	private byte maxAllowedStudents;
	
	//@Column(name="S_YEAR")
	private byte studyYear;
	
	//@JoinColumn(name="CAT_ID")
	//@ManyToOne(fetch = FetchType.LAZY)
	private String category;
	
	
	private String teacherName;

	public ElectiveCourse() {
		
	}
	
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte getMaxAllowedStudents() {
		return maxAllowedStudents;
	}

	public void setMaxAllowedStudents(byte maxAllowedStudents) {
		this.maxAllowedStudents = maxAllowedStudents;
	}

	public byte getStudyYear() {
		return studyYear;
	}

	public void setStudyYear(byte studyYear) {
		this.studyYear = studyYear;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	
}
