package org.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Bank {
    private Map<String, BankAccount> accounts;

    public Bank() {
        this.accounts = new ConcurrentHashMap<>();
    }

    public void addAccount(BankAccount account) {
        if (accounts.containsKey(account.getAccountNumber())) {
            System.out.println("Error: Account with number " + account.getAccountNumber() + " already exists.");
            return;
        }
        accounts.put(account.getAccountNumber(), account);
    }

    public BankAccount getAccount(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("Error: Account with number " + accountNumber + " not found.");
        }
        return account;
    }
}
