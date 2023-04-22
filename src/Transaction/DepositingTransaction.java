package Transaction;

import PhysicalPerson.PhysicalPerson;

public class DepositingTransaction extends Transaction {

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
