package service;

import model.Account;

public class TransactionThread extends Thread {
    private Account account;
    private double amount;
    private boolean isDeposit;

    public TransactionThread(Account account, double amount, boolean isDeposit) {
        this.account = account;
        this.amount = amount;
        this.isDeposit = isDeposit;
    }

    @Override
    public void run() {
        synchronized (account) {
            if (isDeposit)
                account.deposit(amount);
            else
                account.withdraw(amount);
        }
        System.out.println("Transaction completed by " + Thread.currentThread().getName() +
                " | Account: " + account.getAccountNumber() + " | New Balance: " + account.getBalance());
    }
}

