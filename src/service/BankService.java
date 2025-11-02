package service;

import java.util.*;
import java.util.stream.Collectors;

import model.Account;
import exception.*;
import util.AccountNumberGenerator;

public class BankService {
    private Map<String, Account> accounts = new HashMap<>();

    public Account createAccount(String name) throws InvalidNameException {
        if (name == null || name.trim().isEmpty())
            throw new InvalidNameException("Name cannot be empty!");

        String accNum = AccountNumberGenerator.generate(name);
        Account acc = new Account(accNum, name);
        accounts.put(accNum, acc);
        return acc;
    }

    public void deposit(String accNum, double amount) throws InvalidAmountException, AccountNotFoundException {
        Account acc = findAccount(accNum);
        if (amount <= 0)
            throw new InvalidAmountException("Amount must be positive!");
        acc.deposit(amount);
    }

    public void withdraw(String accNum, double amount) throws InvalidAmountException, InsufficientBalanceException, AccountNotFoundException {
        Account acc = findAccount(accNum);
        if (amount <= 0)
            throw new InvalidAmountException("Amount must be positive!");
        if (acc.getBalance() < amount)
            throw new InsufficientBalanceException("Insufficient balance!");
        acc.withdraw(amount);
    }

    public void transfer(String fromAcc, String toAcc, double amount)
            throws InvalidAmountException, InsufficientBalanceException, AccountNotFoundException {
        Account src = findAccount(fromAcc);
        Account dest = findAccount(toAcc);

        if (amount <= 0)
            throw new InvalidAmountException("Amount must be positive!");
        if (src.getBalance() < amount)
            throw new InsufficientBalanceException("Insufficient balance!");

        synchronized (this) {
            src.withdraw(amount);
            dest.deposit(amount);
        }
    }

    public Account findAccount(String accNum) throws AccountNotFoundException {
        Account acc = accounts.get(accNum);
        if (acc == null)
            throw new AccountNotFoundException("Account not found: " + accNum);
        return acc;
    }

    public List<Account> searchByName(String name) {
        return accounts.values().stream()
                .filter(a -> a.getHolderName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }
}
