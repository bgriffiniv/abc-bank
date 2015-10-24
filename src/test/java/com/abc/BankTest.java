package com.abc;

import org.junit.Test;

import com.abc.Account.AccountType;

import static org.junit.Assert.assertEquals;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	public void customerSummary() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount("MY_CHECKING", AccountType.CHECKING);
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (1 account)",
				bank.getCustomerSummary());
	}

	@Test
	public void checkingAccount() {
		Bank bank = new Bank();
		final String billC = "BILL_CHECKING_1";
		Customer bill = new Customer("Bill").openAccount(billC,
				AccountType.CHECKING);
		bank.addCustomer(bill);

		bill.deposit(billC, 100.0);

		assertEquals(0.1, bank.getTotalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void savings_account() {
		Bank bank = new Bank();
		final String billS = "BILL_SAVINGS_1";
		bank.addCustomer(new Customer("Bill").openAccount(billS,
				AccountType.SAVINGS));
		bank.getFirstCustomer().deposit(billS, 1500.0);

		assertEquals(2.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void maxi_savings_legacy_account() {
		Bank bank = new Bank();
		final String billL = "Bill's Legacy Savings";
		bank.addCustomer(new Customer("Bill").openAccount(billL,
				AccountType.MAXI_SAVINGS_LEGACY));

		bank.getFirstCustomer().deposit(billL, 3000.0);

		assertEquals(170.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void maxi_savings_account() {
		Bank bank = new Bank();
		final String billM = "BILL_MAXI";
		bank.addCustomer(new Customer("Bill").openAccount(billM,
				AccountType.MAXI_SAVINGS));

		bank.getFirstCustomer().deposit(billM, 3000.0);

		assertEquals(3.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
	}

}
