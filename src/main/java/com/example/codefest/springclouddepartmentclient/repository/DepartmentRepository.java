package com.example.codefest.springclouddepartmentclient.repository;

import com.example.codefest.springclouddepartmentclient.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "select d.department_id from  department d WHERE  d.department_code=:code",nativeQuery = true)
    Long getDepartmentByCode(@Param("code") String code);

    @Query(value = "select d.department_code from  department d WHERE  d.department_id=:id",nativeQuery = true)
    String getDepartmentCodeById(@Param("id") Long id);
}
