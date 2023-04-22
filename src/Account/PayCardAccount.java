package Account;

import Card.Card;
import Transaction.PayTransaction;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class PayCardAccount extends Account {

    private ArrayList<Card> cards = new ArrayList<>();

    private ArrayList<PayTransaction> payTransactions = new ArrayList<>();

    private float blockedSum;


    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<PayTransaction> getPayTransactions() {
        return payTransactions;
    }

    public void setPayTransactions(ArrayList<PayTransaction> payTransactions) {
        this.payTransactions = payTransactions;
    }

    public float getBlockedSum() {
        return blockedSum;
    }

    public void setBlockedSum(float blockedSum) {
        this.blockedSum = blockedSum;
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
/*
    // Добавить транзакцию об оплате
    public void addPayTransaction(PayTransaction payTransaction) {
        payTransactions.add(payTransaction);
    }
*/
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
        String[] allPayTransactions = new String[getPayTransactions().size()];

        int countAllPayTransactions = 0;
        // перебираем транзакции оплаты и добавляем их в массив в человеко-читаемом формате
        for (int idTransaction = 0; idTransaction < payTransactions.size(); idTransaction++) {
            allPayTransactions[countAllPayTransactions++] = payTransactions.get(idTransaction).getStringTransaction();
        }

        return allPayTransactions;
    }
}
