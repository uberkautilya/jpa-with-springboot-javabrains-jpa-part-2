package com.uberkautilya.jpawithspringboot;

import com.uberkautilya.jpawithspringboot.entity.Employee;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigInteger;
import java.util.Date;

@SpringBootApplication
public class JpaWithSpringbootApplication {
    /**
     * @PersistenceUnit gives more context to the dependency injection that the properties for the persistence unit are defined
     * Not just any bean, but the EntityManagerFactory defined in the persistence unit
     * As the beans are managed by Spring, needn't close it after persisting
     * Without spring - this cannot be a dependency injection. Need to get it from the Persistence.createEntityManagerFactory from the XML
     * If you need to persist, this is the way to do it
     */
    @PersistenceUnit
    EntityManagerFactory emFactory;

    /**
     * To get the persistence context directly. There are a few limitations
     * This is a shared entityManager. Used commonly in the entire application.
     * EntityManagers need to be thread safe especially when writing to DB
     * Thus the shared entity managers may be used for Reads, not for writes - cannot create transactions on the shared entityManager
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * type EXTENDED allows creation of context that extends across multiple transactions
     * JPA no longer ensures thread safety in this case
     * Here, the programmer needs to manage thread safety with flush etc. With this persist is allowed
     */
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager extendedManager;

    public static void main(String[] args) {
        SpringApplication.run(JpaWithSpringbootApplication.class, args);
    }

    /**
     * A method to run a method when the spring boot application starts @PostConstruct annotation
     * Once all the beans are initialized this method would be run
     * Typical mode to run a method is by accessing a REST endpoint
     */
    @PostConstruct
    public void start() {
        EntityManager eManager = emFactory.createEntityManager();
        EntityTransaction transaction = eManager.getTransaction();
        Employee e = new Employee();
        e.setAge(20);
        e.setName("Kautilya");
        e.setSsn("1234567");
        e.setDob(new Date());
        transaction.begin();
        eManager.persist(e);
        transaction.commit();
        eManager.close();


        Employee emp = new Employee();
        emp.setAge(20);
        emp.setName("Chanakya");
        emp.setSsn("1");
        emp.setDob(new Date());
        extendedManager.persist(emp);

        Employee employee = entityManager.find(Employee.class, BigInteger.valueOf(1));
        System.out.println(employee);
    }

}
