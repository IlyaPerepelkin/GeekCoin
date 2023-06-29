package ru.geekstar.ThreadJobs;

import ru.geekstar.Account.Account;
import ru.geekstar.Card.Card;

public class TransferRunnable extends Operations implements Runnable {

    private float sumTransfer;


    public TransferRunnable(Card fromCard, Card toCard, float sumTransfer) {
        this.sumTransfer = sumTransfer;
        this.fromCard = fromCard;
        this.toCard = toCard;
    }

    public TransferRunnable(Card fromCard, Account toAccount, float sumTransfer) {
        this.sumTransfer = sumTransfer;
        this.fromCard = fromCard;
        this.toAccount = toAccount;
    }

    public TransferRunnable(Account fromAccount, Card toCard, float sumTransfer) {
        this.sumTransfer = sumTransfer;
        this.fromAccount = fromAccount;
        this.toCard = toCard;
    }

    public TransferRunnable(Account fromAccount, Account toAccount, float sumTransfer) {
        this.sumTransfer = sumTransfer;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    @Override
    public void run() {
        if (fromCard != null && toCard != null) {
            fromCard.transferCard2Card(toCard, sumTransfer);
        } else if (fromCard != null && toAccount != null) {
            fromCard.transferCard2Account(toAccount, sumTransfer);
        } else if (fromAccount != null && toCard != null) {
            fromAccount.transferAccount2Card(toCard, sumTransfer);
        } else if (fromAccount != null && toAccount != null) {
            fromAccount.transferAccount2Account(toAccount, sumTransfer);
        }
    }

}
