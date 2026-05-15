package net.javaguides.springboot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenNothing() {
        // given - setup or precondition
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john@gmail.com");

        given(employeeRepository.save(employee)).willReturn(employee);

        // when - action or the behaviour that we are going test
        employeeService.saveEmployee(employee);

        // then - verify the output
        verify(employeeRepository, times(1)).save(employee);
    }

    @DisplayName("JUnit test for getAllEmployees method")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {
        // given
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john@gmail.com");

        given(employeeRepository.findAll()).willReturn(List.of(employee));

        // when
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(1);
    }

    @DisplayName("JUnit test for getAllEmployees method (negative scenario)")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
        // given
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getEmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        // given
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john@gmail.com");

        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when
        Employee savedEmployee = employeeService.getEmployeeById(1L);

        // then
        assertThat(savedEmployee).isNotNull();
    }

    @DisplayName("JUnit test for deleteEmployeeById method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        // given
        long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // when
        employeeService.deleteEmployeeById(employeeId);

        // then
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}
