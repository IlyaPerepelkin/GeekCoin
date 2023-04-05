package PhysicalPerson;

import Account.Account;
import Account.SberPayCardAccount;
import Account.SberSavingsAccount;
import Bank.IBankServicePhysicalPerson;
import Card.IBonusCard;
import Card.Card;
import Card.IMulticurrencyCard;
import Card.IPaySystem.IPaySystem;
import ClientProfile.PhysicalPersonProfile;

public class PhysicalPerson {

    private String firstName;

    private String lastName;

    private String telephone;

    private byte age;

    private char gender;

    private PhysicalPersonProfile physicalPersonProfile;


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

    public PhysicalPersonProfile getPhysicalPersonProfile() {
        return physicalPersonProfile;
    }

    public void setPhysicalPersonProfile(PhysicalPersonProfile physicalPersonProfile) {
        this.physicalPersonProfile = physicalPersonProfile;
    }


    public void registerPhysicalPersonToBank(IBankServicePhysicalPerson bank) {
        setPhysicalPersonProfile(bank.registerPhysicalPersonProfile(this));
    }

    public Card openCard(IBankServicePhysicalPerson bank, Card card, String currencyCode, String pinCode) {
        return bank.openCard(physicalPersonProfile, card, currencyCode, pinCode);
    }

    public Account openAccount(IBankServicePhysicalPerson bank, Account account, String currencyCode) {
        return bank.openAccount(physicalPersonProfile, account, currencyCode);
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

    public void transferCard2Card(Card fromCard, Card toCard, float sumTransfer) {
        fromCard.transferCard2Card(toCard, sumTransfer);
    }

    public void transferCard2Account(Card fromCard, SberSavingsAccount toAccount, float sumTransfer) {
        fromCard.transferCard2Account(toAccount, sumTransfer);
    }

    public void transferAccount2Card(SberSavingsAccount fromAccount, Card toCard, float sumTransfer) {
        fromAccount.transferAccount2Card(toCard, sumTransfer);
    }

    public void transferAccount2Account(SberSavingsAccount fromAccount, SberSavingsAccount toAccount, float sumTransfer) {
        fromAccount.transferAccount2Account(toAccount, sumTransfer);
    }

    public void depositingCardFromCard(Card toCard, Card fromCard, float sumDepositing) {
        toCard.depositingCardFromCard(fromCard, sumDepositing);
    }

    public void depositingCardFromAccount(Card toCard, SberSavingsAccount fromAccount, float sumDepositing) {
        toCard.depositingCardFromAccount(fromAccount, sumDepositing);
    }

    public void depositingAccountFromCard(SberSavingsAccount toAccount, Card fromCard, float sumDepositing) {
        toAccount.depositingAccountFromCard(fromCard, sumDepositing);
    }

    public void depositingAccountFromAccount(SberSavingsAccount toAccount, SberSavingsAccount fromAccount, float sumDepositing) {
        toAccount.depositingAccountFromAccount(fromAccount, sumDepositing);
    }

    public float getExchangeRatePaySystem(IPaySystem paySystemCard, String currency, String currencyExchangeRate) {
        return paySystemCard.getExchangeRatePaySystem(currency, currencyExchangeRate);
    }

    public void displayCardTransactions(Card card) {
        card.displayCardTransactions();
    }

    public void displayAccountTransactions(SberSavingsAccount account) {
        account.displayAccountTransactions();
    }

    public void displayAccountTransactions(SberPayCardAccount account) {
        account.displayAccountTransactions();
    }

    public void displayProfileTransactions() {
        physicalPersonProfile.displayProfileTransactions();
    }


    public void addAccountToMulticurrencyCard(IBankServicePhysicalPerson bank, IMulticurrencyCard multicurrencyCard, String currencyCodeAccount) {
        multicurrencyCard.addAccount(bank, physicalPersonProfile, currencyCodeAccount);
    }

    public void switchAccountOfMulticurrencyCard(IMulticurrencyCard multicurrencyCard, String currencyCodeAccount) {
        multicurrencyCard.switchAccount(currencyCodeAccount);
    }

    public void payByCardBonuses(IBonusCard bonusCard, float sumPay, int bonusesPay, String buyProductOrService) {
        bonusCard.payByCardBonuses(sumPay, bonusesPay, buyProductOrService);
    }
}
