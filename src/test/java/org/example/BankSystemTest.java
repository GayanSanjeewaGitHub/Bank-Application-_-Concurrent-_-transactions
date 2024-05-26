package org.example;
import java.math.BigInteger;

public class BankSystemTest {

    public static void main(String[] args) {
        testValidAccountCreation();
        testDuplicateAccountCreation();
        testNonExistingAccountRetrieval();
        testConcurrentOperations();
        testThreadPriority();
    }

    // Test valid account creation
    public static void testValidAccountCreation() {
        try {
            Bank bank = new Bank();
            BankAccount acc1 = new BankAccount("101", "John Doe", BigInteger.valueOf(1000));
            BankAccount acc2 = new BankAccount("102", "Jane Doe", BigInteger.valueOf(1500));

            bank.addAccount(acc1);
            bank.addAccount(acc2);

            System.out.println("Test: Valid Account Creation - PASSED");
        } catch (Exception e) {
            System.out.println("Test: Valid Account Creation - FAILED");
            e.printStackTrace();
        }
    }

    // Test duplicate account creation
    public static void testDuplicateAccountCreation() {
        try {
            Bank bank = new Bank();
            BankAccount acc1 = new BankAccount("101", "John Doe", BigInteger.valueOf(1000));
            BankAccount acc2 = new BankAccount("101", "Jane Doe", BigInteger.valueOf(1500)); // Duplicate account number

            bank.addAccount(acc1);
            bank.addAccount(acc2); // Should fail due to duplicate account number

            System.out.println("Test: Duplicate Account Creation - FAILED");
        } catch (Exception e) {
            System.out.println("Test: Duplicate Account Creation - PASSED");
        }
    }

    // Test non-existing account retrieval
    public static void testNonExistingAccountRetrieval() {
        try {
            Bank bank = new Bank();
            BankAccount acc1 = new BankAccount("101", "John Doe", BigInteger.valueOf(1000));

            bank.addAccount(acc1);

            BankAccount retrievedAccount = bank.getAccount("102"); // Account does not exist

            if (retrievedAccount == null) {
                System.out.println("Test: Non-Existing Account Retrieval - PASSED");
            } else {
                System.out.println("Test: Non-Existing Account Retrieval - FAILED");
            }
        } catch (Exception e) {
            System.out.println("Test: Non-Existing Account Retrieval - FAILED");
            e.printStackTrace();
        }
    }

    // Test concurrent operations (deposits and withdrawals)
    public static void testConcurrentOperations() {
        try {
            Bank bank = new Bank();
            BankAccount acc1 = new BankAccount("101", "John Doe", BigInteger.valueOf(1000));

            bank.addAccount(acc1);

            // Creating multiple client threads
            for (int i = 0; i < 5; i++) {
                Client client = new Client("Client_" + (i + 1), bank, acc1, false);
                client.start();
            }

            System.out.println("Test: Concurrent Operations - PASSED");
        } catch (Exception e) {
            System.out.println("Test: Concurrent Operations - FAILED");
            e.printStackTrace();
        }
    }

    // Test thread priority (VIP clients have higher priority)
    public static void testThreadPriority() {
        try {
            Bank bank = new Bank();
            BankAccount acc1 = new BankAccount("101", "John Doe", BigInteger.valueOf(1000));

            bank.addAccount(acc1);

            // Creating VIP and regular clients
            Client vipClient = new Client("VIP_Client", bank, acc1, true);
            Client regularClient = new Client("Regular_Client", bank, acc1, false);

            vipClient.start();
            regularClient.start();

            System.out.println("Test: Thread Priority - PASSED");
        } catch (Exception e) {
            System.out.println("Test: Thread Priority - FAILED");
            e.printStackTrace();
        }
    }
}
