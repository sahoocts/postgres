package com.postgres.postgresExample.controller;

import com.postgres.postgresExample.exception.ResourceNotFoundException;
import com.postgres.postgresExample.model.Employee;
import com.postgres.postgresExample.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
private EmployeeRepository employeeRepository;

@PostMapping("/employees")
public Employee createEmployee(@RequestBody Employee emp){
    return employeeRepository.save(emp);
}

@GetMapping("/employees")
public List<Employee> getAll(){
    return employeeRepository.findAll();

}
@GetMapping("employee/{id}")
public ResponseEntity<Employee> getEmployeeById(@PathVariable(value= "id") Long employeeId)throws ResourceNotFoundException{
    Employee emp1=employeeRepository.findById(employeeId).orElseThrow(()->new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
    return ResponseEntity.ok().body(emp1);
}

@DeleteMapping("employees/{id}")
public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("id") Long id) {
    try{
        employeeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    catch(Exception e){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    }

}
