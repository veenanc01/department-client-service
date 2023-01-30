package com.example.codefest.springclouddepartmentclient.controller;

import com.example.codefest.springclouddepartmentclient.entity.Department;
import com.example.codefest.springclouddepartmentclient.entity.Response;
import com.example.codefest.springclouddepartmentclient.entity.ResponseCode;
import com.example.codefest.springclouddepartmentclient.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Response saveDepartment(
            @RequestBody Department department)
    {
         Response response = null;

         Department department1 = departmentService.saveDepartment(department);
         response =  new Response(ResponseCode.SUCCESS.getCode(), "Department details saved successfully",department1);
         return response;
    }

    // Read operation
    @GetMapping("/departments")
    public Response fetchDepartmentList()
    {
        Response response =null;
        List<Department> list = departmentService.fetchDepartmentList();
        response =  new Response(ResponseCode.SUCCESS.getCode(),
                "Department details fetched successfully",list);
        return response;
    }

    @GetMapping("/departments/detailsfor/{id}")
    public Response fetchDepartmentById(@PathVariable("id") Long id)
    {
        Response response =null;
        if(null != id) {
            Department department = departmentService.getDepartmentDetails(id);
            if (null != department) {
                response = new Response(ResponseCode.SUCCESS.getCode(),
                        "Department details fetched successfully", department);
            } else {
                response = new Response(ResponseCode.NOT_FOUND.getCode(),
                        "Department details not found", department);
            }
        } else {
            response = new Response(ResponseCode.INTERNAL_ERROR.getCode(),
                    "Provide valid department id", null);
        }
        return response;
    }

    // Update operation
    @PutMapping("/departments/{id}")
    public Response
    updateDepartment(@RequestBody Department department,
                     @PathVariable("id") Long departmentId)
    {
        Response response =null;
        Department department1 = departmentService.updateDepartment(
                department, departmentId);
        response = new Response(ResponseCode.SUCCESS.getCode(), "Department details updated successfully",department1);
        return response;
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

    @PostMapping("/departments/{deptCode}")
    public Long getDepartmentByCode(@PathVariable("deptCode")
                                       String deptCode)
    {

        return departmentService.getDepartmentByCode(
                deptCode);
    }
    @GetMapping("/departments/{deptId}")
    public String getDepartmentByDeptId(@PathVariable("deptId")
                                    Long deptId)
    {

        return departmentService.getDepartmentCodeById(
                deptId);
    }




}
