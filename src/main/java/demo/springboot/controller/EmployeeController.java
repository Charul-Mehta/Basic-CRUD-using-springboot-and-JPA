package demo.springboot.controller;


import demo.springboot.exception.ResourceNotFoundException;
import demo.springboot.model.Employee;
import demo.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // get employee
    @GetMapping("employees")
    public List<Employee> getAllEmployees(){
        System.out.println("In get");
        return this.employeeRepository.findAll();
    }

    // get employee by id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException {
        Employee emp= this.employeeRepository.findById(employeeId).orElseThrow(()-> new ResourceNotFoundException(" Employee is not found with Id:"+employeeId));

        return ResponseEntity.ok(emp);
    }
    // save employee
    @PostMapping("employees")
    public Employee createEmployee(@RequestBody Employee employee){
        System.out.println("In post");
        return this.employeeRepository.save(employee);

    }

    // update employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId, @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee emp= this.employeeRepository.findById(employeeId).orElseThrow(()-> new ResourceNotFoundException(" Employee is not found with Id:"+employeeId));

        emp.setEmail(employeeDetails.getEmail());
        emp.setFirstName(employeeDetails.getFirstName());
        emp.setLastName(employeeDetails.getLastName());

        return ResponseEntity.ok(this.employeeRepository.save(emp));
    }

    // delete employee
    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException {
        Employee emp = this.employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException(" Employee is not found with Id:" + employeeId));
        this.employeeRepository.delete(emp);
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("deleted", Boolean.TRUE);

        return map;
    }

}
