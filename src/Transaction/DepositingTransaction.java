package Transaction;

public class DepositingTransaction extends Transaction {


    @Override
    public String getStringTransaction() {

        String transaction = getLocalDateTime() + " " + getRecipient() + " " + getTypeOperation() + (!getSender().isEmpty() ? " " + getSender() : "") + ": " + getSum() + getCurrencySymbol() +
                " Статус: " + getStatusOperation() + " Баланс: " + getBalance() + getCurrencySymbol() + " Комиссия составила: " + getCommission() +
                getCurrencySymbol() + (getAuthorizationCode() != null ? " Код автаризации: " + getAuthorizationCode() : "");

        return transaction;
    }

}
