package edu.sharda.university.controller;

import edu.sharda.university.api.ApiModels.DepartmentData;
import edu.sharda.university.model.Department;
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
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentRepository departments;

    public DepartmentController(DepartmentRepository departments) {
        this.departments = departments;
    }

    @GetMapping
    public List<DepartmentData> getAll() {
        return departments.findAll().stream().map(DepartmentController::toData).toList();
    }

    @GetMapping("/{id}")
    public DepartmentData getById(@PathVariable Integer id) {
        return toData(findDepartment(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentData create(@Valid @RequestBody DepartmentData data) {
        if (departments.existsById(data.deptId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Department ID already exists");
        }
        return toData(departments.save(new Department(data.deptId(), data.deptName(), data.building(), data.budget())));
    }

    @PutMapping("/{id}")
    public DepartmentData update(@PathVariable Integer id, @Valid @RequestBody DepartmentData data) {
        Department department = findDepartment(id);
        department.setDeptName(data.deptName());
        department.setBuilding(data.building());
        department.setBudget(data.budget());
        return toData(departments.save(department));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        departments.delete(findDepartment(id));
    }

    private Department findDepartment(Integer id) {
        return departments.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));
    }

    private static DepartmentData toData(Department department) {
        return new DepartmentData(department.getDeptId(), department.getDeptName(), department.getBuilding(), department.getBudget());
    }
}
