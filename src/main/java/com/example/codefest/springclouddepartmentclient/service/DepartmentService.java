package com.example.codefest.springclouddepartmentclient.service;

import com.example.codefest.springclouddepartmentclient.entity.Department;

import java.util.List;

public interface DepartmentService {
    Department saveDepartment(Department department);

    List<Department> fetchDepartmentList();

    Department updateDepartment(Department department,Long departmentId);

    void deleteDepartmentById(Long departmentId);

    Long getDepartmentByCode(String departmentCode);

    String getDepartmentCodeById(Long departmentId);

    Department getDepartmentDetails(Long departmentId);
}
