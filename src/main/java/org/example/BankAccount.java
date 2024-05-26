package org.example;

import java.math.BigInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private BigInteger balance;
    private final Lock lock;
    private final Condition sufficientFunds;

    public BankAccount(String accountNumber, String accountHolderName, BigInteger initialBalance) {
        // Data validations
        if (accountNumber.isEmpty() || accountHolderName.isEmpty() || initialBalance.compareTo(BigInteger.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid account details provided.");
        }

        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.lock = new ReentrantLock();
        this.sufficientFunds = lock.newCondition();
    }

    public synchronized void  deposit(BigInteger amount) throws InterruptedException {



        lock.lock();
        try {
            if (amount.compareTo(BigInteger.ZERO) <= 0) {
                System.out.println("Error: Deposit amount must be greater than zero.");
                return;
            }
            balance = balance.add(amount);
            System.out.println(accountHolderName + " deposited: $" + amount);
            sufficientFunds.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public synchronized void withdraw(BigInteger amount) throws InterruptedException {


        lock.lock();
        try {
            if (amount.compareTo(BigInteger.ZERO) <= 0) {
                System.out.println("Error: Withdrawal amount must be greater than zero.");
                return;
            }

            while (balance.compareTo(amount) < 0) {
                System.out.println("Insufficient balance for " + accountHolderName + ". Waiting...");
                sufficientFunds.await();  
            }
            balance = balance.subtract(amount);
            System.out.println(accountHolderName + " withdrew: $" + amount);
        } finally {
            lock.unlock();
        }
    }

    public synchronized BigInteger getBalance() {
        return balance;
    }

    public synchronized String getAccountNumber() {
        return accountNumber;
    }

    public synchronized  String getAccountHolderName() {
        return accountHolderName;
    }
}
