package Transaction;

import Account.Account;
import Card.Card;

import java.time.LocalDateTime;

public class PayMilesTransaction extends PayTransaction {

    public int balanceMiles;


    public int getBalanceMiles() {
        return balanceMiles;
    }

    public void setBalanceMiles(int balanceMiles) {
        this.balanceMiles = balanceMiles;
    }

    public PayMilesTransaction(LocalDateTime localDateTime, Account fromAccount, Account toAccount, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, fromAccount, toAccount, typeOperation, sum, currencySymbol);
    }

    public PayMilesTransaction(LocalDateTime localDateTime, Account fromAccount, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, fromAccount, typeOperation, sum, currencySymbol);
    }

    public PayMilesTransaction(LocalDateTime localDateTime, Card fromCard, Card toCard, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, fromCard, toCard, typeOperation, sum, currencySymbol);
    }

    public PayMilesTransaction(LocalDateTime localDateTime, Card fromCard, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, fromCard, typeOperation, sum, currencySymbol);
    }

    public PayMilesTransaction(LocalDateTime localDateTime, Card fromCard, Account toAccount, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, fromCard, toAccount, typeOperation, sum, currencySymbol);
    }

    public PayMilesTransaction(LocalDateTime localDateTime, Account fromAccount, Card toCard, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, fromAccount, toCard, typeOperation, sum, currencySymbol);
    }

    @Override
    public String getStringTransaction() {

        String transaction = getLocalDateTime() + " " + getNameCard(getFromCard()) + " " + getTypeOperation() + getBuyProductOrService() + " " +
                " Статус: " + getStatusOperation() + " " + "Баланс миль: " + getBalanceMiles();

        return transaction;
    }

}
