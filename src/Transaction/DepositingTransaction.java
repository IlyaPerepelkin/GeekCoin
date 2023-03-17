package Transaction;

import PhysicalPerson.PhysicalPerson;

public class DepositingTransaction extends Transaction {

    @Override
    public String getSender() {

        String sender = "";

        if (getFromCard() != null) {
            PhysicalPerson cardHolder = getFromCard().getCardHolder().getPhysicalPerson();

            if ((getToCard() != null && !getFromCard().getCardHolder().isClientCard(getToCard())) || (getToAccount() != null && !getFromCard().getCardHolder().isClientAccount(getToAccount()))) {
                sender = cardHolder.getFirstName() + " " + cardHolder.getLastName().substring(0,1) + ".";
            }
        }

        if (getFromAccount() != null) {
            PhysicalPerson accountHolder = getFromAccount().getAccountHolder().getPhysicalPerson();

            if ((getToAccount() != null && !getFromAccount().getAccountHolder().isClientAccount(getToAccount())) || (getToCard() != null && !getFromAccount().getAccountHolder().isClientCard(getToCard()))) {
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
