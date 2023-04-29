package ClientProfile;

import Account.Account;
import Bank.Bank;
import Card.Card;
import PhysicalPerson.PhysicalPerson;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class PhysicalPersonProfile extends ClientProfile {

    public PhysicalPersonProfile(Bank bank, PhysicalPerson physicalPerson) {
        super(bank, physicalPerson);
    }

    private PhysicalPerson physicalPerson;

    private ArrayList<Card> cards = new ArrayList<>();

    private ArrayList<Account> accounts = new ArrayList<>();


    public PhysicalPerson getPhysicalPerson() {
        return physicalPerson;
    }

    public void setPhysicalPerson(PhysicalPerson physicalPerson) {
        this.physicalPerson = physicalPerson;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }


    // проверить привязана ли карта к профилю клиента
    public boolean isClientCard(Card card) {
        return cards.contains(card);
    }

    // проверить привязан ли счет к профилю клиента
    public boolean isClientAccount(Account account) {
        return accounts.contains(account);
    }

    // Прибавить сумму перевода на карту к общей сумме совершенных оплат и переводов в сутки, чтобы контролировать лимиты
    public void updateTotalPaymentsTransfersDay(float sum, String fromCurrencyCode, Card toCard) {
        // моя ли карта, на которую выполняем перевод
        boolean isMyCard = isClientCard(toCard);
        // если не моя карта, то обновляем общую сумму
        if (!isMyCard) updateTotalPaymentsTransfersDay(sum, fromCurrencyCode);
    }

    // Прибавить сумму перевода на счет к общей сумме совершенных оплат и переводов в сутки, чтобы контролировать лимиты
    public void updateTotalPaymentsTransfersDay(float sum, String fromCurrencyCode, Account toAccount) {
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

        // подсчитать общее количество всех транзакций по всем счетам
        for (int idAccount = 0; idAccount < accounts.size(); idAccount++) {
            countAllTransactions += accounts.get(idAccount).getAllAccountTransactions().length;
        }

        // и объявить массив всех транзакций профиля клиента длинной равной количеству всех транзакций
        String[] allTransactions = new String[countAllTransactions];

        // теперь нужно перебрать все счета
        int destPos = 0;
        for (int idAccount = 0; idAccount < accounts.size(); idAccount++) {
            String[] allAccountTransactions = accounts.get(idAccount).getAllAccountTransactions();
            System.arraycopy(allAccountTransactions, 0, allTransactions, destPos, allAccountTransactions.length);
            destPos += allAccountTransactions.length;
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
