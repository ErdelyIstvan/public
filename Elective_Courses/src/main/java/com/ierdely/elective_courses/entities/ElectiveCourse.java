package com.ierdely.elective_courses.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="T_E_COURSE")
@Getter
@Setter
@AllArgsConstructor
public class ElectiveCourse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	@Column(name="ID")
	int id;
	
	@Column(name="TITLE")
	private String title;

	@Column(name="MAX_A")
	private byte maxAllowedStudents;
	
	@Column(name="S_YEAR")
	private byte studyYear;
	
	@JoinColumn(name="CAT_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private CourseCategory category;
	
	@Column(name="TEACHER")
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

	public CourseCategory getCategory() {
		return category;
	}

	public void setCategory(CourseCategory category) {
		this.category = category;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	
}
