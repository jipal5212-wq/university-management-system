package edu.sharda.university.controller;

import edu.sharda.university.api.ApiModels.CourseData;
import edu.sharda.university.model.Course;
import edu.sharda.university.model.Department;
import edu.sharda.university.repository.CourseRepository;
import edu.sharda.university.repository.DepartmentRepository;
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
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseRepository courses;
    private final DepartmentRepository departments;

    public CourseController(CourseRepository courses, DepartmentRepository departments) {
        this.courses = courses;
        this.departments = departments;
    }

    @GetMapping
    public List<CourseData> getAll() {
        return courses.findAll().stream().map(CourseController::toData).toList();
    }

    @GetMapping("/{id}")
    public CourseData getById(@PathVariable Integer id) {
        return toData(findCourse(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseData create(@Valid @RequestBody CourseData data) {
        if (courses.existsById(data.courseId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course ID already exists");
        }
        return toData(courses.save(new Course(data.courseId(), data.courseTitle(), data.credits(),
                findDepartmentOrNull(data.deptId()))));
    }

    @PutMapping("/{id}")
    public CourseData update(@PathVariable Integer id, @Valid @RequestBody CourseData data) {
        Course course = findCourse(id);
        course.setCourseTitle(data.courseTitle());
        course.setCredits(data.credits());
        course.setDepartment(findDepartmentOrNull(data.deptId()));
        return toData(courses.save(course));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        courses.delete(findCourse(id));
    }

    private Course findCourse(Integer id) {
        return courses.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
    }

    private Department findDepartmentOrNull(Integer id) {
        if (id == null) return null;
        return departments.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));
    }

    private static CourseData toData(Course course) {
        return new CourseData(course.getCourseId(), course.getCourseTitle(), course.getCredits(),
                course.getDepartment() == null ? null : course.getDepartment().getDeptId());
    }
}
