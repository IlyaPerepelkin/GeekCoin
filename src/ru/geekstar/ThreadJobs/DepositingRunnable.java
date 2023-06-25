package ru.geekstar.ThreadJobs;

import ru.geekstar.Account.Account;
import ru.geekstar.Card.Card;

public class DepositingRunnable extends Operations implements Runnable {

    private float sumDepositing;


    public DepositingRunnable(Card toCard, float sumDepositing) {
        this.sumDepositing = sumDepositing;
        this.toCard = toCard;
    }

    public DepositingRunnable(Card toCard, Card fromCard, float sumDepositing) {
        this.sumDepositing = sumDepositing;
        this.toCard = toCard;
        this.fromCard = fromCard;
    }

    public DepositingRunnable(Card toCard, Account fromAccount, float sumDepositing) {
        this.sumDepositing = sumDepositing;
        this.toCard = toCard;
        this.fromAccount = fromAccount;
    }

    public DepositingRunnable(Account toAccount, Card fromCard, float sumDepositing) {
        this.sumDepositing = sumDepositing;
        this.toAccount = toAccount;
        this.fromCard = fromCard;
    }

    public DepositingRunnable(Account toAccount, Account fromAccount, float sumDepositing) {
        this.sumDepositing = sumDepositing;
        this.toAccount = toAccount;
        this.fromAccount = fromAccount;
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
