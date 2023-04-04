package Account;

import Card.Card;
import Card.SberVisaGold;
import Transaction.PayTransaction;

import java.util.Arrays;

public abstract class PayCardAccount extends Account {

    private Card [] cards = new Card[2];

    private PayTransaction [] payTransactions = new PayTransaction[50];

    private float blockedSum;

    private byte countCards;

    private int countPayTransactions;


    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public PayTransaction[] getPayTransactions() {
        return payTransactions;
    }

    public void setPayTransactions(PayTransaction[] payTransactions) {
        this.payTransactions = payTransactions;
    }

    public float getBlockedSum() {
        return blockedSum;
    }

    public void setBlockedSum(float blockedSum) {
        this.blockedSum = blockedSum;
    }

    public byte getCountCards() {
        return countCards;
    }

    public void setCountCards(byte countCards) {
        this.countCards = countCards;
    }

    public int getCountPayTransactions() {
        return countPayTransactions;
    }

    public void setCountPayTransactions(int countPayTransactions) {
        this.countPayTransactions = countPayTransactions;
    }

    // Привязать карту к платежному счету
    public void addCard(Card card) {
        if (countCards < cards.length) {
            cards[countCards++] = card;
        } else System.out.println("Массив карт переполнен");
    }

    // Блокировать сумму на счете карты
    public boolean blockSum(float sum) {
        // сохранить сумму на счете как зарезервированную
        blockedSum += sum;
        // и списать зарезервированную сумму со счета, чтобы ее нельзя было использовать одновременно при совершении других операций
        withdrawal(sum);
        return true;
    }

    // Списать зарезервированную сумму со счета карты
    public boolean writeOffBlockedSum(float sum) {
        blockedSum -= sum;
        return true;
    }

    // Добавить транзакцию об оплате
    public void addPayTransaction(PayTransaction payTransaction) {
        if (countPayTransactions < payTransactions.length) {
            payTransactions[countPayTransactions++] = payTransaction;
        } else System.out.println("Массив транзакций оплаты переполнен");
    }

    @Override
    // Вывести транзакции по счету
    public void displayAccountTransactions() {
        // сформировать общий массив транзакций по платежному счету в человеко-читаемом формате
        String[] allPayCardAccountTransactions = getAllAccountTransactions();

        // отсортировать транзакции по дате
        Arrays.sort(allPayCardAccountTransactions);

        // вывести все транзакции
        for (int idTransaction = 0; idTransaction < allPayCardAccountTransactions.length; idTransaction++) {
            System.out.println("#" + (idTransaction + 1) + " " + allPayCardAccountTransactions[idTransaction]);
        }

    }

    @Override
    public String[] getAllAccountTransactions() {
        // сформировать общий массив транзакций перевода и пополнения в человеко-читаемом формате
        String[] allTransferDepositingTransactions = super.getAllAccountTransactions();
        // сформировать массив транзакций оплаты в человеко-читаемом формате
        String[] allPayTransactions = getAllPayTransactions();

        // объявляем общий массив всех транзакций по платежному счету длиной равной общему количеству транзакций
        String[] allTransactions = new String[allTransferDepositingTransactions.length + allPayTransactions.length];

        // объединить массивы в один массив
        // копировать массив транзакций перевода и пополнения в общий массив всех транзакций
        System.arraycopy(allTransferDepositingTransactions, 0, allTransactions, 0, allTransferDepositingTransactions.length);
        // копировать массив транзакций оплаты в общий массив всех транзакций
        System.arraycopy(allPayTransactions, 0, allTransactions, allTransferDepositingTransactions.length, allPayTransactions.length);

        return allTransactions;

    }

    private String[] getAllPayTransactions() {
        // объявить массив транзакций оплаты по платежному счету длиной равной количеству транзакций
        String[] allPayTransactions = new String[countPayTransactions];

        int countAllPayTransactions = 0;
        // перебираем транзакции оплаты и добавляем их в массив в человеко-читаемом формате
        for (int idTransaction = 0; idTransaction < countPayTransactions; idTransaction++) {
            allPayTransactions[countAllPayTransactions++] = payTransactions[idTransaction].getStringTransaction();
        }

        return allPayTransactions;
    }
}
