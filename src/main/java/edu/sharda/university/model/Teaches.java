package edu.sharda.university.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "teaches")
public class Teaches {
    @EmbeddedId
    private TeachesId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("teacherId")
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    protected Teaches() {
    }

    public Teaches(Teacher teacher, Course course) {
        this.id = new TeachesId(teacher.getTeacherId(), course.getCourseId());
        this.teacher = teacher;
        this.course = course;
    }

    public TeachesId getId() { return id; }
    public Teacher getTeacher() { return teacher; }
    public Course getCourse() { return course; }
}
