package com.crud.example.cruds.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.example.cruds.model.Employee;


@Repository
public interface EmpRepo extends JpaRepository<Employee,Long> {
}

