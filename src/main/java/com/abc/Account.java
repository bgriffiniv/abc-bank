package com.abc;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import javax.swing.text.DateFormatter;

public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;
	public static final int MAXI_SAVINGS_LEGACY = 3;

	private final int accountType;
	private List<Transaction> transactions;

	public Account(int accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount));
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
		} else {
			transactions.add(new Transaction(-amount));
		}
	}

	public double interestEarned() {
		double amount = sumTransactions();
		double interest = 0;
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
			Calendar latestCal = Calendar.getInstance();
			latestCal.setTime(getLatestTransaction().getTransactionDate());
			Calendar tenDaysAgoCal = Calendar.getInstance();
			tenDaysAgoCal.setTime(DateProvider.getInstance().now());
			tenDaysAgoCal.add(Calendar.DAY_OF_MONTH, -10);

			if (latestCal.after(tenDaysAgoCal)) {
				interest += amount * 0.05;
			} else {
				interest += amount * 0.001;
			}

			break;
		default:
			interest += amount * 0.001;
		}

		return interest;
	}

	public Transaction getLatestTransaction() {
		/*
		 * // may not need sorting transactions.sort(new
		 * Comparator<Transaction>() { public int compare(Transaction o1,
		 * Transaction o2) { int res = o1.compareTo(o2); if (res == 0 ||
		 * !o1.equals(o2)) { return o1.hashCode() - o2.hashCode(); } return res;
		 * } });
		 */

		return transactions.get(transactions.size() - 1); // get latest
															// transaction
	}

	public double sumTransactions() {
		double total = 0;
		if (checkIfTransactionsExist()) {
			for (Transaction t : transactions) {
				total += t.getAmount();
			}
		}
		return total;
	}

	private boolean checkIfTransactionsExist() {
		return !transactions.isEmpty();
	}

	public int getAccountType() {
		return accountType;
	}

}
