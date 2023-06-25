package ru.geekstar.PhysicalPerson;

import ru.geekstar.Account.Account;
import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.Bank.Bank;
import ru.geekstar.Bank.IBankServicePhysicalPersons;
import ru.geekstar.Card.*;
import ru.geekstar.Card.IPaySystem.IPaySystem;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.ThreadJobs.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class PhysicalPerson {

    public static int personCount;

    private String firstName;

    private String lastName;

    private String telephone;

    private LocalDate dateOfBirth;;

    private char gender;

    private ArrayList<PhysicalPersonProfile> physicalPersonProfiles = new ArrayList<>();


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public ArrayList<PhysicalPersonProfile> getPhysicalPersonProfiles() {
        return physicalPersonProfiles;
    }

    public void setPhysicalPersonProfiles(ArrayList<PhysicalPersonProfile> physicalPersonProfiles) {
        this.physicalPersonProfiles = physicalPersonProfiles;
    }

    public PhysicalPerson(String firstName, String lastName, String telephone) {
        personCount++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
    }

    public PhysicalPerson(String firstName, String lastName, String telephone, LocalDate dateOfBirth, char gender) {
        this(firstName, lastName, telephone);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public PhysicalPersonProfile getPhysicalPersonProfile(Class<? extends Bank> classBank) {
        for (int idProfile = 0; idProfile < physicalPersonProfiles.size(); idProfile++) {
            PhysicalPersonProfile profile = physicalPersonProfiles.get(idProfile);
            // если объект банка является инстансом (экземпляром класса) classBank, то возвращаем найденный профиль
            if (classBank.isInstance(profile.getBank())) return profile;
        }
        return null;
    }

    public PhysicalPersonProfile getPhysicalPersonProfile(IBankServicePhysicalPersons bank) {
        for (int idProfile = 0; idProfile < physicalPersonProfiles.size(); idProfile++) {
            PhysicalPersonProfile profile = physicalPersonProfiles.get(idProfile);
            if (profile.getBank().equals(bank)) return profile;
        }
        return null;
    }

    public PhysicalPersonProfile registerPhysicalPersonToBank(IBankServicePhysicalPersons bank) {
        PhysicalPersonProfile physicalPersonProfile = bank.registerPhysicalPersonProfile(this);
        physicalPersonProfiles.add(physicalPersonProfile);
        return physicalPersonProfile;
    }

    public Card openCard(IBankServicePhysicalPersons bank, Class<? extends Card> classCard, Class<? extends PayCardAccount> classPayCardAccount, String currencyCode, String pinCode) {
        return bank.openCard(getPhysicalPersonProfile(bank), classCard, classPayCardAccount, currencyCode, pinCode);
    }

    public Account openAccount(IBankServicePhysicalPersons bank, Class<? extends Account> classAccount, String currencyCode) {
        return bank.openAccount(getPhysicalPersonProfile(bank), classAccount, currencyCode);
    }

    public void payByCard(Card card, float sumPay, String buyProductOrService, String pinCode) {
        Runnable payByCardJob = new PayByCardRunnable(card, sumPay, buyProductOrService, pinCode);
        Thread threadPayByCard = new Thread(payByCardJob);
        threadPayByCard.start();
    }

    public void payByCard(Card card, float sumPay, String buyProductOrService, String country, String pinCode) {
        Runnable payByCardJob = new PayByCardRunnable(card, sumPay, buyProductOrService, country, pinCode);
        Thread threadPayByCard = new Thread(payByCardJob);
        threadPayByCard.start();
    }

    public void payByCardMiles(IAirlinesCard airlinesCard, float sumPay, int milesPay, String buyProductOrService, String pinCode) {
        Runnable payByCardMiles = new PayByCardMilesRunnable(airlinesCard, sumPay, milesPay, buyProductOrService, pinCode);
        Thread threadPayByCardMiles = new Thread(payByCardMiles);
        threadPayByCardMiles.start();
    }

    public void payByCardBonuses(IBonusCard bonusCard, float sumPay, int bonusesPay, String buyProductOrService, String pinCode) {
        Runnable payByCardBonuses = new PayByCardBonusRunnable(bonusCard, sumPay, bonusesPay, buyProductOrService, pinCode);
        Thread threadPayByCardBonuses = new Thread(payByCardBonuses);
        threadPayByCardBonuses.start();
    }

    public void transferCard2Card(Card fromCard, Card toCard, float sumTransfer) {
        Runnable transferCard2Card = new TransferRunnable(fromCard, toCard, sumTransfer);
        Thread threadTransferCard2Card = new Thread(transferCard2Card);
        threadTransferCard2Card.start();
    }

    public void transferCard2Account(Card fromCard, Account toAccount, float sumTransfer) {
        Runnable transferCard2Account = new TransferRunnable(fromCard, toAccount, sumTransfer);
        Thread threadTransferCard2Account = new Thread(transferCard2Account);
        threadTransferCard2Account.start();
    }

    public void transferAccount2Card(Account fromAccount, Card toCard, float sumTransfer) {
        Runnable transferAccount2Card = new TransferRunnable(fromAccount, toCard, sumTransfer);
        Thread threadTransferAccount2Card = new Thread(transferAccount2Card);
        threadTransferAccount2Card.start();
    }

    public void transferAccount2Account(Account fromAccount, Account toAccount, float sumTransfer) {
        Runnable transferAccount2Account = new TransferRunnable(fromAccount, toAccount, sumTransfer);
        Thread threadTransferAccount2Account = new Thread(transferAccount2Account);
        threadTransferAccount2Account.start();
    }

    public void depositingCash2Card(Card toCard, float sumDepositing) {
        Runnable depositingCash2Card = new DepositingRunnable(toCard, sumDepositing);
        Thread threadDepositingCash2Card = new Thread(depositingCash2Card);
        threadDepositingCash2Card.start();
    }

    public void depositingCardFromCard(Card toCard, Card fromCard, float sumDepositing) {
        Runnable depositingCardFromCard = new DepositingRunnable(toCard, fromCard, sumDepositing);
        Thread threadDepositingCardFromCard = new Thread(depositingCardFromCard);
        threadDepositingCardFromCard.start();
    }

    public void depositingCardFromAccount(Card toCard, Account fromAccount, float sumDepositing) {
        Runnable depositingCardFromAccount = new DepositingRunnable(toCard, fromAccount, sumDepositing);
        Thread threadDepositingCardFromAccount = new Thread(depositingCardFromAccount);
        threadDepositingCardFromAccount.start();
    }

    public void depositingAccountFromCard(Account toAccount, Card fromCard, float sumDepositing) {
        Runnable depositingAccountFromCard = new DepositingRunnable(toAccount, fromCard, sumDepositing);
        Thread threadDepositingAccountFromCard = new Thread(depositingAccountFromCard);
        threadDepositingAccountFromCard.start();
    }

    public void depositingAccountFromAccount(Account toAccount, Account fromAccount, float sumDepositing) {
        Runnable depositingAccountFromAccount = new DepositingRunnable(toAccount, fromAccount, sumDepositing);
        Thread threadDepositingAccountFromAccount = new Thread(depositingAccountFromAccount);
        threadDepositingAccountFromAccount.start();
    }

    public ArrayList<Float> getExchangeRatePaySystem(IPaySystem paySystemCard, String currency, String currencyExchangeRate) {
        return paySystemCard.getExchangeRatePaySystem(currency, currencyExchangeRate);
    }

    public void displayCardTransactions(Card card) {
        card.displayCardTransactions();
    }

    public void displayAccountTransactions(Account account) {
        account.displayAccountTransactions();
    }

    public void displayProfileTransactions(IBankServicePhysicalPersons bank) {
            getPhysicalPersonProfile(bank).displayProfileTransactions();
    }

    public void displayTransactionHistory(IBankServicePhysicalPersons bank) {
        getPhysicalPersonProfile(bank).displayTransactionHistory();
    }

    public void clearTransactionHistory(IBankServicePhysicalPersons bank) {
        getPhysicalPersonProfile(bank).clearTransactionHistory();
    }

    public void displayMulticurrencyCardTransactions(IMulticurrencyCard multicurrencyCard) {
        multicurrencyCard.displayMulticurrencyCardTransactions();
    }

    public void displayAllProfileTransactions() {
        for (int i = 0; i < physicalPersonProfiles.size(); i++) {
            physicalPersonProfiles.get(i).displayProfileTransactions();
        }
    }


    public void addAccountToMulticurrencyCard(IMulticurrencyCard multicurrencyCard, String currencyCodeAccount) {
        multicurrencyCard.addAccount(currencyCodeAccount);
    }

    public void switchAccountOfMulticurrencyCard(IMulticurrencyCard multicurrencyCard, String currencyCodeAccount) {
        multicurrencyCard.switchAccount(currencyCodeAccount);
    }

    public String toString() {
        return lastName + " " + firstName;
    }

}
