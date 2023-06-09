package ru.geekstar.Transaction;

import ru.geekstar.Account.Account;
import ru.geekstar.Card.Card;
import ru.geekstar.PhysicalPerson.PhysicalPerson;

public class DepositingTransaction extends Transaction {

    public DepositingTransaction(Card fromCard, Card toCard, String typeOperation, float sum, String currencySymbol) {
        super(fromCard, toCard, typeOperation, sum, currencySymbol);
    }

    public DepositingTransaction(Card fromCard, Account toAccount, String typeOperation, float sum, String currencySymbol) {
        super(fromCard, toAccount, typeOperation, sum, currencySymbol);
    }

    public DepositingTransaction(Account fromAccount, Account toAccount, String typeOperation, float sum, String currencySymbol) {
        super(fromAccount, toAccount, typeOperation, sum, currencySymbol);
    }

    public DepositingTransaction(Account fromAccount, Card toCard, String typeOperation, float sum, String currencySymbol) {
        super(fromAccount, toCard, typeOperation, sum, currencySymbol);
    }

    public DepositingTransaction(Card toCard, String typeOperation, float sum) {
        super(typeOperation, toCard, sum);
    }

    @Override
    public String getSender() {

        String sender = "";

        if (getFromCard() != null) {

            if ((getToCard() != null && !getFromCard().getCardHolder().isClientCard(getToCard())) || (getToAccount() != null && !getFromCard().getCardHolder().isClientAccount(getToAccount()))) {
                PhysicalPerson cardHolder = getFromCard().getCardHolder().getPhysicalPerson();
                sender = cardHolder.getFirstName() + " " + cardHolder.getLastName().substring(0,1) + ".";
            }
        }

        if (getFromAccount() != null) {

            if ((getToAccount() != null && !getFromAccount().getAccountHolder().isClientAccount(getToAccount())) || (getToCard() != null && !getFromAccount().getAccountHolder().isClientCard(getToCard()))) {
                PhysicalPerson accountHolder = getFromAccount().getAccountHolder().getPhysicalPerson();
                sender = accountHolder.getFirstName() + " " + accountHolder.getLastName().substring(0, 1) + ".";
            }
        }

        if (!sender.isEmpty()) {
            sender = super.getSender() + " от " + sender;
        }

        return sender;
    }

    @Override
    public String getStringTransaction() {

        String transaction = getLocalDateTime() + " " + getRecipient() + "\n" + getTypeOperation() + (!getSender().isEmpty() ? " " + getSender() : "") + ": +" + getSum() + getCurrencySymbol() +
                "\nСтатус: " +  getStatusOperation() + "\nБаланс: " + getBalance() + getCurrencySymbol() + "\nКомиссия составила: " + getCommission() +
                getCurrencySymbol() + (getAuthorizationCode() != null ? " Код авторизации: " + getAuthorizationCode() : "");

        return transaction;
    }

}
