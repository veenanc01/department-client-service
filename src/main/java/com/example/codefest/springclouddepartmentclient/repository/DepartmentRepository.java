package com.example.codefest.springclouddepartmentclient.repository;

import com.example.codefest.springclouddepartmentclient.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
