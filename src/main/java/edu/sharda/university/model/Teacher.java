package edu.sharda.university.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @Column(name = "teacher_id")
    private Integer teacherId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String designation;

    private Integer experience;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Department department;

    protected Teacher() {
    }

    public Teacher(Integer teacherId, String name, String designation, Integer experience, BigDecimal salary, Department department) {
        this.teacherId = teacherId;
        this.name = name;
        this.designation = designation;
        this.experience = experience;
        this.salary = salary;
        this.department = department;
    }

    public Integer getTeacherId() { return teacherId; }
    public String getName() { return name; }
    public String getDesignation() { return designation; }
    public Integer getExperience() { return experience; }
    public BigDecimal getSalary() { return salary; }
    public Department getDepartment() { return department; }
    public void setName(String name) { this.name = name; }
    public void setDesignation(String designation) { this.designation = designation; }
    public void setExperience(Integer experience) { this.experience = experience; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
    public void setDepartment(Department department) { this.department = department; }
}
