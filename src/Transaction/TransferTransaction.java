package Transaction;

public class TransferTransaction extends Transaction {

    @Override
    public String getStringTransactoin() {

        String transaction = getLocalDateTime() + " " + getSender() + " " + getTypeOperation() + " " + getRecipient() + ": " + getSum() + getCurrencySymbol() +
                " Статус: " + getStatusOperation() + " Баланс: " + getBalance() + getCurrencySymbol() + " Комиссия составила: " + getCommission() +
                getCurrencySymbol() + (getAuthorizationCode() != null ? " Код автаризации: " + getAuthorizationCode() : "");

        return transaction;

    }
}
