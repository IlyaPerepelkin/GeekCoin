package ClientProfile;

public class TinkoffPhysicalPersonProfile extends PhysicalPersonProfile {

    private float cashback;

    private float percentCashbackOfSumPay;

    private int miles;

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


    @Override
    // Вывод всех операций по всем картам и счетам профиля физического лица в Тинькове
    public void displayProfileTransactions() {
        // дополним метод уникальной информацией, присуще только Тинькову
        System.out.println("Все операции по картам и счетам клиента " + getPhysicalPerson().getFirstName() + " " + getPhysicalPerson().getLastName() +
                " в " + getBank().getBankName() + "Банке");

        System.out.println("Накопленный кэшбэк: " + getCashback());

        System.out.println("Накопленные милли: " + getMiles());

        // и вызываем родительскую версию метода
        super.displayProfileTransactions();
    }

}
