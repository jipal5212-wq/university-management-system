package edu.sharda.university.controller;

import edu.sharda.university.api.ApiModels.TeacherData;
import edu.sharda.university.model.Department;
import edu.sharda.university.model.Teacher;
import edu.sharda.university.repository.DepartmentRepository;
import edu.sharda.university.repository.TeacherRepository;
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
@RequestMapping("/api/teachers")
public class TeacherController {
    private final TeacherRepository teachers;
    private final DepartmentRepository departments;

    public TeacherController(TeacherRepository teachers, DepartmentRepository departments) {
        this.teachers = teachers;
        this.departments = departments;
    }

    @GetMapping
    public List<TeacherData> getAll() {
        return teachers.findAll().stream().map(TeacherController::toData).toList();
    }

    @GetMapping("/{id}")
    public TeacherData getById(@PathVariable Integer id) {
        return toData(findTeacher(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherData create(@Valid @RequestBody TeacherData data) {
        if (teachers.existsById(data.teacherId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Teacher ID already exists");
        }
        return toData(teachers.save(new Teacher(data.teacherId(), data.name(), data.designation(),
                data.experience(), data.salary(), findDepartmentOrNull(data.deptId()))));
    }

    @PutMapping("/{id}")
    public TeacherData update(@PathVariable Integer id, @Valid @RequestBody TeacherData data) {
        Teacher teacher = findTeacher(id);
        teacher.setName(data.name());
        teacher.setDesignation(data.designation());
        teacher.setExperience(data.experience());
        teacher.setSalary(data.salary());
        teacher.setDepartment(findDepartmentOrNull(data.deptId()));
        return toData(teachers.save(teacher));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        teachers.delete(findTeacher(id));
    }

    private Teacher findTeacher(Integer id) {
        return teachers.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found"));
    }

    private Department findDepartmentOrNull(Integer id) {
        if (id == null) return null;
        return departments.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));
    }

    private static TeacherData toData(Teacher teacher) {
        return new TeacherData(teacher.getTeacherId(), teacher.getName(), teacher.getDesignation(),
                teacher.getExperience(), teacher.getSalary(),
                teacher.getDepartment() == null ? null : teacher.getDepartment().getDeptId());
    }
}
