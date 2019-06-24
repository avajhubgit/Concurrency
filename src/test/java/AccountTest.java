package test.java;

import main.java.sortercomp.Account;
import main.java.sortercomp.AccountUtils;
import main.java.sortercomp.InsuffitientFundsException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    @Test
    public void accountBalance() {
        Account account = new Account(500);
        assertEquals(500, account.getBalance());

        account.incBalance(1000);
        assertEquals(1500, account.getBalance());

        account.decBalance(600);
        assertEquals(900, account.getBalance());
    }

    @Test
    public void simpleTransfer() {
        final Account a = new Account(1000);
        final Account b = new Account(4000);

        try {
            AccountUtils.transfer(a, b, 500);
        } catch (InsuffitientFundsException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = InsuffitientFundsException.class)
    public void notEnoughMoney() throws InsuffitientFundsException {
        final Account a = new Account(1000);
        final Account b = new Account(4000);
        AccountUtils.transfer(a, b, 2000);
    }

    @Test
    public void twoThreadTransfer() {
        final Account a = new Account(1000);
        final Account b = new Account(4000);

        new Thread(() -> {
            try {
                AccountUtils.transfer(a, b, 300);
            } catch (InsuffitientFundsException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            AccountUtils.transfer(b, a, 500);
        } catch (InsuffitientFundsException e) {
            e.printStackTrace();
        }
    }
}
