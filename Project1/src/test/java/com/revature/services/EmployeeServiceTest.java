package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.revature.beans.Employee;
import com.revature.beans.EmployeeType;
import com.revature.data.EmployeeDAO;

public class EmployeeServiceTest {

	private EmployeeService service = null;
	private Employee employee = null;
	private EmployeeDAO ed = null;

	@BeforeEach
	public void setupEach() {
		employee = new Employee("Test_employee", "test_password", "test@test.com", "Test", "Employee", "johnnyTest",
				"Testing_department", false, EmployeeType.REGEMPLOYEE);
		ed = Mockito.mock(EmployeeDAO.class);
		service = new EmployeeServiceImpl();
	}

	@Test
	public void testingLogin() {
		Mockito.when(ed.getEmployeeByName(employee.getUsername(), employee.getPassword())).thenReturn(employee);
		
		ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
		Mockito.verify(ed).getEmployeeByName(usernameCaptor.capture(), passwordCaptor.capture());
		assertEquals(employee.getUsername(), usernameCaptor.getValue().toString());
		assertEquals(employee.getPassword(), passwordCaptor.getValue().toString());
	}

}
