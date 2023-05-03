package Transaction;

import Account.Account;
import Card.Card;

import java.time.LocalDateTime;

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

    public PayBonusTransaction(LocalDateTime localDateTime, Account fromAccount, Account toAccount, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, fromAccount, toAccount, typeOperation, sum, currencySymbol);
    }

    public PayBonusTransaction(LocalDateTime localDateTime, Account fromAccount, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, fromAccount, typeOperation, sum, currencySymbol);
    }

    public PayBonusTransaction(LocalDateTime localDateTime, Card fromCard, Card toCard, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, fromCard, toCard, typeOperation, sum, currencySymbol);
    }

    public PayBonusTransaction(LocalDateTime localDateTime, Card fromCard, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, fromCard, typeOperation, sum, currencySymbol);
    }

    public PayBonusTransaction(LocalDateTime localDateTime, Card fromCard, Account toAccount, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, fromCard, toAccount, typeOperation, sum, currencySymbol);
    }

    public PayBonusTransaction(LocalDateTime localDateTime, Account fromAccount, Card toCard, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, fromAccount, toCard, typeOperation, sum, currencySymbol);
    }

    @Override
    public String getStringTransaction() {

        String transaction = getLocalDateTime() + " " + getNameCard(getFromCard()) + " " + getTypeOperation() + " " + getBuyProductOrService() + ": " +
                getPayBonuses() + " " + "бонусов" + " Статус: " + getStatusOperation() + " Доступно бонусов: " + getBalanceBonuses();

        return transaction;
    }

}
