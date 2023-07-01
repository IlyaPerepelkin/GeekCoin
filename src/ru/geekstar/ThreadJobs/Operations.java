package ru.geekstar.ThreadJobs;

import ru.geekstar.Account.Account;
import ru.geekstar.Card.Card;

public abstract class Operations {

    Card toCard;

    Card fromCard;

    Account toAccount;

    Account fromAccount;

    float sum;

    public Operations(Card toCard, float sum) {
        this.sum = sum;
        this.toCard = toCard;
    }

    public Operations(Card toCard, Card fromCard, float sum) {
        this.sum = sum;
        this.toCard = toCard;
        this.fromCard = fromCard;
    }

    public Operations(Card toCard, Account fromAccount, float sum) {
        this.sum = sum;
        this.toCard = toCard;
        this.fromAccount = fromAccount;
    }

    public Operations(Account toAccount, Card fromCard, float sum) {
        this.sum = sum;
        this.toAccount = toAccount;
        this.fromCard = fromCard;
    }

    public Operations(Account toAccount, Account fromAccount, float sum) {
        this.sum = sum;
        this.toAccount = toAccount;
        this.fromAccount = fromAccount;
    }

    public Operations() {
    }

}
