package PhysicalPerson;

import Account.SberSavingsAccount;
import Bank.Sberbank;
import Card.SberVisaGold;
import ClientProfile.SberPhysicalPersonProfile;

public class PhysicalPerson {

    private String firstName;

    private String lastName;

    private String telephone;

    private byte age;

    private char gender;

    private SberPhysicalPersonProfile physicalPersonProfile;


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

    public SberPhysicalPersonProfile getPhysicalPersonProfile() {
        return physicalPersonProfile;
    }

    public void setPhysicalPersonProfile(SberPhysicalPersonProfile physicalPersonProfile) {
        this.physicalPersonProfile = physicalPersonProfile;
    }


    public void registerToBank(Sberbank bank) {
        setPhysicalPersonProfile(bank.registerClientProfile(this));
    }

    public SberVisaGold openCard(Sberbank bank, SberVisaGold card, String currencyCode) {
        return bank.openCard(physicalPersonProfile, card, currencyCode);
    }

    public SberSavingsAccount openAccount(Sberbank bank, SberSavingsAccount account, String currencyCode) {
        return bank.openAccount(physicalPersonProfile, account, currencyCode);
    }

    public void depositingCash2Card(SberVisaGold toCard, float sumDepositing) {
        toCard.depositingCash2Card(sumDepositing);
    }

    public void payBayCard(SberVisaGold card, float sumPay, String byProductOrService) {
        card.payByCard(sumPay, byProductOrService);
    }

    public void payBayCard(SberVisaGold card, float sumPay, String byProductOrService, String country) {
        card.payByCard(sumPay, byProductOrService, country);
    }

    public void transferCard2Card(SberVisaGold fromCard, SberVisaGold toCard, float sumTransfer) {
        fromCard.transferCard2Card(toCard, sumTransfer);
    }

    public void transferCard2Account(SberVisaGold fromCard, SberSavingsAccount toAccount, float sumTransfer) {
        fromCard.transferCard2Account(toAccount, sumTransfer);
    }

    public void transferAccount2Card(SberSavingsAccount fromAccount, SberVisaGold toCard, float sumTransfer) {
        fromAccount.transferAccount2Card(toCard, sumTransfer);
    }

    public void depositingCardFromCard(SberVisaGold toCard, SberVisaGold fromCard, float sumDepositing) {
        toCard.depositingCardFromCard(fromCard, sumDepositing);
    }

    public void depositingCardFromAccount(SberVisaGold toCard, SberSavingsAccount fromAccount, float sumDepositing) {
        toCard.depositingCardFromAccount(fromAccount, sumDepositing);
    }

    public void depositingAccountFromCard(SberSavingsAccount toAccount, SberVisaGold fromCard, float sumDepositing) {
        toAccount.depositingAccountFromCard(fromCard, sumDepositing);
    }

    public void displayCardTransactions(SberVisaGold card) {
        card.displayCardTransactions();
    }

    public void displayAccountTransactions(SberSavingsAccount account) {
        account.displayAccountTransactions();
    }

    public void displayProfileTransactions() {
        physicalPersonProfile.displayProfileTransactions();
    }
}
