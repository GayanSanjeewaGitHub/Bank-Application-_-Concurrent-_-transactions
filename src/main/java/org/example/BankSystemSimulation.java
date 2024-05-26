package org.example;

import java.math.BigInteger;

class BankSystemSimulation {
    public static void main(String[] args) {
        Bank bank = new Bank();

        try {
            // Valid BankAccount creations
            BankAccount acc1 = new BankAccount("101", "John Doe", BigInteger.valueOf(1000));
            BankAccount acc2 = new BankAccount("102", "Jane Doe", BigInteger.valueOf(1500));
            BankAccount acc3 = new BankAccount("103", "Alice Smith", BigInteger.valueOf(200));

           // BankAccount acc4 = new BankAccount("", "Bob Johnson", BigInteger.valueOf(-500)); // Invalid account number and initial balance

            bank.addAccount(acc1);
            bank.addAccount(acc2);
            bank.addAccount(acc3);
            //bank.addAccount(acc4); // This will not be added due to invalid data

            // Creating client threads
            Client vipClient1 = new Client("VIP_Client1", bank, acc1, true);
            Client vipClient2 = new Client("VIP_Client2", bank, acc2, true);
            Client regularClient1 = new Client("Regular_Client1", bank, acc3, false);

            // Start client threads
            vipClient1.start();
            vipClient2.start();
            regularClient1.start();

            // Wait for threads to complete
            vipClient1.join();
            vipClient2.join();
            regularClient1.join();

            System.out.println("Final balances:");
            System.out.println(acc1.getAccountHolderName() + ": $" + acc1.getBalance());
            System.out.println(acc2.getAccountHolderName() + ": $" + acc2.getBalance());
            System.out.println(acc3.getAccountHolderName() + ": $" + acc3.getBalance());
        } catch (IllegalArgumentException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
