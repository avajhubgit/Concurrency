package main.java.sortercomp;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountUtils {
    private static final int WAIT_SEC = 10;

    public static void transfer(Account accountSource, Account accountDestination, int amount) throws InsuffitientFundsException {
        if (accountSource.getBalance() < amount) {
            throw new InsuffitientFundsException();
        }
        Lock lockSource = new ReentrantLock();
        Lock lockDestination = new ReentrantLock();
        try {
            if (lockSource.tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
                try {
                    Thread.sleep(1000);
                    if (lockDestination.tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
                        try {
                            System.out.format("Balance before source = %d, destination = %d, amount = %d%n", accountSource.getBalance(), accountDestination.getBalance(), amount);
                            accountSource.decBalance(amount);
                            accountDestination.incBalance(amount);
                            System.out.format("Balance after source = %d, destination = %d, amount = %d %n", accountSource.getBalance(), accountDestination.getBalance(), amount);
                        } finally {
                            lockDestination.unlock();
                        }
                    } else {
                        System.out.println("Error waiting lockDestination.");
                    }
                } finally {
                    lockSource.unlock();
                }
            } else {
                System.out.println("Error waiting lockSource.");
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(AccountUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
