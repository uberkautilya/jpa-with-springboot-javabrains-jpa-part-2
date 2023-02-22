package com.uberkautilya.jpawithspringboot.service;

import com.uberkautilya.jpawithspringboot.aspect.log;
import com.uberkautilya.jpawithspringboot.entity.AccessCard;
import com.uberkautilya.jpawithspringboot.entity.Employee;
import com.uberkautilya.jpawithspringboot.repository.AccessCardRepository;
import com.uberkautilya.jpawithspringboot.repository.EmployeeRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;

@Service
public class TransactionalDemo {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    AccessCardRepository accessCardRepository;

    /**
     * This method calls updateEmployee() which is another method annotated with @Transactional
     * This should not result in another transaction. If there is an existing transaction when calling updateEmployee(), it should continue within it.
     * Only if there is no existing prior transaction should a fresh transaction be started by updateEmployee()
     * Hence the need for transaction management - by default if there is existing transaction, will continue with it. No new one is created
     * @param employee
     * @param accessCard
     */
    @Transactional
    @log
    public void updateEmployeeAndAccessCard(Employee employee, AccessCard accessCard) {
        updateEmployee(employee);
        accessCardRepository.save(accessCard);
    }

    /**
     * @Transactional tells spring everything within the method should happen within a transaction
     * Starts a transaction at the beginning of the method, and commits at the end of it or throws exception if it fails
     * Note: Check if it comes from spring or jakarta or spring.
     * Also need to add @EnableTransactionManagement on the main class for it to work
     * rollbackOn property specifies explicitly which all exception are the sole cases for rollback. If not provided, rolled back for all exceptions
     * dontRollbackOn specifies those for which transactions shouldn't be rolled back
     *
     * value = REQUIRED directs that it requires a transaction, not necessarily new.
     * NOT_SUPPORTED means this method shouldn't be called as a part of any transaction
     * MANDATORY means a new transaction is not created by this method, however it needs it from its parent method
     * @param employee
     */
    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {SQLException.class, IOException.class},
            dontRollbackOn = NullPointerException.class)
    @log
    public void updateEmployee(Employee employee) {
        employee.setName("Updated Name");
        /*
         * Imperative transaction approach: Where explicitly the transaction has to be started, ended and rollbacks handled
         * SpringBoot's declarative approach: With the concept of proxies that wrap around methods - @Transactional annotation
         */
        employeeRepository.save(employee);
    }

    /**
     * Case for reading multiple databases within a transaction:
     * If reads are from multiple tables, if they are out of a single transaction, the values could change between reads on the tables
     * This inconsistency can be avoided when using a common transaction for fetching data from tables
     * readOnly is available only on the springframework.transaction.annotation.Transactional and not on jakarta
     * This doesn't block writes on the tables while this method is being executed. Also, there is a slight performance gain
     */
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public void readEmployeeAndAccessCards() {
        Iterable<Employee> employeeListIterable = employeeRepository.findAll();
        employeeListIterable.forEach(System.out::println);
        Iterable<AccessCard> accessCardListIterable = accessCardRepository.findAll();
        accessCardListIterable.forEach(System.out::println);
    }
}
