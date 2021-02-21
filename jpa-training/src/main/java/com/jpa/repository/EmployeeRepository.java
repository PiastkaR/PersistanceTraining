package com.jpa.repository;

import com.jpa.model.Employee;
import com.jpa.model.FullTimeEmployee;
import com.jpa.model.PartTimeEmployee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Transactional
@Slf4j
public class EmployeeRepository {
    private final EntityManager entityManager;

    public void insert(Employee employee) {
        entityManager.persist(employee);
    }

//    public List<Employee> retrieveAllEmployees() {
//        return entityManager.createQuery("select e from Employee e", Employee.class).getResultList();
//    }
    public List<PartTimeEmployee> retrieveAllPartTimeEmployees() {
        return entityManager.createQuery("select e from PartTimeEmployee e", PartTimeEmployee.class).getResultList();
    }
    public List<FullTimeEmployee> retrieveAllFullTimeEmployees() {
        return entityManager.createQuery("select e from FullTimeEmployee e", FullTimeEmployee.class).getResultList();
    }
}
