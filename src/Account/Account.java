package Account;

import Bank.Sberbank;
import ClientProfile.SberPhysicalPersonProfile;
import Transaction.DepositingTransaction;
import Transaction.TransferTransaction;

public class Account {

    private Sberbank bank;

    private SberPhysicalPersonProfile accountHolder;

    private String numberAccount;

    private String currencyCode;

    private char currencySymbol;

    private float balance;

    private TransferTransaction[] transferTransactions = new TransferTransaction[50];

    private DepositingTransaction[] depositingTransactions = new DepositingTransaction[50];

    private int countTransferTransactions;

    private int countDepositingTransactions;


    public Sberbank getBank() {
        return bank;
    }

    public void setBank(Sberbank bank) {
        this.bank = bank;
    }

    public SberPhysicalPersonProfile getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(SberPhysicalPersonProfile accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(String numberAccount) {
        this.numberAccount = numberAccount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public char getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencyCode) {
        if (currencyCode.equals("RUB")) this.currencySymbol = '₽';
        else if (currencyCode.equals("USD")) this.currencySymbol = '$';
        else if (currencyCode.equals("EUR")) this.currencySymbol = '€';
        else System.out.println("Недопусимый код валюты: " + currencyCode);
    }

    public float getBalance() {
        return balance;
    }

    public boolean setBalance(float balance) {
        if (balance >= 0) {
            this.balance = balance;
            return true;
        }
        else {
            System.out.println("Отрицательное недопустимое значенеи баланса: " + balance);
            return false;
        }
    }

    public TransferTransaction[] getTransferTransactions() {
        return transferTransactions;
    }

    public void setTransferTransactions(TransferTransaction[] transferTransactions) {
        this.transferTransactions = transferTransactions;
    }

    public DepositingTransaction[] getDepositingTransactions() {
        return depositingTransactions;
    }

    public void setDepositingTransactions(DepositingTransaction[] depositingTransactions) {
        this.depositingTransactions = depositingTransactions;
    }

    public int getCountTransferTransactions() {
        return countTransferTransactions;
    }

    public void setCountTransferTransactions(int countTransferTransactions) {
        this.countTransferTransactions = countTransferTransactions;
    }

    public int getCountDepositingTransactions() {
        return countDepositingTransactions;
    }

    public void setCountDepositingTransactions(int countDepositingTransactions) {
        this.countDepositingTransactions = countDepositingTransactions;
    }

    // пополнить баланс
    public boolean topUp(float sum) {
        setBalance(balance + sum);
        return true;
    }

    // добавить транзакцию о пополнении
    public void addDepositingTransaction(DepositingTransaction depositingTransaction) {
        depositingTransactions[countDepositingTransactions++] = depositingTransaction;
    }

    //  Проверить достаточно ли денег на балансе
    public boolean checkBalance(float sum) {
        if (sum <= balance) return true;
        return false;
    }

    // Списать средства в три попытки в случае сбоя
    public boolean withdrawal(float sum) {
        boolean writeOffStatus;
        byte errorTransaction = 0;
        do {
            writeOffStatus = writeOff(sum);
            if (!writeOffStatus) errorTransaction++;
        } while (!writeOffStatus && errorTransaction < 3);

        return writeOffStatus;
    }

    // списать со счета
    private boolean writeOff(float sum) {
        return setBalance(balance - sum);
    }

}
