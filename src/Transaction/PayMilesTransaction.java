package Transaction;

import Account.Account;
import Card.Card;

public class PayMilesTransaction extends PayTransaction {

    public int balanceMiles;


    public int getBalanceMiles() {
        return balanceMiles;
    }

    public void setBalanceMiles(int balanceMiles) {
        this.balanceMiles = balanceMiles;
    }

    public PayMilesTransaction(Account fromAccount, Account toAccount, String typeOperation, float sum, char currencySymbol) {
        super(fromAccount, toAccount, typeOperation, sum, currencySymbol);
    }

    public PayMilesTransaction(Account fromAccount, String typeOperation, float sum, char currencySymbol) {
        super(fromAccount, typeOperation, sum, currencySymbol);
    }

    public PayMilesTransaction(Card fromCard, Card toCard, String typeOperation, float sum, char currencySymbol) {
        super(fromCard, toCard, typeOperation, sum, currencySymbol);
    }

    public PayMilesTransaction(Card fromCard, String typeOperation, float sum, char currencySymbol) {
        super(fromCard, typeOperation, sum, currencySymbol);
    }

    public PayMilesTransaction(Card fromCard, Account toAccount, String typeOperation, float sum, char currencySymbol) {
        super(fromCard, toAccount, typeOperation, sum, currencySymbol);
    }

    public PayMilesTransaction(Account fromAccount, Card toCard, String typeOperation, float sum, char currencySymbol) {
        super(fromAccount, toCard, typeOperation, sum, currencySymbol);
    }

    @Override
    public String getStringTransaction() {

        String transaction = getLocalDateTime() + " " + getNameCard(getFromCard()) + " " + getTypeOperation() + getBuyProductOrService() + " " +
                " Статус: " + getStatusOperation() + " " + "Баланс миль: " + getBalanceMiles();

        return transaction;
    }

}
