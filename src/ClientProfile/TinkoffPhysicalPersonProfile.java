package ClientProfile;

public class TinkoffPhysicalPersonProfile extends PhysicalPersonProfile {

    private float percentBonusOfSumPay;

    private float limitTransfersToClientTinkoffWithoutCommissionMonthInRUB;

    private float cashback;

    private float percentCashbackOfSumPay;

    private float totalTransfersToClientTinkoffWithoutCommissionMonthInRUB;


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

    public float getPercentBonusOfSumPay() {
        return percentBonusOfSumPay;
    }

    public void setPercentBonusOfSumPay(float percentBonusOfSumPay) {
        this.percentBonusOfSumPay = percentBonusOfSumPay;
    }

    public float getLimitTransfersToClientTinkoffWithoutCommissionMonthInRUB() {
        return limitTransfersToClientTinkoffWithoutCommissionMonthInRUB;
    }

    public void setLimitTransfersToClientTinkoffWithoutCommissionMonthInRUB(float limitTransfersToClientSberWithoutCommissionMonthInRUB) {
        this.limitTransfersToClientTinkoffWithoutCommissionMonthInRUB = limitTransfersToClientSberWithoutCommissionMonthInRUB;
    }

    public float getTotalTransfersToClientTinkoffWithoutCommissionMonthInRUB() {
        return totalTransfersToClientTinkoffWithoutCommissionMonthInRUB;
    }

    public void setTotalTransfersToClientTinkoffWithoutCommissionMonthInRUB(float totalTransfersToClientTinkoffWithoutCommissionMonthInRUB) {
        this.totalTransfersToClientTinkoffWithoutCommissionMonthInRUB = totalTransfersToClientTinkoffWithoutCommissionMonthInRUB;
    }

    @Override
    // Вывод всех операций по всем картам и счетам профиля физического лица в Тинькове
    public void displayProfileTransactions() {
        System.out.println(getPhysicalPerson() + getBank().getBankName() + "Накопленный кэшбэк:" + getCashback());

        // дополним метод уникальной информацией, присуще только Тинькову
        System.out.println("Все операции по картам и счетам клиента " + getPhysicalPerson().getFirstName() + " " + getPhysicalPerson().getLastName() +
                " в " + getBank().getBankName() + "Банке");

        System.out.println("Переводы клиентам " + getBank().getBankName() +
                " без комиссии за текущий месяц: " + getTotalTransfersToClientTinkoffWithoutCommissionMonthInRUB() + "₽ Доступный лимит: " +
                (getLimitTransfersToClientTinkoffWithoutCommissionMonthInRUB() - getTotalTransfersToClientTinkoffWithoutCommissionMonthInRUB()) + "₽ из " +
                getLimitTransfersToClientTinkoffWithoutCommissionMonthInRUB() + "₽");
        
        // и вызываем родительскую версию метода
        super.displayProfileTransactions();
    }

}
