package ClientProfile;

import Bank.Bank;
import PhysicalPerson.PhysicalPerson;
import java.io.*;

public class TinkoffPhysicalPersonProfile extends PhysicalPersonProfile {

    private float cashback;

    private float percentCashbackOfSumPay;

    private int miles;

    private float costOfMileRUB;

    private float costOfMileUSD;

    private float costOfMileEUR;


    public float getCostOfMileRUB() {
        return costOfMileRUB;
    }

    public void setCostOfMileRUB(float costOfMileRUB) {
        this.costOfMileRUB = costOfMileRUB;
    }

    public float getCostOfMileUSD() {
        return costOfMileUSD;
    }

    public void setCostOfMileUSD(float costOfMileUSD) {
        this.costOfMileUSD = costOfMileUSD;
    }

    public float getCostOfMileEUR() {
        return costOfMileEUR;
    }

    public void setCostOfMileEUR(float costOfMileEUR) {
        this.costOfMileEUR = costOfMileEUR;
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
    public void displayProfileTransactions() {
        // дополним метод уникальной информацией, присуще только Тинькову
        System.out.println("Все операции по картам и счетам клиента " + getPhysicalPerson().getFirstName() + " " + getPhysicalPerson().getLastName() +
                " в " + getBank().getBankName() + "Банке");

        System.out.println("Накопленный кэшбэк: " + getCashback());

        System.out.println("Накопленные милли: " + getMiles());

        IOFile.write(getPathToTransactionHistoryFile(), allTransactions, false);

        // и вызываем родительскую версию метода
        super.displayProfileTransactions();
    }

}
