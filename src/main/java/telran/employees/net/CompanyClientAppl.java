package telran.employees.net;

import java.util.*;

import telran.employees.CompanyApplItems;
import telran.net.TcpClient;
import telran.view.*;

public class CompanyClientAppl {
	private static final int PORT = 5000;

	public static void main(String[] args) {
		
		TcpClient tcpClient = new TcpClient("localhost", PORT);
		
		CompanyProxy companyProxy = new CompanyProxy(tcpClient);
		
		List<Item> items = CompanyApplItems.getCompanyItems(companyProxy,
				new HashSet<>(List.of(new String[] {"QA", "Audit", "Development"})));
		
		items.add(Item.of("Exit & connection close", io -> {
			try {
				tcpClient.close();
			} catch (Exception e) {		
				
			}
		}, true));
		Menu menu = new Menu("TCP Client Application", items.toArray(Item[]::new));
		menu.perform(new SystemInputOutput());

	}

}
