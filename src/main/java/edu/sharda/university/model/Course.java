package edu.sharda.university.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "course_title", nullable = false, length = 150)
    private String courseTitle;

    private Integer credits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Department department;

    protected Course() {
    }

    public Course(Integer courseId, String courseTitle, Integer credits, Department department) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.credits = credits;
        this.department = department;
    }

    public Integer getCourseId() { return courseId; }
    public String getCourseTitle() { return courseTitle; }
    public Integer getCredits() { return credits; }
    public Department getDepartment() { return department; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }
    public void setCredits(Integer credits) { this.credits = credits; }
    public void setDepartment(Department department) { this.department = department; }
}
