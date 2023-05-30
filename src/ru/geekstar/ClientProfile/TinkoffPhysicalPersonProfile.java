package ru.geekstar.ClientProfile;

import ru.geekstar.Bank.Bank;
import ru.geekstar.IOFile;
import ru.geekstar.PhysicalPerson.PhysicalPerson;

public class TinkoffPhysicalPersonProfile extends PhysicalPersonProfile {

    private float cashback;

    private float percentCashbackOfSumPay;

    private int miles;

    private float costOf1MileRUB;

    private float costOf1MileUSD;

    private float costOf1MileEUR;


    public float getCostOf1MileRUB() {
        return costOf1MileRUB;
    }

    public void setCostOf1MileRUB(float costOf1MileRUB) {
        this.costOf1MileRUB = costOf1MileRUB;
    }

    public float getCostOf1MileUSD() {
        return costOf1MileUSD;
    }

    public void setCostOf1MileUSD(float costOf1MileUSD) {
        this.costOf1MileUSD = costOf1MileUSD;
    }

    public float getCostOf1MileEUR() {
        return costOf1MileEUR;
    }

    public void setCostOf1MileEUR(float costOf1MileEUR) {
        this.costOf1MileEUR = costOf1MileEUR;
    }

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }


    public float getCashback() {
        return cashback;
    }

    public void setCashback(float cashback) {
        this.cashback = cashback;
    }

    public float getPercentCashbackOfSumPay() {
        return percentCashbackOfSumPay;
    }

    public void setPercentCashbackOfSumPay(float percentCashbackOfSumPay) {
        this.percentCashbackOfSumPay = percentCashbackOfSumPay;
    }


    public TinkoffPhysicalPersonProfile(Bank bank, PhysicalPerson physicalPerson) {
        super(bank, physicalPerson);
    }

    @Override
    // Вывод всех операций по всем картам и счетам профиля физического лица в Тинькове
    public String displayProfileTransactions() {
        // дополним метод уникальной информацией, присуще только Сберу
        String allTransactionsPhysicalPerson = "Все операции по картам и счетам клиента " + getPhysicalPerson().getFirstName() + " " + getPhysicalPerson().getLastName() +
                " в " + getBank().getBankName() + "Банке";

        String cashback = "Накоплено кэшбэка: " + getCashback();
        String balanceMiles = "Накоплено миль: " + getMiles();

        String headerProfileTransactions = allTransactionsPhysicalPerson + "\n" + cashback + "\n" + balanceMiles;

        String profileTransactions = headerProfileTransactions + "\n" + super.displayProfileTransactions();

        System.out.println(profileTransactions);
        IOFile.write(getPathToTransactionHistoryFile(), profileTransactions, true);

        return profileTransactions;
    }

}
