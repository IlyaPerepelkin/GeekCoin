package Transaction;

import Account.SberSavingsAccount;
import Card.SberVisaGold;
import PhysicalPerson.PhysicalPerson;

public class DepositingTransaction extends Transaction {

    @Override
    public String getSender() {
        String sender = "";
        if (fromCard != null) sender = getNameCard(fromCard);
        if (fromAccount != null) sender = getNameAccount(fromAccount);
        return sender;
    }

    @Override
    public String getNameCard(SberVisaGold card) {
        if (card != mySberVisaGold) {
            return card.getBank().getBankName() + "Карта " + card.getClass().getSimpleName() + " ••" + card.getNumberCard().split(" ")[3]
                    + "от " + PhysicalPerson.getFirstName + PhysicalPerson.getLastName.substring(getLastName.indexOf(1)) + ".";
        }
    }

    @Override
    public String getNameAccount(SberSavingsAccount account) {
        if (account != mySberSavingsAccount) {
            return account.getBank().getBankName() + "Счет ••" + account.getNumberAccount().substring(16) +
                    "от " + PhysicalPerson.getFirstName + PhysicalPerson.getLastName.substring(getLastName.indexOf(1)) + ".";
        }
    }

    @Override
    public String getStringTransaction() {

        String transaction = getLocalDateTime() + " " + getRecipient() + " " + getTypeOperation() + (!getSender().isEmpty() ? " " + getSender() : "") + ": " + getSum() + getCurrencySymbol() +
                " Статус: " + getStatusOperation() + " Баланс: " + getBalance() + getCurrencySymbol() + " Комиссия составила: " + getCommission() +
                getCurrencySymbol() + (getAuthorizationCode() != null ? " Код авторизации: " + getAuthorizationCode() : "");

        return transaction;
    }

}
