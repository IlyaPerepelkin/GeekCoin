package ClientProfile;

import Account.SberPayCardAccount;
import Account.SberSavingsAccount;
import Card.SberVisaGold;
import PhysicalPerson.PhysicalPerson;

public class PhysicalPersonProfile extends ClientProfile {

    private PhysicalPerson physicalPerson;

    private SberVisaGold[] cards = new SberVisaGold[5];

    private SberPayCardAccount[] payCardAccounts = new SberPayCardAccount[5];

    private SberSavingsAccount[] savingsAccounts = new SberSavingsAccount[15];

    private byte countCards;

    private byte countPayCardAccounts;

    private byte countSavingsAccounts;


    public PhysicalPerson getPhysicalPerson() {
        return physicalPerson;
    }

    public void setPhysicalPerson(PhysicalPerson physicalPerson) {
        this.physicalPerson = physicalPerson;
    }

    public SberVisaGold[] getCards() {
        return cards;
    }

    public void setCards(SberVisaGold[] cards) {
        this.cards = cards;
    }

    public SberPayCardAccount[] getPayCardAccounts() {
        return payCardAccounts;
    }

    public void setPayCardAccounts(SberPayCardAccount[] payCardAccounts) {
        this.payCardAccounts = payCardAccounts;
    }

    public SberSavingsAccount[] getSavingsAccounts() {
        return savingsAccounts;
    }

    public void setSavingsAccounts(SberSavingsAccount[] savingsAccounts) {
        this.savingsAccounts = savingsAccounts;
    }

    public byte getCountCards() {
        return countCards;
    }

    public void setCountCards(byte countCards) {
        this.countCards = countCards;
    }

    public byte getCountPayCardAccounts() {
        return countPayCardAccounts;
    }

    public void setCountPayCardAccounts(byte countPayCardAccounts) {
        this.countPayCardAccounts = countPayCardAccounts;
    }

    public byte getCountSavingsAccounts() {
        return countSavingsAccounts;
    }

    public void setCountSavingsAccounts(byte countSavingsAccounts) {
        this.countSavingsAccounts = countSavingsAccounts;
    }


    // Привязать платежный счет к профилю клиента
    public void addAccount(SberPayCardAccount payCardAccount) {
        payCardAccounts[countPayCardAccounts++] = payCardAccount;
    }

    // Привязать сберегательный счет к профилю клиента
    public void addAccount(SberSavingsAccount savingsAccount) {
        savingsAccounts[countSavingsAccounts++] = savingsAccount;
    }

    // Привязать карут к прфоилю клиента
    public void addCard(SberVisaGold card) {
        cards[countCards++] = card;
    }

    // проверить привязана ли карта к профилю клиента
    public boolean isClientCard(SberVisaGold card) {
        for (int idCard = 0; idCard < countCards; idCard++) {
            if (cards[idCard].equals(card)) return true;
        }
        return false;
    }

    // проверить привязан ли счет к профилю клиента
    public boolean isClientAccount(SberSavingsAccount account) {
        for (int idAccount = 0; idAccount < countSavingsAccounts; idAccount++) {
            if (savingsAccounts[idAccount].equals(account)) return true;
        }
        return false;
    }

    // Прибавить сумму перевода на карту к общей сумме совершенных оплат и переводов в сутки, чтобы контролировать лимиты
    public void updateTotalPaymentsTransfersDay(float sum, String fromCurrencyCode, SberVisaGold toCard) {
        // моя ли карта, на которую выполняем перевод
        boolean isMyCard = isClientCard(toCard);
        // если не моя карта, то обновляем общую сумму
        if (!isMyCard) updateTotalPaymentsTransfersDay(sum, fromCurrencyCode);
    }

    // Прибавить сумму перевода на счет к общей сумме совершенных оплат и переводов в сутки, чтобы контролировать лимиты
    public void updateTotalPaymentsTransfersDay(float sum, String fromCurrencyCode, SberSavingsAccount toAccount) {
        // мой ли счета, на который выполняем перевод
        boolean isMyAccount = isClientAccount(toAccount);
        // если не мой счет, то обновляем общую сумму
        if (!isMyAccount) updateTotalPaymentsTransfersDay(sum, fromCurrencyCode);
    }
}
