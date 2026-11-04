package edu.sharda.university.controller;

import edu.sharda.university.api.ApiModels.TeachesData;
import edu.sharda.university.model.Course;
import edu.sharda.university.model.Teacher;
import edu.sharda.university.model.Teaches;
import edu.sharda.university.model.TeachesId;
import edu.sharda.university.repository.CourseRepository;
import edu.sharda.university.repository.TeacherRepository;
import edu.sharda.university.repository.TeachesRepository;
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
@RequestMapping("/api/teaches")
public class TeachesController {
    private final TeachesRepository teaches;
    private final TeacherRepository teachers;
    private final CourseRepository courses;

    public TeachesController(TeachesRepository teaches, TeacherRepository teachers, CourseRepository courses) {
        this.teaches = teaches;
        this.teachers = teachers;
        this.courses = courses;
    }

    @GetMapping
    public List<TeachesData> getAll() {
        return teaches.findAll().stream()
                .map(row -> new TeachesData(row.getId().getTeacherId(), row.getId().getCourseId()))
                .toList();
    }

    @GetMapping("/{teacherId}/{courseId}")
    public TeachesData getById(@PathVariable Integer teacherId, @PathVariable Integer courseId) {
        Teaches row = findTeaches(teacherId, courseId);
        return new TeachesData(row.getId().getTeacherId(), row.getId().getCourseId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeachesData create(@Valid @RequestBody TeachesData data) {
        TeachesId id = new TeachesId(data.teacherId(), data.courseId());
        if (teaches.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Teacher-course assignment already exists");
        }
        return toData(teaches.save(new Teaches(findTeacher(data.teacherId()), findCourse(data.courseId()))));
    }

    @PutMapping("/{teacherId}/{courseId}")
    public TeachesData update(@PathVariable Integer teacherId, @PathVariable Integer courseId) {
        TeachesId id = new TeachesId(teacherId, courseId);
        return teaches.findById(id).map(TeachesController::toData)
                .orElseGet(() -> toData(teaches.save(new Teaches(findTeacher(teacherId), findCourse(courseId)))));
    }

    @DeleteMapping("/{teacherId}/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer teacherId, @PathVariable Integer courseId) {
        teaches.delete(findTeaches(teacherId, courseId));
    }

    private Teaches findTeaches(Integer teacherId, Integer courseId) {
        return teaches.findById(new TeachesId(teacherId, courseId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher-course assignment not found"));
    }

    private Teacher findTeacher(Integer id) {
        return teachers.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found"));
    }

    private Course findCourse(Integer id) {
        return courses.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
    }

    private static TeachesData toData(Teaches row) {
        return new TeachesData(row.getId().getTeacherId(), row.getId().getCourseId());
    }
}
