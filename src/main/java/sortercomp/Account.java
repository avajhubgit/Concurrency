package main.java.sortercomp;

public class Account {
    public int getBalance() {
        return balance;
    }

    private volatile int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    //deposite
    public void incBalance(int amount) {
        balance += amount;
    }

    //with draw
    public void decBalance(int amount) {
        balance -= amount;
    }
}
