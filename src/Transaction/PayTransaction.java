package Transaction;

import Account.Account;
import Card.Card;

public class PayTransaction extends Transaction {

    private String buyProductOrService;


    public String getBuyProductOrService() {
        return buyProductOrService;
    }

    public void setBuyProductOrService(String buyProductOrService) {
        this.buyProductOrService = buyProductOrService;
    }


    public PayTransaction(Account fromAccount, Account toAccount, String typeOperation, float sum, char currencySymbol) {
        super(fromAccount, toAccount, typeOperation, sum, currencySymbol);
    }

    public PayTransaction(Account fromAccount, String typeOperation, float sum, char currencySymbol) {
        super(fromAccount, typeOperation, sum, currencySymbol);
    }

    public PayTransaction(Card fromCard, Card toCard, String typeOperation, float sum, char currencySymbol) {
        super(fromCard, toCard, typeOperation, sum, currencySymbol);
    }

    public PayTransaction(Card fromCard, String typeOperation, float sum, char currencySymbol) {
        super(fromCard, typeOperation, sum, currencySymbol);
    }

    public PayTransaction(Card fromCard, Account toAccount, String typeOperation, float sum, char currencySymbol) {
        super(fromCard, toAccount, typeOperation, sum, currencySymbol);
    }

    public PayTransaction(Account fromAccount, Card toCard, String typeOperation, float sum, char currencySymbol) {
        super(fromAccount, toCard, typeOperation, sum, currencySymbol);
    }

    @Override
    public String getStringTransaction() {
        String consumer = getNameCard(getFromCard());

        String transaction = getLocalDateTime() + " " + consumer + " " + getTypeOperation() + buyProductOrService + ": " + getSum() + getCurrencySymbol()
                + " Статус: " + getStatusOperation() + " Баланс: " + getBalance() + getCurrencySymbol() + " Комиссия составила: " + getCommission() +
                getCurrencySymbol() + " Код авторизации: " + getAuthorizationCode();

        return transaction;

    }

}
