package com.example.codefest.springclouddepartmentclient;

import com.example.codefest.springclouddepartmentclient.entity.Department;
import com.example.codefest.springclouddepartmentclient.entity.ResponseCode;
import com.example.codefest.springclouddepartmentclient.service.DepartmentService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
@WebMvcTest
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class DepartmentControllerTest {

    @Test
    void contextLoads() {
    }

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    ObjectMapper objectMapper;


    @MockBean
    private DepartmentService departmentService;

    @Test
    public void givenDepartmentObject_whenCreateDepartment_thenReturnSavedDepartment() throws Exception {

        // given - precondition or setup
        Department department = new Department(100L, "IS", "Block A", "IS2023");
        given(departmentService.saveDepartment(any(Department.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/v1/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(department)));

        JsonNode node = objectMapper.readTree(response.andReturn().getResponse().getContentAsString());
        Assert.assertEquals(Optional.ofNullable(ResponseCode.SUCCESS.getCode()),Optional.ofNullable(node.get("status").asInt()));

    }

    @Test
    public void givenListOfDepartments_whenGetAllDepartments_thenReturnDepartmentList() throws Exception {
        // given - precondition or setup
        List<Department> departments = new ArrayList<>();
        departments.add(new Department(100L, "IS", "Block A", "IS2023"));
        departments.add(new Department(101L, "CS", "Block C", "CS2023"));
        given(departmentService.fetchDepartmentList()).willReturn(departments);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/departments"));

        JsonNode node = objectMapper.readTree(response.andReturn().getResponse().getContentAsString());
        Assert.assertEquals(Optional.ofNullable(ResponseCode.SUCCESS.getCode()),Optional.ofNullable(node.get("status").asInt()));
        Assert.assertEquals(2,node.get("data").size());

    }


    @Test
    public void givenDepartmentId_whenGetDepartmentById_thenReturnDepartmentObject() throws Exception {
        // given - precondition or setup
        long departmentId = 101L;
        Department department = new Department(101L, "CS", "Block C", "CS2023");
        given(departmentService.getDepartmentDetails(departmentId)).willReturn(Optional.of(department).get());

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/departments/detailsfor/{id}", departmentId));
        JsonNode node = objectMapper.readTree(response.andReturn().getResponse().getContentAsString());
        Assert.assertEquals(Optional.ofNullable(ResponseCode.SUCCESS.getCode()),Optional.ofNullable(node.get("status").asInt()));
        Assert.assertEquals(101L,node.get("data").get("departmentId").asInt());
    }

    @Test
    public void givenInvalidDepartmentId_whenGetDepartmentById_thenReturnEmpty() throws Exception {
        // given - precondition or setup
        long departmentId = 1L;
        Department department = new Department(101L, "CS", "Block C", "CS2023");
        Department findDept = null;
        given(departmentService.getDepartmentDetails(departmentId)).willReturn(findDept);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/departments/detailsfor/{id}", departmentId));
        JsonNode node = objectMapper.readTree(response.andReturn().getResponse().getContentAsString());
        Assert.assertEquals(ResponseCode.NOT_FOUND.getCode(),
                node.isEmpty() ? ResponseCode.NOT_FOUND.getCode() : ResponseCode.SUCCESS.getCode());


    }

    @Test
    public void givenUpdatedDepartment_whenUpdateDepartment_thenReturnUpdateDepartmentObject() throws Exception {

        long departmentId = 101L;
        Department savedDepartment = new Department(101L, "CS", "Block C", "CS2023");

        Department updatedDepartment = new Department(101L, "CS", "Block D", "CS2023");

        given(departmentService.getDepartmentDetails(departmentId)).willReturn(updatedDepartment);
        ResultActions response = mockMvc.perform(put("/api/v1/departments/{id}", departmentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDepartment)));
        JsonNode node = objectMapper.readTree(response.andReturn().getResponse().getContentAsString());

        Assert.assertEquals(Optional.ofNullable(ResponseCode.SUCCESS.getCode()),Optional.ofNullable(node.get("status").asInt()));
    }

    @Test
    public void givenDepartmentId_whenDeleteDepartment_thenReturn200() throws Exception {
        long departmentId = 1L;
        willDoNothing().given(departmentService).deleteDepartmentById(departmentId);
        ResultActions response = mockMvc.perform(delete("/api/v1/departments/{id}", departmentId));

        Assert.assertEquals("Deleted Successfully",response.andReturn().getResponse().getContentAsString());
    }

}