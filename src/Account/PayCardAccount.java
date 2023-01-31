package Account;

import Card.SberVisaGold;
import Transaction.PayTransaction;

public class PayCardAccount extends Account {

    private SberVisaGold [] cards = new SberVisaGold[2];

    private PayTransaction [] payTransactions = new PayTransaction[50];

    private float blockedSum;

    private byte countCards;

    private int countPayTransactions;


    public SberVisaGold[] getCards() {
        return cards;
    }

    public void setCards(SberVisaGold[] cards) {
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
    public void addCard(SberVisaGold card) {
        cards[countCards++] = card;
    }

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
        payTransactions[countPayTransactions++] = payTransaction;
    }
}
