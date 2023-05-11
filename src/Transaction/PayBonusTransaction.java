package Transaction;

import Account.Account;
import Card.Card;

public class PayBonusTransaction extends PayTransaction {

    private int payBonuses;

    private int balanceBonuses;


    public int getPayBonuses() {
        return payBonuses;
    }

    public void setPayBonuses(int payBonuses) {
        this.payBonuses = payBonuses;
    }

    public int getBalanceBonuses() {
        return balanceBonuses;
    }

    public void setBalanceBonuses(int balanceBonuses) {
        this.balanceBonuses = balanceBonuses;
    }

    public PayBonusTransaction(Account fromAccount, Account toAccount, String typeOperation, float sum, char currencySymbol) {
        super(fromAccount, toAccount, typeOperation, sum, currencySymbol);
    }

    public PayBonusTransaction(Account fromAccount, String typeOperation, float sum, char currencySymbol) {
        super(fromAccount, typeOperation, sum, currencySymbol);
    }

    public PayBonusTransaction(Card fromCard, Card toCard, String typeOperation, float sum, char currencySymbol) {
        super(fromCard, toCard, typeOperation, sum, currencySymbol);
    }

    public PayBonusTransaction(Card fromCard, String typeOperation, float sum, char currencySymbol) {
        super(fromCard, typeOperation, sum, currencySymbol);
    }

    public PayBonusTransaction(Card fromCard, Account toAccount, String typeOperation, float sum, char currencySymbol) {
        super(fromCard, toAccount, typeOperation, sum, currencySymbol);
    }

    public PayBonusTransaction(Account fromAccount, Card toCard, String typeOperation, float sum, char currencySymbol) {
        super(fromAccount, toCard, typeOperation, sum, currencySymbol);
    }

    @Override
    public String getStringTransaction() {

        String transaction = getLocalDateTime() + " " + getNameCard(getFromCard()) + " " + getTypeOperation() + " " + getBuyProductOrService() + ": " +
                getPayBonuses() + " " + "бонусов" + " Статус: " + getStatusOperation() + " Доступно бонусов: " + getBalanceBonuses();

        return transaction;
    }

}
