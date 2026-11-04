package edu.sharda.university.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public final class ApiModels {
    private ApiModels() {
    }

    public record DepartmentData(
            @NotNull Integer deptId,
            @NotBlank String deptName,
            String building,
            BigDecimal budget) {
    }

    public record TeacherData(
            @NotNull Integer teacherId,
            @NotBlank String name,
            String designation,
            Integer experience,
            BigDecimal salary,
            Integer deptId) {
    }

    public record StudentData(
            @NotNull Integer studentId,
            @NotBlank String name,
            String city,
            Integer admissionYear,
            BigDecimal cgpa,
            Integer deptId) {
    }

    public record CourseData(
            @NotNull Integer courseId,
            @NotBlank String courseTitle,
            Integer credits,
            Integer deptId) {
    }

    public record TeachesData(
            @NotNull Integer teacherId,
            @NotNull Integer courseId) {
    }

    public record EnrollmentData(
            @NotNull Integer studentId,
            @NotNull Integer courseId,
            BigDecimal marks,
            BigDecimal attendance) {
    }
}
