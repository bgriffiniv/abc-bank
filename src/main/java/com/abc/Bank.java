package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
	private List<Customer> customers;

	public Bank() {
		customers = new ArrayList<Customer>();
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	public String getCustomerSummary() {
		String summary = "Customer Summary";
		for (Customer c : customers)
			summary += "\n - " + c.getName() + " ("
					+ formatPlurality(c.getNumberOfAccounts(), "account") + ")";
		return summary;
	}

	// Make sure correct plural of word is created based on the number passed
	// in:
	// If number passed in is 1 just return the word otherwise add an 's' at the
	// end
	private String formatPlurality(int number, String word) {
		return number + " " + (number == 1 ? word : word + "s");
	}

	public double getTotalInterestPaid() {
		double total = 0;
		for (Customer c : customers)
			total += c.getTotalInterestEarned();
		return total;
	}

	public Customer getFirstCustomer() {
		try {
			// customers = null; // Why nullify the customers list???
			return customers.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
