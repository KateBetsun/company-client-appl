package telran.employees.net;

import java.util.Arrays;
import java.util.Iterator;

import telran.employees.*;
import telran.net.*;

public class CompanyProxy implements Company {
    TcpClient tcpClient;
    
	public CompanyProxy(TcpClient tcpClient) {
		this.tcpClient = tcpClient;
	}

	@Override
	public Iterator<Employee> iterator() {
		throw new UnsupportedOperationException("Iterator is not supported fro TCP proxy");
	}

	@Override
	public void addEmployee(Employee empl) {
		tcpClient.sendAndReceive(new Request("addEmployee", empl.getJSON()));
	}

	@Override
	public Employee getEmployee(long id) {
		String empl = tcpClient.sendAndReceive(new Request("getEmployee", String.valueOf(id)));
	    return (Employee) new Employee().setObject(empl);
	}

	@Override
	public Employee removeEmployee(long id) {
		String empl = tcpClient.sendAndReceive(new Request("removeEmployee", String.valueOf(id)));
		return (Employee) new Employee().setObject(empl);
	}

	@Override
	public int getDepartmentBudget(String department) {
		String budget = tcpClient.sendAndReceive(new Request("getDepartmentBudget", department));
		return Integer.parseInt(budget);
	}

	@Override
	public String[] getDepartments() {
		String departments = tcpClient.sendAndReceive(new Request("getDepartments", ""));
		return departments.split("; ");
	}

	@Override
	public Manager[] getManagersWithMostFactor() {
		String managersJSON = tcpClient.sendAndReceive(new Request("getManagersWithMostFactor", ""));
		
		return Arrays.stream(managersJSON.split(";"))
				.map(s -> (Manager)new Employee().setObject(s))
				.toArray(Manager[]::new);
	}

}
