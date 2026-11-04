package edu.sharda.university.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "enrollment")
public class Enrollment {
    @EmbeddedId
    private EnrollmentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(precision = 5, scale = 2)
    private BigDecimal marks;

    @Column(precision = 5, scale = 2)
    private BigDecimal attendance;

    protected Enrollment() {
    }

    public Enrollment(Student student, Course course, BigDecimal marks, BigDecimal attendance) {
        this.id = new EnrollmentId(student.getStudentId(), course.getCourseId());
        this.student = student;
        this.course = course;
        this.marks = marks;
        this.attendance = attendance;
    }

    public EnrollmentId getId() { return id; }
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public BigDecimal getMarks() { return marks; }
    public BigDecimal getAttendance() { return attendance; }
    public void setMarks(BigDecimal marks) { this.marks = marks; }
    public void setAttendance(BigDecimal attendance) { this.attendance = attendance; }
}
