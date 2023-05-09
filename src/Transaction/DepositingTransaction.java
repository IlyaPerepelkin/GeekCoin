package Transaction;

import Account.Account;
import Card.Card;
import PhysicalPerson.PhysicalPerson;

import java.time.LocalDateTime;

public class DepositingTransaction extends Transaction {

    public DepositingTransaction(LocalDateTime localDateTime, Account fromAccount, Account toAccount, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, fromAccount, null, toAccount, null, typeOperation, sum, currencySymbol);
    }

    public DepositingTransaction(LocalDateTime localDateTime, Account fromAccount, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, fromAccount, null, null, null, typeOperation, sum, currencySymbol);
    }

    public DepositingTransaction(LocalDateTime localDateTime, Card fromCard, Card toCard, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, null, fromCard, null, toCard, typeOperation, sum, currencySymbol);
    }

    public DepositingTransaction(LocalDateTime localDateTime, Card fromCard, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime,null, fromCard, null, null, typeOperation, sum, currencySymbol);
    }

    public DepositingTransaction(LocalDateTime localDateTime, Card fromCard, Account toAccount, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, null, fromCard, toAccount, null, typeOperation, sum, currencySymbol);
    }

    public DepositingTransaction(LocalDateTime localDateTime, Account fromAccount, Card toCard, String typeOperation, float sum, char currencySymbol) {
        super(localDateTime, fromAccount, null, null, toCard, typeOperation, sum, currencySymbol);
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

        String transaction = getLocalDateTime() + " " + getRecipient() + " " + getTypeOperation() + (!getSender().isEmpty() ? " " + getSender() : "") + ": " + getSum() + getCurrencySymbol() +
                " Статус: " + getStatusOperation() + " Баланс: " + getBalance() + getCurrencySymbol() + " Комиссия составила: " + getCommission() +
                getCurrencySymbol() + (getAuthorizationCode() != null ? " Код авторизации: " + getAuthorizationCode() : "");

        return transaction;
    }

}
