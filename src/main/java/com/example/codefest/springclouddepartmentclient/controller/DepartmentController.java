package com.example.codefest.springclouddepartmentclient.controller;

import com.example.codefest.springclouddepartmentclient.entity.Department;
import com.example.codefest.springclouddepartmentclient.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/api/v1")
public class DepartmentController {

    @Autowired
    private Environment env;

    @Autowired
    private DepartmentService departmentService;


    @GetMapping("/hello")
    public ResponseEntity<String> getHello(Model model) {
        return new ResponseEntity<String>( env.getProperty("message"), HttpStatus.OK);
    }

  // Save operation
    @PostMapping("/departments")
    public Department saveDepartment(
            @RequestBody Department department)
    {
        return departmentService.saveDepartment(department);
    }

    // Read operation
    @GetMapping("/departments")
    public List<Department> fetchDepartmentList()
    {
        return departmentService.fetchDepartmentList();
    }

    // Update operation
    @PutMapping("/departments/{id}")
    public Department
    updateDepartment(@RequestBody Department department,
                     @PathVariable("id") Long departmentId)
    {
        return departmentService.updateDepartment(
                department, departmentId);
    }

    // Delete operation
    @DeleteMapping("/departments/{id}")
    public String deleteDepartmentById(@PathVariable("id")
                                       Long departmentId)
    {
        departmentService.deleteDepartmentById(
                departmentId);
        return "Deleted Successfully";
    }




}
