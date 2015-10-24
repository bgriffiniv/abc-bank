package com.abc;

import static java.lang.Math.abs;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.abc.Account.AccountType;

public class Customer {
	private String name;
	private Map<String, Account> accounts;

	public Customer(String name) {
		this.name = name;
		this.accounts = new HashMap<String, Account>();
	}

	public String getName() {
		return name;
	}

	public Map<String, Account> getAccounts() {
		return accounts;
	}

	public Customer openAccount(String accountName, AccountType accountType) {
		if (accounts.containsKey(accountName)) {
			throw new IllegalArgumentException(
					"account with that name already exists");
		}
		accounts.put(accountName, new Account(accountType));
		return this;
	}

	public Customer closeAccount(String accountName) {
		accounts.remove(accountName);
		return this;
	}

	public int getNumberOfAccounts() {
		return accounts.size();
	}

	public double getTotalInterestEarned() {
		double total = 0;
		if (!accounts.isEmpty()) {
			for (Account a : accounts.values()) {
				total += a.getInterestEarned();
			}
		}
		return total;
	}

	public String getStatement() {
		StringBuilder statement = new StringBuilder();
		statement.append("Statement for " + name + "\n");
		double total = 0.0;
		if (!accounts.isEmpty()) {
			for (Entry<String, Account> entry : accounts.entrySet()) {
				statement.append("\n" + entry.getKey() + " -- "
						+ statementForAccount(entry.getValue()) + "\n");
				total += entry.getValue().sumTransactions();
			}
		}
		statement.append("\nTotal In All Accounts " + toDollars(total, true));
		return statement.toString();
	}

	private String statementForAccount(Account a) {
		StringBuilder s = new StringBuilder();

		// Translate to pretty account type
		switch (a.getAccountType()) {
		case CHECKING:
			s.append("Checking Account\n");
			break;
		case SAVINGS:
			s.append("Savings Account\n");
			break;
		case MAXI_SAVINGS:
			s.append("Maxi Savings Account\n");
			break;
		case MAXI_SAVINGS_LEGACY:
			s.append("Maxi Savings Account(Legacy)\n");
			break;
		}

		// Now total up all the transactions
		double total = 0.0;
		if (!a.getTransactions().isEmpty()) {
			for (Transaction t : a.getTransactions()) {
				s.append("  " + (t.getAmount() < 0 ? "withdrawal" : "deposit")
						+ " " + toDollars(t.getAmount()) + "\n");
				total += t.getAmount();
			}
		}
		s.append("Total " + toDollars(total, true));
		return s.toString();
	}

	private String toDollars(double d) {
		return toDollars(d, false);
	}

	private String toDollars(double d, boolean isBalance) {
		StringBuilder s = new StringBuilder();
		// do something for negative account balances
		if (isBalance && d < 0) {
			s.append("-");
		}
		return s.append(String.format("$%,.2f", abs(d))).toString();
	}

	public Customer transfer(String from, String to, double amount) {
		withdraw(from, amount);
		deposit(to, amount);

		// does not account for overdraft

		return this;
	}

	public Customer deposit(String to, double amount) {
		if (!accounts.containsKey(to)) {
			throw new IllegalArgumentException("TO account does not exists");
		}
		accounts.get(to).deposit(amount);

		return this;
	}

	public Customer withdraw(String from, double amount) {
		if (!accounts.containsKey(from)) {
			throw new IllegalArgumentException("FROM account does not exists");
		}
		accounts.get(from).withdraw(amount);

		return this;
	}

}
