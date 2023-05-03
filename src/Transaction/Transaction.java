package Transaction;

import Account.Account;
import Card.Card;

import java.time.LocalDateTime;

public abstract class Transaction {

    private LocalDateTime localDateTime;

    private float sum;

    private float balance;

    private float commission;

    private char currencySymbol;

    private String authorizationCode;

    private String typeOperation;

    private String statusOperation;

    private Card toCard;

    private Card fromCard;

    private Account toAccount;

    private Account fromAccount;


    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public char getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(char currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public String getStatusOperation() {
        return statusOperation;
    }

    public void setStatusOperation(String statusOperation) {
        this.statusOperation = statusOperation;
    }

    public Card getToCard() {
        return toCard;
    }

    public void setToCard(Card toCard) {
        this.toCard = toCard;
    }

    public Card getFromCard() {
        return fromCard;
    }

    public void setFromCard(Card fromCard) {
        this.fromCard = fromCard;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }


    public Transaction(LocalDateTime localDateTime, Account fromAccount, Account toAccount, String typeOperation, float sum, char currencySymbol) {
        this.localDateTime = localDateTime;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.typeOperation = typeOperation;
        this.sum = sum;
        this.currencySymbol = currencySymbol;
    }

    public Transaction(LocalDateTime localDateTime, Account fromAccount, String typeOperation, float sum, char currencySymbol) {
        this.localDateTime = localDateTime;
        this.fromAccount = fromAccount;
        this.typeOperation = typeOperation;
        this.sum = sum;
        this.currencySymbol = currencySymbol;
    }

    public Transaction(LocalDateTime localDateTime, Card fromCard, Card toCard, String typeOperation, float sum, char currencySymbol) {
        this.localDateTime = localDateTime;
        this.fromCard = fromCard;
        this.toCard = toCard;
        this.typeOperation = typeOperation;
        this.sum = sum;
        this.currencySymbol = currencySymbol;
    }

    public Transaction(LocalDateTime localDateTime, Card fromCard, String typeOperation, float sum, char currencySymbol) {
        this.localDateTime = localDateTime;
        this.fromCard = fromCard;
        this.typeOperation = typeOperation;
        this.sum = sum;
        this.currencySymbol = currencySymbol;
    }

    public Transaction(LocalDateTime localDateTime, Card fromCard, Account toAccount, String typeOperation, float sum, char currencySymbol) {
        this.localDateTime = localDateTime;
        this.fromCard = fromCard;
        this.toAccount = toAccount;
        this.typeOperation = typeOperation;
        this.sum = sum;
        this.currencySymbol = currencySymbol;
    }

    public Transaction(LocalDateTime localDateTime, Account fromAccount, Card toCard, String typeOperation, float sum, char currencySymbol) {
        this.localDateTime = localDateTime;
        this.fromAccount = fromAccount;
        this.toCard = toCard;
        this.typeOperation = typeOperation;
        this.sum = sum;
        this.currencySymbol = currencySymbol;
    }

    public String getRecipient() {
        String recipient = "";
        if (toCard != null) recipient = getNameCard(toCard);
        if (toAccount != null) recipient = getNameAccount(toAccount);
        return  recipient;
    }

    public String getSender() {
        String sender = "";
        if (fromCard != null) sender = getNameCard(fromCard);
        if (fromAccount != null) sender = getNameAccount(fromAccount);
        return sender;
    }

    public String getNameCard(Card card) {
        return card.getBank().getBankName() + "Карта " + card.getClass().getSimpleName() + " ••" + card.getNumberCard().split(" ")[3];
    }

    public String getNameAccount(Account account) {
        return account.getBank().getBankName() + "Счет ••" + account.getNumberAccount().substring(20);
    }

    // Вывести транзакции в строковом человеко-читаемом формате
    public abstract String getStringTransaction();
}
