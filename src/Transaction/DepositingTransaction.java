package Transaction;

import PhysicalPerson.PhysicalPerson;

public class DepositingTransaction extends Transaction {

    @Override
    public String getSender() {

        String sender = "";

        if (getFromCard() != null && !getFromCard().getCardHolder().isClientCard(getToCard())) {
            PhysicalPerson cardHolder = getFromCard().getCardHolder().getPhysicalPerson(); sender = cardHolder.getFirstName() +
                   cardHolder.getLastName().substring(0,1);}
        if (getFromAccount() != null && !getFromAccount().getAccountHolder().isClientAccount(getToAccount())) {
            PhysicalPerson accountHolder = getFromAccount().getAccountHolder().getPhysicalPerson(); sender = accountHolder.getFirstName() +
                  accountHolder.getLastName().substring(0,1);}
        if (getFromCard() != null && !getFromAccount().getAccountHolder().isClientAccount(getToAccount())) {
            PhysicalPerson cardHolder = getFromCard().getCardHolder().getPhysicalPerson(); sender = cardHolder.getFirstName() +
                    cardHolder.getLastName().substring(0,1);}
        if (getFromAccount() != null && !getFromCard().getCardHolder().isClientCard(getToCard())) {
            PhysicalPerson accountHolder = getFromAccount().getAccountHolder().getPhysicalPerson(); sender = accountHolder.getFirstName() +
                    accountHolder.getLastName().substring(0,1);}
        sender = super.getSender() + " от" + sender;
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
