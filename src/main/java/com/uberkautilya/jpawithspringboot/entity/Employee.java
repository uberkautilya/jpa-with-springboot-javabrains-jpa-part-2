package com.uberkautilya.jpawithspringboot.entity;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Entity tells the JPA that it needs to make the ORM for it - a concern for it
 * @Table notifies it as a table, name being an optional attribute
 * Other options like schema, catalog(equivalent to schema in certain DBs)
 * @NamedQuery allows defining certain often repeated queries, called using its name later
 */

@Entity
@Table(name = "EMPLOYEE_DATA")
@NamedQuery(query = "select e from Employee e where e.age >= :minAge order by e.name", name = "emp name asc")
public class Employee {
    /**
     * @Id is used to specify the primary key column
     * @Id can be a String - however performance implications
     * long has precision issues - not recommended to have it as an ID - not a precise type
     * value you have and that which is saved might be off by a slight amount - which are still unequal
     * @GeneratedValue leverages the functionality of the DB to generate the value - but don't explicitly assign value to such fields
     * GenerationType.SEQUENCE maintains a separate object called sequence that the table maintains
     * GenerationType.TABLE creates a separate table to manage the unique values - some DBs allow sequences while others don't.
     * Typically, let JPA decide with GenerationType.AUTO
     */

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    /**
     * @Column can override default column mapping, though it is optional
     */
    @Column(unique = true, length = 10, nullable = false, updatable = false)
    private String ssn;
    @Column(name = "emp_name", length = 150)
    private String name;
    /**
     * @Basic is redundant practically - it says a primitive column mapping - the default behavior
     * Numeric values cannot be null on the Java side - int default value is 0, but can be on th DB side
     * insertable=false enforces that when inserted, the value is null - opposite to 'nullable = false'
     */
    @Basic
    @Column(insertable = true, updatable = true)
    private int age;

    /**
     * @Temporal is used in case of time units from java.util to specify the type of column to be mapped
     * It is a non-primitive type
     */
    @Temporal(TemporalType.DATE)
    private Date dob;

    /**
     * A non-primitive type - enum: By default JPA assigns the EnumType.ORDINAL of the value in the enum - i.e., integer value (0,1,2..)
     * This default behavior would result in data inconsistency if the enum order changes
     */
    @Enumerated(EnumType.STRING)
    private EmployeeType type;

    /**
     * @Transient tells the JPA not to save the value to the DB - variable only useful in the Java side: Counter to @Basic
     * Alternatively use private transient String notToBePersisted; - the variable itself would be transient, not just for purposes of JPA
     */
    @Transient
    private String notToBePersisted;

    /**
     * FetchType.EAGER is the default behavior. When LAZY, JPA fetches the accessCard only when it is explicitly used in the code
     */
    @OneToOne(fetch = FetchType.EAGER)
    private AccessCard accessCard;

    /**
     * Reverse of @ManyToOne on the employee entity. The PayStub owns the relationship - primary - as it contains the foreign key of employee entity
     * Default behavior of fetchType is LAZY by default
     * Initialization has been done to support the method addPayStub()
     * cascade = CascadeType.REMOVE ensures that when an Employee is removed, the associated payslips are also removed
     */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
    private List<PayStub> payStubList = new ArrayList<>();

    /**
     * To specify the relationship described by employeeList variable on the EmailGroup is the same, mark it as the same relation with mappedBy
     * The class which doesn't have mappedBy property is the owning side of the relationship - on which @JoinTable can be applied
     * If mappedBy is not provided in either class, two tables get created by JPA
     * An Employee is likely to have few EmailGroup as against it's corollary. Hence the default LAZY mode is overridden her with EAGER
     * joinColumns property can be used to customize the mapping table created. inverseJoinColumns specify the other foreign keys in the join table
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "EMAIL_GROUP_MAPPING",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMAIL_GROUP_ID"))
    private List<EmailGroup> emailGroupList = new ArrayList<>();

    public List<EmailGroup> getEmailGroupList() {
        return emailGroupList;
    }

    public void setEmailGroupList(List<EmailGroup> emailGroupList) {
        this.emailGroupList = emailGroupList;
    }

    public void addEmailGroup(EmailGroup emailGroup) {
        this.emailGroupList.add(emailGroup);
    }

    public AccessCard getAccessCard() {
        return accessCard;
    }

    public void setAccessCard(AccessCard accessCard) {
        this.accessCard = accessCard;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }

    public List<PayStub> getPayStubList() {
        return payStubList;
    }

    public void setPayStubList(List<PayStub> payStubList) {
        this.payStubList = payStubList;
    }

    public void addPayStub(PayStub payStub) {
        this.payStubList.add(payStub);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", ssn='" + ssn + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", dob=" + dob +
                ", type=" + type +
                ", notToBePersisted='" + notToBePersisted + '\'' +
                ", accessCard=" + accessCard +
                '}';
    }

    public String toStringWithNoAccessCard() {
        return "Employee{" +
                "id=" + id +
                ", ssn='" + ssn + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", dob=" + dob +
                ", type=" + type +
                ", notToBePersisted='" + notToBePersisted + '\'' +
                '}';
    }
}
