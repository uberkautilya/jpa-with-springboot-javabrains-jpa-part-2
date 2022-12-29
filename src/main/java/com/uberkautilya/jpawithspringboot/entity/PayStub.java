package com.uberkautilya.jpawithspringboot.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class PayStub {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date payPeriodStart;
    private Date payPeriodEnd;
    private float salary;
    /**
     * @JoinColumn is used to specify the name of the foreign key that needed on the DB side.
     * Note the typical @Column annotation is replaced by this on a @ManyToOne relationship mapping
     * cascade property to remove is not set on employee field - as when a payStub is removed, the employee should not be deleted
     */
    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;

    public PayStub() {
    }
    public PayStub(Date payPeriodStart, Date payPeriodEnd, float salary) {
        this.payPeriodStart = payPeriodStart;
        this.payPeriodEnd = payPeriodEnd;
        this.salary = salary;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPayPeriodStart() {
        return payPeriodStart;
    }

    public void setPayPeriodStart(Date payPeriodStart) {
        this.payPeriodStart = payPeriodStart;
    }

    public Date getPayPeriodEnd() {
        return payPeriodEnd;
    }

    public void setPayPeriodEnd(Date payPeriodEnd) {
        this.payPeriodEnd = payPeriodEnd;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "PayStub{" +
                "id=" + id +
                ", payPeriodStart=" + payPeriodStart +
                ", payPeriodEnd=" + payPeriodEnd +
                ", salary=" + salary +
                ", employee=" + employee +
                '}';
    }
}
