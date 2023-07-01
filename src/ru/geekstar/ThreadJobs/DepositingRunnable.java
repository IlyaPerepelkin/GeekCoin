package ru.geekstar.ThreadJobs;

import ru.geekstar.Account.Account;
import ru.geekstar.Card.Card;

public class DepositingRunnable extends Operations implements Runnable {

    private float sumDepositing;


    public DepositingRunnable(Card toCard, float sumDepositing) {
        super(toCard, sumDepositing);
        this.sumDepositing = sumDepositing;
    }

    public DepositingRunnable(Card toCard, Card fromCard, float sumDepositing) {
        super(toCard, fromCard, sumDepositing);
        this.sumDepositing = sumDepositing;
    }

    public DepositingRunnable(Card toCard, Account fromAccount, float sumDepositing) {
        super(toCard, fromAccount, sumDepositing);
        this.sumDepositing = sumDepositing;
    }

    public DepositingRunnable(Account toAccount, Card fromCard, float sumDepositing) {
        super(toAccount, fromCard, sumDepositing);
        this.sumDepositing = sumDepositing;
    }

    public DepositingRunnable(Account toAccount, Account fromAccount, float sumDepositing) {
        super(toAccount, fromAccount, sumDepositing);
        this.sumDepositing = sumDepositing;
    }

    @Override
    public void run() {
        if (toCard != null && fromCard == null && fromAccount == null) {
            toCard.depositingCash2Card(sumDepositing);
        } else if (toCard != null && fromCard != null) {
            toCard.depositingCardFromCard(fromCard, sumDepositing);
        } else if (toCard != null && fromAccount != null) {
            toCard.depositingCardFromAccount(fromAccount, sumDepositing);
        } else if (toAccount != null && fromCard != null) {
            toAccount.depositingAccountFromCard(fromCard, sumDepositing);
        } else if (toAccount != null && fromAccount != null) {
            toAccount.depositingAccountFromAccount(fromAccount, sumDepositing);
        }
    }

}
