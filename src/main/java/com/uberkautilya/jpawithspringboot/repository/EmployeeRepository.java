package com.uberkautilya.jpawithspringboot.repository;

import com.uberkautilya.jpawithspringboot.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * The generic CrudRepository takes in the type of the entity and its ID type
 * The CrudRepository provides all the crud operations
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, BigInteger> {

}
