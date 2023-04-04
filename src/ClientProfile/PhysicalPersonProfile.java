package ClientProfile;

import Account.SavingsAccount;
import Account.SberPayCardAccount;
import Account.SberSavingsAccount;
import Card.Card;
import Card.SberVisaGold;
import PhysicalPerson.PhysicalPerson;

import java.util.Arrays;

public abstract class PhysicalPersonProfile extends ClientProfile {

    private PhysicalPerson physicalPerson;

    private Card[] cards = new Card[5];

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

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
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
        if (countPayCardAccounts < payCardAccounts.length) {
            payCardAccounts[countPayCardAccounts++] = payCardAccount;
        } else System.out.println("Массив аккаунтов переполнен");
    }

    // Привязать сберегательный счет к профилю клиента
    public void addAccount(SberSavingsAccount savingsAccount) {
        if (countSavingsAccounts < savingsAccounts.length) {
            savingsAccounts[countSavingsAccounts++] = savingsAccount;
        } else System.out.println("Массив аккаунтов переполнен");
    }

    // Привязать карту к профилю клиента
    public void addCard(Card card) {
        if (countCards < cards.length) {
            cards[countCards++] = card;
        } else System.out.println("Массив карт переполнен");
    }

    // проверить привязана ли карта к профилю клиента
    public boolean isClientCard(Card card) {
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
    public void updateTotalPaymentsTransfersDay(float sum, String fromCurrencyCode, Card toCard) {
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

    @Override
    // Вывод всех операций по всем картам и счетам профиля физического лица
    public void displayProfileTransactions() {
        System.out.println("Платежей и переводов за текущие сутки выполнено на сумму: " + getTotalPaymentsTransfersDayInRUB() +
                "₽ Доступный лимит: " + (getLimitPaymentsTransfersDayInRUB() - getTotalPaymentsTransfersDayInRUB()) + "₽ из " +
                getLimitPaymentsTransfersDayInRUB() + "₽");

        // для подсчета всех транзакций по всем счетам и картам клиента
        int countAllTransactions = 0;

        // подсчитать общее количество всех транзакций по платежным счетам(то есть картам)
        for (int idPayCardAccount = 0; idPayCardAccount < countPayCardAccounts; idPayCardAccount++) {
            countAllTransactions += payCardAccounts[idPayCardAccount].getAllPayCardAccountTransactions().length;
        }

        // и общее количество всех транзакций по сберегательным счетам
        for (int idSavingsAccount = 0; idSavingsAccount < countSavingsAccounts; idSavingsAccount++) {
            countAllTransactions += savingsAccounts[idSavingsAccount].getAllTransferDepositingTransactions().length;
        }

        // и объявить массив всех транзакций профиля клиента длинной равной количеству всех транзакций
        String[] allTransactions = new String[countAllTransactions];

        // теперь нужно перебрать платежные счета (карты)
        int destPos = 0;
        for (int idPayCardAccount = 0; idPayCardAccount < countPayCardAccounts; idPayCardAccount++) {
            String[] allPayCardAccountTransactions = payCardAccounts[idPayCardAccount].getAllPayCardAccountTransactions();
            System.arraycopy(allPayCardAccountTransactions, 0, allTransactions, destPos, allPayCardAccountTransactions.length);
            destPos += allPayCardAccountTransactions.length;
        }

        // и перебрать сберегательные счета
        for (int idSavingsAccount = 0; idSavingsAccount < countSavingsAccounts; idSavingsAccount++) {
            String[] allTransferDepositingTransactions = savingsAccounts[idSavingsAccount].getAllTransferDepositingTransactions();
            System.arraycopy(allTransferDepositingTransactions, 0, allTransactions, destPos, allTransferDepositingTransactions.length);
            destPos += allTransferDepositingTransactions.length;
        }

        // далее нужно отсортировать все транзакции по дате и времени
        Arrays.sort(allTransactions);

        // и осталось вывести все транзакции
        for (int idTransaction = 0; idTransaction < countAllTransactions; idTransaction++) {
            System.out.println("#" + (idTransaction + 1) + " " + allTransactions[idTransaction]);
        }

        System.out.println();

    }
}
