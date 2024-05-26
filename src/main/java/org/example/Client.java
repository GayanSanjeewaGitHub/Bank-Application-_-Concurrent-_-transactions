package org.example;

import java.math.BigInteger;

class Client extends Thread {
    private static final ThreadGroup VIP_GROUP = new ThreadGroup("VIP Clients");
    private static final ThreadGroup REGULAR_GROUP = new ThreadGroup("Regular Clients");

    private Bank bank;
    private BankAccount account;

    public Client(String name, Bank bank, BankAccount account, boolean isVIP) {
        super(isVIP ? VIP_GROUP : REGULAR_GROUP, name);
        this.bank = bank;
        this.account = account;
        if (isVIP) {
            setPriority(Thread.MAX_PRIORITY); // VIP clients have higher priority
        }
    }

    public void run() {
        // Simulating banking operations
        for (int i = 0; i < 3; i++) {
            try {
                BigInteger amount = new BigInteger(String.valueOf(Math.round(Math.random() * 1000)));
                account.deposit(amount);
                account.withdraw(amount.divide(BigInteger.valueOf(2)));

                // Check balance after each operation
                System.out.println(getName() + " Balance: $" + account.getBalance());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}