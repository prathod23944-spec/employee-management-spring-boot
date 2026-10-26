package com.example.employee_management.service;

import com.example.employee_management.entity.Employee;
import com.example.employee_management.repository.EmployeeRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeService employeeService;


	// Test 1: Get all employees
	@Test
	void getAllEmployees_shouldReturnEmployeeList() {

		Employee employee1 = new Employee();
		Employee employee2 = new Employee();

		when(employeeRepository.findAll())
				.thenReturn(List.of(employee1, employee2));

		List<Employee> result = employeeService.getAllEmployees();

		assertEquals(2, result.size());
		verify(employeeRepository).findAll();
	}


	// Test 2: Get employee by ID - found
	@Test
	void getEmployeeById_shouldReturnEmployeeWhenFound() {

		Long id = 1L;

		Employee employee = new Employee();
		employee.setId(id);

		when(employeeRepository.findById(id))
				.thenReturn(Optional.of(employee));

		Employee result = employeeService.getEmployeeById(id);

		assertNotNull(result);
		assertEquals(id, result.getId());

		verify(employeeRepository).findById(id);
	}


	// Test 3: Get employee by ID - not found
	@Test
	void getEmployeeById_shouldReturnNullWhenNotFound() {

		Long id = 99L;

		when(employeeRepository.findById(id))
				.thenReturn(Optional.empty());

		Employee result = employeeService.getEmployeeById(id);

		assertNull(result);

		verify(employeeRepository).findById(id);
	}


	// Test 4: Add employee
	@Test
	void addEmployee_shouldSaveAndReturnEmployee() {

		Employee employee = new Employee();

		when(employeeRepository.save(employee))
				.thenReturn(employee);

		Employee result = employeeService.addEmployee(employee);

		assertNotNull(result);
		assertSame(employee, result);

		verify(employeeRepository).save(employee);
	}


	// Test 5: Update existing employee
	@Test
	void updateEmployee_shouldUpdateEmployeeWhenFound() {

		Long id = 1L;

		Employee existingEmployee = new Employee();
		existingEmployee.setId(id);

		Employee employeeDetails = new Employee();

		when(employeeRepository.findById(id))
				.thenReturn(Optional.of(existingEmployee));

		when(employeeRepository.save(existingEmployee))
				.thenReturn(existingEmployee);

		Employee result =
				employeeService.updateEmployee(id, employeeDetails);

		assertNotNull(result);
		assertEquals(id, result.getId());

		verify(employeeRepository).findById(id);
		verify(employeeRepository).save(existingEmployee);
	}


	// Test 6: Update employee - not found
	@Test
	void updateEmployee_shouldReturnNullWhenNotFound() {

		Long id = 99L;

		when(employeeRepository.findById(id))
				.thenReturn(Optional.empty());

		Employee result =
				employeeService.updateEmployee(id, new Employee());

		assertNull(result);

		verify(employeeRepository).findById(id);
		verify(employeeRepository, never()).save(any(Employee.class));
	}


	// Test 7: Delete employee
	@Test
	void deleteEmployee_shouldDeleteEmployeeById() {

		Long id = 1L;

		employeeService.deleteEmployee(id);

		verify(employeeRepository).deleteById(id);
	}
}
