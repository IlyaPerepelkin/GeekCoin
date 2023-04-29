package PhysicalPerson;

import Account.Account;
import Account.PayCardAccount;
import Account.SavingsAccount;
import Account.SberPayCardAccount;
import Bank.IBankServicePhysicalPerson;
import Card.*;
import Card.IPaySystem.IPaySystem;
import ClientProfile.PhysicalPersonProfile;

import java.util.ArrayList;

public class PhysicalPerson {

    public static int personCount;

    private String firstName;

    private String lastName;

    private String telephone;

    private byte age;

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

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
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

    public PhysicalPerson(String firstName, String lastName, String telephone, byte age, char gender) {
        this(firstName, lastName, telephone);
        this.age = age;
        this.gender = gender;
    }

    public PhysicalPersonProfile getPhysicalPersonProfile(IBankServicePhysicalPerson bank) {
        for (int i = 0; i < physicalPersonProfiles.size(); i++) {
            PhysicalPersonProfile physicalPersonProfile = physicalPersonProfiles.get(i);
            if (physicalPersonProfile.getBank() == bank) {
                return physicalPersonProfile;
            }
        }
        return null;
    }

    public void registerPhysicalPersonToBank(IBankServicePhysicalPerson bank) {
        physicalPersonProfiles.add(bank.registerPhysicalPersonProfile(this));
    }

    public Card openCard(IBankServicePhysicalPerson bank, Class<? extends Card> classCard, Class<? extends Account> classAccount, String currencyCode, String pinCode) {
        return bank.openCard(getPhysicalPersonProfile(bank), classCard, classAccount, currencyCode, pinCode);
    }

    public Account openAccount(IBankServicePhysicalPerson bank, Account account, String currencyCode) {
        return bank.openAccount(getPhysicalPersonProfile(bank), account, currencyCode);
    }

    public void depositingCash2Card(Card toCard, float sumDepositing) {
        toCard.depositingCash2Card(sumDepositing);
    }

    public void payByCard(Card card, float sumPay, String byProductOrService, String pinCode) {
        card.payByCard(sumPay, byProductOrService, pinCode);
    }

    public void payByCard(Card card, float sumPay, String byProductOrService, String country, String pinCode) {
        card.payByCard(sumPay, byProductOrService, pinCode, country);
    }

    public void payByCardMiles(IAirlinesCard airlinesCard, int sumPay, int milesPay, String byProductOrService, String pinCode) {
        airlinesCard.payByCardMiles(sumPay, milesPay, byProductOrService, pinCode);
    }

    public void payByCardBonuses(IBonusCard bonusCard, float sumPay, int bonusesPay, String buyProductOrService, String pinCode) {
        bonusCard.payByCardBonuses(sumPay, bonusesPay, buyProductOrService, pinCode);
    }

    public void transferCard2Card(Card fromCard, Card toCard, float sumTransfer) {
        fromCard.transferCard2Card(toCard, sumTransfer);
    }

    public void transferCard2Account(Card fromCard, Account toAccount, float sumTransfer) {
        fromCard.transferCard2Account(toAccount, sumTransfer);
    }

    public void transferAccount2Card(Account fromAccount, Card toCard, float sumTransfer) {
        fromAccount.transferAccount2Card(toCard, sumTransfer);
    }

    public void transferAccount2Account(Account fromAccount, Account toAccount, float sumTransfer) {
        fromAccount.transferAccount2Account(toAccount, sumTransfer);
    }

    public void depositingCardFromCard(Card toCard, Card fromCard, float sumDepositing) {
        toCard.depositingCardFromCard(fromCard, sumDepositing);
    }

    public void depositingCardFromAccount(Card toCard, Account fromAccount, float sumDepositing) {
        toCard.depositingCardFromAccount(fromAccount, sumDepositing);
    }

    public void depositingAccountFromCard(Account toAccount, Card fromCard, float sumDepositing) {
        toAccount.depositingAccountFromCard(fromCard, sumDepositing);
    }

    public void depositingAccountFromAccount(Account toAccount, Account fromAccount, float sumDepositing) {
        toAccount.depositingAccountFromAccount(fromAccount, sumDepositing);
    }

    public void depositingCashback2Card(ICashbackCard toCard) {
        toCard.depositingCashback2Card();
    }

    public float getExchangeRatePaySystem(IPaySystem paySystemCard, String currency, String currencyExchangeRate) {
        return paySystemCard.getExchangeRatePaySystem(currency, currencyExchangeRate);
    }

    public void displayCardTransactions(Card card) {
        card.displayCardTransactions();
    }

    public void displayAccountTransactions(SavingsAccount account) {
        account.displayAccountTransactions();
    }

    public void displayAccountTransactions(SberPayCardAccount account) {
        account.displayAccountTransactions();
    }

    public void displayProfileTransactions(IBankServicePhysicalPerson bank) {
            getPhysicalPersonProfile(bank).displayProfileTransactions();
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


}
