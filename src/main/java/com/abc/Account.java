package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Account {

	public enum AccountType {
		CHECKING, SAVINGS, MAXI_SAVINGS, MAXI_SAVINGS_LEGACY
	};

	// private final int accountType;
	private final AccountType accountType;
	private List<Transaction> transactions;

	public Account(AccountType accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
		}
		transactions.add(new Transaction(amount));
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
		}
		transactions.add(new Transaction(-amount));
	}

	public double getInterestEarned() {
		double amount = sumTransactions();
		double interest = 0;

		if (amount > 0) {
			switch (accountType) {
			case CHECKING:
				interest += amount * 0.001;
				break;
			case SAVINGS:
				if (amount <= 1000)
					interest += amount * 0.001;
				else
					interest += 1 + (amount - 1000) * 0.002;
				break;
			// case SUPER_SAVINGS:
			// if (amount <= 4000)
			// return 20;
			case MAXI_SAVINGS_LEGACY:
				if (amount <= 1000)
					interest += amount * 0.02;
				if (amount <= 2000)
					interest += 20 + (amount - 1000) * 0.05;
				interest += 70 + (amount - 2000) * 0.1;
				break;
			case MAXI_SAVINGS:
				// get date of latest transaction
				Calendar latestCal = Calendar.getInstance();
				latestCal.setTime(getLatestTransaction().getTransactionDate());

				// get date of ten days ago
				Calendar tenDaysAgoCal = Calendar.getInstance();
				tenDaysAgoCal.setTime(DateProvider.getInstance().now());
				tenDaysAgoCal.add(Calendar.DAY_OF_MONTH, -10);

				// if the last transaction occurred at least ten days ago...
				if (latestCal.before(tenDaysAgoCal)) {
					interest += amount * 0.05;
				} else {
					interest += amount * 0.001;
				}

				break;
			default:
				interest += amount * 0.001;
			}
		}
		return interest;
	}

	public Transaction getLatestTransaction() {
		// may not need sorting because transactions automatically occur on
		// "today"
		// Collections.sort(transactions);

		// get the last transaction; this should be the most recent
		return transactions.get(transactions.size() - 1);
	}

	public double sumTransactions() {
		double total = 0;
		for (Transaction t : transactions) {
			total += t.getAmount();
		}
		return total;
	}

	public void accrueInterest() {
		// daily
		deposit(getInterestEarned());
	}

}
