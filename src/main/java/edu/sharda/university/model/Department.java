package edu.sharda.university.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "department")
public class Department {
    @Id
    @Column(name = "dept_id")
    private Integer deptId;

    @Column(name = "dept_name", nullable = false, length = 100)
    private String deptName;

    @Column(length = 100)
    private String building;

    @Column(precision = 12, scale = 2)
    private BigDecimal budget;

    protected Department() {
    }

    public Department(Integer deptId, String deptName, String building, BigDecimal budget) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.building = building;
        this.budget = budget;
    }

    public Integer getDeptId() { return deptId; }
    public String getDeptName() { return deptName; }
    public String getBuilding() { return building; }
    public BigDecimal getBudget() { return budget; }
    public void setDeptName(String deptName) { this.deptName = deptName; }
    public void setBuilding(String building) { this.building = building; }
    public void setBudget(BigDecimal budget) { this.budget = budget; }
}
