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
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "student_id")
    private Integer studentId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String city;

    @Column(name = "admission_year")
    private Integer admissionYear;

    @Column(precision = 4, scale = 2)
    private BigDecimal cgpa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Department department;

    protected Student() {
    }

    public Student(Integer studentId, String name, String city, Integer admissionYear, BigDecimal cgpa, Department department) {
        this.studentId = studentId;
        this.name = name;
        this.city = city;
        this.admissionYear = admissionYear;
        this.cgpa = cgpa;
        this.department = department;
    }

    public Integer getStudentId() { return studentId; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public Integer getAdmissionYear() { return admissionYear; }
    public BigDecimal getCgpa() { return cgpa; }
    public Department getDepartment() { return department; }
    public void setName(String name) { this.name = name; }
    public void setCity(String city) { this.city = city; }
    public void setAdmissionYear(Integer admissionYear) { this.admissionYear = admissionYear; }
    public void setCgpa(BigDecimal cgpa) { this.cgpa = cgpa; }
    public void setDepartment(Department department) { this.department = department; }
}
