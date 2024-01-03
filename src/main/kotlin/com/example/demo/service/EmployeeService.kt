package com.example.demo.service

import com.example.demo.model.Employee
import com.example.demo.repository.EmployeeRepository
import org.springframework.stereotype.Service

@Service
class EmployeeService(private val employeeRepository: EmployeeRepository) {


    fun getAllEmployee(): List<Employee> = employeeRepository.findAll()

    fun getEmployeeById(id: Long): Employee? = employeeRepository.findById(id).orElse(null)

    fun createEmployee(employee: Employee): Employee = employeeRepository.save(employee)

    fun updateEmployee(id: Long, employee: Employee): Employee {
        val existingEmployee = employeeRepository.findById(id)
            .orElseThrow { NoSuchElementException("Employee not found with ID: $id") }

        require(id == employee.id) { "Employee Id mismatch" }

        // Update other fields of the existingEmployee entity with the new data
        existingEmployee.name = employee.name
        existingEmployee.email = employee.email
        return employeeRepository.save(existingEmployee)
    }

    fun deleteEmployee(id: Long){
        employeeRepository.deleteById(id)
    }
}
