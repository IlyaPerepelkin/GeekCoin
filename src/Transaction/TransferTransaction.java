package Transaction;

import Account.Account;
import Card.Card;

public class TransferTransaction extends Transaction {

    public TransferTransaction(Account fromAccount, Account toAccount, String typeOperation, float sum, char currencySymbol) {
        super(fromAccount, toAccount, typeOperation, sum, currencySymbol);
    }

    public TransferTransaction(Account fromAccount, String typeOperation, float sum, char currencySymbol) {
        super(fromAccount, typeOperation, sum, currencySymbol);
    }

    public TransferTransaction(Card fromCard, Card toCard, String typeOperation, float sum, char currencySymbol) {
        super(fromCard, toCard, typeOperation, sum, currencySymbol);
    }

    public TransferTransaction(Card fromCard, String typeOperation, float sum, char currencySymbol) {
        super(fromCard, typeOperation, sum, currencySymbol);
    }

    public TransferTransaction(Card fromCard, Account toAccount, String typeOperation, float sum, char currencySymbol) {
        super(fromCard, toAccount, typeOperation, sum, currencySymbol);
    }

    public TransferTransaction(Account fromAccount, Card toCard, String typeOperation, float sum, char currencySymbol) {
        super(fromAccount, toCard, typeOperation, sum, currencySymbol);
    }

    @Override
    public String getStringTransaction() {

        String transaction = getLocalDateTime() + " " + getSender() + " " + getTypeOperation() + " " + getRecipient() + ": " + getSum() + getCurrencySymbol() +
                " Статус: " + getStatusOperation() + " Баланс: " + getBalance() + getCurrencySymbol() + " Комиссия составила: " + getCommission() +
                getCurrencySymbol() + (getAuthorizationCode() != null ? " Код авторизации: " + getAuthorizationCode() : "");

        return transaction;

    }
}
