package com.uberkautilya.jpawithspringboot.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class EmailGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    /**
     * If you need to track the relationship in a single join table, need mappedBy in either of the 2 classes - EmailGroup or Employee
     * The below annotation shows that each of the Employee objects has a field emailGroupList which tracks it's email groups
     * An email group is likely to have many employees. Hence fetching employees via the default LAZY fetchType
     */
    @ManyToMany(mappedBy = "emailGroupList", fetch = FetchType.LAZY)
    private List<Employee> employeeList = new ArrayList<>();

    public EmailGroup() {
    }

    public EmailGroup(String name) {
        this.name = name;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public void addEmployee(Employee employee) {
        this.employeeList.add(employee);
    }

    @Override
    public String toString() {
        return "EmailGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
