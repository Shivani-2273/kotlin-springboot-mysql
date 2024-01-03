package com.example.demo.controller

import com.example.demo.model.Employee
import com.example.demo.service.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/employees")
class EmployeeController(private val employeeService: EmployeeService) {

    @GetMapping
    fun getAllEmployees(): List<Employee> = employeeService.getAllEmployee()

    @GetMapping("/{id}")
    fun getEmployeeById(@PathVariable id: Long): ResponseEntity<Employee>{
        val employee = employeeService.getEmployeeById(id)
        return if (employee != null){
            ResponseEntity.ok(employee)
        }else{
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createEmployee(@RequestBody employee: Employee): ResponseEntity<Employee>{
        val createdEmployee = employeeService.createEmployee(employee)
        return ResponseEntity.ok().body(createdEmployee)
    }

    @PutMapping("/{id}")
    fun updateEmployee(@PathVariable id: Long, @RequestBody employee: Employee): ResponseEntity<Any> {
        return try {
            val updatedEmployee = employeeService.updateEmployee(id, employee)
            ResponseEntity.ok(updatedEmployee)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found with ID: $id")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable id:Long): ResponseEntity<Void>{
        employeeService.deleteEmployee(id)
        return ResponseEntity.ok().build()
    }
}
