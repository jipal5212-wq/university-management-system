package edu.sharda.university.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TeachesId implements Serializable {
    @Column(name = "teacher_id")
    private Integer teacherId;

    @Column(name = "course_id")
    private Integer courseId;

    protected TeachesId() {
    }

    public TeachesId(Integer teacherId, Integer courseId) {
        this.teacherId = teacherId;
        this.courseId = courseId;
    }

    public Integer getTeacherId() { return teacherId; }
    public Integer getCourseId() { return courseId; }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof TeachesId that)) return false;
        return Objects.equals(teacherId, that.teacherId) && Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherId, courseId);
    }
}
