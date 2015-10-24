package com.abc;

import org.junit.Test;

import com.abc.Account.AccountType;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

	@Test
	// Test customer statement generation
	public void testApp() {

		final String henryc = "HENRY_C";
		final String henrys = "HENRY_S";

		Customer henry = new Customer("Henry").openAccount(henryc,
				AccountType.CHECKING).openAccount(henrys, AccountType.SAVINGS);

		// don't use the Accounts to deposit/withdraw; use the Customer

		henry.deposit(henryc, 100.0);
		henry.deposit(henrys, 4000.0);
		henry.withdraw(henrys, 200);

		System.out.println(henry.getStatement());

		assertEquals("Statement for Henry\n" + "\n"
				+ "HENRY_S -- Savings Account\n" + "  deposit $4,000.00\n"
				+ "  withdrawal $200.00\n" + "Total $3,800.00\n" + "\n"
				+ "HENRY_C -- Checking Account\n" + "  deposit $100.00\n"
				+ "Total $100.00\n" + "\n" + "Total In All Accounts $3,900.00",
				henry.getStatement());

		final String jessicac = "Jessica_Checking";
		final String jessicas = "Jessica_Savings";

		Customer jessica = new Customer("Jessica").openAccount(jessicac,
				AccountType.CHECKING)
				.openAccount(jessicas, AccountType.SAVINGS);

		jessica.deposit(jessicac, 300);
		jessica.withdraw(jessicac, 200);
		jessica.withdraw(jessicac, 200);
		jessica.deposit(jessicas, 200);
		jessica.withdraw(jessicas, 500);
		jessica.deposit(jessicas, 1000);

		System.out.println(jessica.getStatement());

	}

	@Test
	public void testOneAccount() {
		Customer oscar = new Customer("Oscar").openAccount("Oscar_Savings",
				AccountType.SAVINGS);
		assertEquals(1, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTwoAccount() {
		Customer oscar = new Customer("Oscar").openAccount("Oscar_Savings",
				AccountType.SAVINGS);
		oscar.openAccount("Oscar_Checking", AccountType.CHECKING);
		assertEquals(2, oscar.getNumberOfAccounts());
	}

	@Test
	public void testThreeAcounts() {
		Customer oscar = new Customer("Oscar").openAccount("Oscar_Savings",
				AccountType.SAVINGS);
		oscar.openAccount("Oscar_Checking", AccountType.CHECKING).openAccount(
				"Oscar_Maxi", AccountType.MAXI_SAVINGS);
		assertEquals(3, oscar.getNumberOfAccounts());
	}
}
