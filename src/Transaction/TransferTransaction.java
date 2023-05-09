package Transaction;

import Account.Account;
import Card.Card;

import java.time.LocalDateTime;

public class TransferTransaction extends Transaction {

    public TransferTransaction(LocalDateTime localDateTime, Account fromAccount, Account toAccount, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, fromAccount, null, toAccount, null, typeOperation, sum, currencySymbol);
    }

    public TransferTransaction(LocalDateTime localDateTime, Account fromAccount, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, fromAccount, null, null, null, typeOperation, sum, currencySymbol);
    }

    public TransferTransaction(LocalDateTime localDateTime, Card fromCard, Card toCard, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, null, fromCard, null, toCard, typeOperation, sum, currencySymbol);
    }

    public TransferTransaction(LocalDateTime localDateTime, Card fromCard, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime,null, fromCard, null, null, typeOperation, sum, currencySymbol);
    }

    public TransferTransaction(LocalDateTime localDateTime, Card fromCard, Account toAccount, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, null, fromCard, toAccount, null, typeOperation, sum, currencySymbol);
    }

    public TransferTransaction(LocalDateTime localDateTime, Account fromAccount, Card toCard, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, fromAccount, null, null, toCard, typeOperation, sum, currencySymbol);
    }

    @Override
    public String getStringTransaction() {

        String transaction = getLocalDateTime() + " " + getSender() + " " + getTypeOperation() + " " + getRecipient() + ": " + getSum() + getCurrencySymbol() +
                " Статус: " + getStatusOperation() + " Баланс: " + getBalance() + getCurrencySymbol() + " Комиссия составила: " + getCommission() +
                getCurrencySymbol() + (getAuthorizationCode() != null ? " Код авторизации: " + getAuthorizationCode() : "");

        return transaction;

    }
}
