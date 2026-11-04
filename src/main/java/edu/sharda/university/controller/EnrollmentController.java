package edu.sharda.university.controller;

import edu.sharda.university.api.ApiModels.EnrollmentData;
import edu.sharda.university.model.Course;
import edu.sharda.university.model.Enrollment;
import edu.sharda.university.model.EnrollmentId;
import edu.sharda.university.model.Student;
import edu.sharda.university.repository.CourseRepository;
import edu.sharda.university.repository.EnrollmentRepository;
import edu.sharda.university.repository.StudentRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentRepository enrollments;
    private final StudentRepository students;
    private final CourseRepository courses;

    public EnrollmentController(EnrollmentRepository enrollments, StudentRepository students, CourseRepository courses) {
        this.enrollments = enrollments;
        this.students = students;
        this.courses = courses;
    }

    @GetMapping
    public List<EnrollmentData> getAll() {
        return enrollments.findAll().stream().map(EnrollmentController::toData).toList();
    }

    @GetMapping("/{studentId}/{courseId}")
    public EnrollmentData getById(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        return toData(findEnrollment(studentId, courseId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentData create(@Valid @RequestBody EnrollmentData data) {
        EnrollmentId id = new EnrollmentId(data.studentId(), data.courseId());
        if (enrollments.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Enrollment already exists");
        }
        return toData(enrollments.save(new Enrollment(findStudent(data.studentId()), findCourse(data.courseId()),
                data.marks(), data.attendance())));
    }

    @PutMapping("/{studentId}/{courseId}")
    public EnrollmentData update(@PathVariable Integer studentId, @PathVariable Integer courseId,
                                 @Valid @RequestBody EnrollmentData data) {
        EnrollmentId id = new EnrollmentId(studentId, courseId);
        Enrollment enrollment = enrollments.findById(id)
                .orElseGet(() -> new Enrollment(findStudent(studentId), findCourse(courseId), data.marks(), data.attendance()));
        enrollment.setMarks(data.marks());
        enrollment.setAttendance(data.attendance());
        return toData(enrollments.save(enrollment));
    }

    @DeleteMapping("/{studentId}/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        enrollments.delete(findEnrollment(studentId, courseId));
    }

    private Enrollment findEnrollment(Integer studentId, Integer courseId) {
        return enrollments.findById(new EnrollmentId(studentId, courseId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found"));
    }

    private Student findStudent(Integer id) {
        return students.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    private Course findCourse(Integer id) {
        return courses.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
    }

    private static EnrollmentData toData(Enrollment enrollment) {
        return new EnrollmentData(enrollment.getId().getStudentId(), enrollment.getId().getCourseId(),
                enrollment.getMarks(), enrollment.getAttendance());
    }
}
