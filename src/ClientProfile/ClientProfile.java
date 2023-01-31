package ClientProfile;

import Bank.Sberbank;

public class ClientProfile {

    private Sberbank bank;

    private float limitPaymentsTransfersDayInRUB;

    private float totalPaymentsTransfersDayInRUB;

    private float limitPaymentsTransfersDayInUSD;

    private float totalPaymentsTransfersDayInUSD;

    private float limitPaymentsTransfersDayInEUR;

    private float totalPaymentsTransfersDayInEUR;


    private float percentOfCommissionForTransferInRUB;

    private float percentOfCommissionForTransferInUsdOrOtherCurrency;

    private float percentOfCommissionForPayHousingCommunalServices;


    private float limitCommissionTransferInRUB;

    private float limitCommissionTransferInUsdOrEquivalentInOtherCurrency;


    public Sberbank getBank() {
        return bank;
    }

    public void setBank(Sberbank bank) {
        this.bank = bank;
    }

    public float getLimitPaymentsTransfersDayInRUB() {
        return limitPaymentsTransfersDayInRUB;
    }

    public void setLimitPaymentsTransfersDayInRUB(float limitPaymentsTransfersDayInRUB) {
        this.limitPaymentsTransfersDayInRUB = limitPaymentsTransfersDayInRUB;
    }

    public float getTotalPaymentsTransfersDayInRUB() {
        return totalPaymentsTransfersDayInRUB;
    }

    public void setTotalPaymentsTransfersDayInRUB(float totalPaymentsTransfersDayInRUB) {
        this.totalPaymentsTransfersDayInRUB = totalPaymentsTransfersDayInRUB;
    }

    public float getLimitPaymentsTransfersDayInUSD() {
        return limitPaymentsTransfersDayInUSD;
    }

    public void setLimitPaymentsTransfersDayInUSD(float limitPaymentsTransfersDayInUSD) {
        this.limitPaymentsTransfersDayInUSD = limitPaymentsTransfersDayInUSD;
    }

    public float getTotalPaymentsTransfersDayInUSD() {
        return totalPaymentsTransfersDayInUSD;
    }

    public void setTotalPaymentsTransfersDayInUSD(float totalPaymentsTransfersDayInUSD) {
        this.totalPaymentsTransfersDayInUSD = totalPaymentsTransfersDayInUSD;
    }

    public float getLimitPaymentsTransfersDayInEUR() {
        return limitPaymentsTransfersDayInEUR;
    }

    public void setLimitPaymentsTransfersDayInEUR(float limitPaymentsTransfersDayInEUR) {
        this.limitPaymentsTransfersDayInEUR = limitPaymentsTransfersDayInEUR;
    }

    public float getTotalPaymentsTransfersDayInEUR() {
        return totalPaymentsTransfersDayInEUR;
    }

    public void setTotalPaymentsTransfersDayInEUR(float totalPaymentsTransfersDayInEUR) {
        this.totalPaymentsTransfersDayInEUR = totalPaymentsTransfersDayInEUR;
    }

    public float getPercentOfCommissionForTransferInRUB() {
        return percentOfCommissionForTransferInRUB;
    }

    public void setPercentOfCommissionForTransferInRUB(float percentOfCommissionForTransferInRUB) {
        this.percentOfCommissionForTransferInRUB = percentOfCommissionForTransferInRUB;
    }

    public float getPercentOfCommissionForTransferInUsdOrOtherCurrency() {
        return percentOfCommissionForTransferInUsdOrOtherCurrency;
    }

    public void setPercentOfCommissionForTransferInUsdOrOtherCurrency(float percentOfCommissionForTransferInUsdOrOtherCurrency) {
        this.percentOfCommissionForTransferInUsdOrOtherCurrency = percentOfCommissionForTransferInUsdOrOtherCurrency;
    }

    public float getPercentOfCommissionForPayHousingCommunalServices() {
        return percentOfCommissionForPayHousingCommunalServices;
    }

    public void setPercentOfCommissionForPayHousingCommunalServices(float percentOfCommissionForPayHousingCommunalServices) {
        this.percentOfCommissionForPayHousingCommunalServices = percentOfCommissionForPayHousingCommunalServices;
    }

    public float getLimitCommissionTransferInRUB() {
        return limitCommissionTransferInRUB;
    }

    public void setLimitCommissionTransferInRUB(float limitCommissionTransferInRUB) {
        this.limitCommissionTransferInRUB = limitCommissionTransferInRUB;
    }

    public float getLimitCommissionTransferInUsdOrEquivalentInOtherCurrency() {
        return limitCommissionTransferInUsdOrEquivalentInOtherCurrency;
    }

    public void setLimitCommissionTransferInUsdOrEquivalentInOtherCurrency(float limitCommissionTransferInUsdOrEquivalentInOtherCurrency) {
        this.limitCommissionTransferInUsdOrEquivalentInOtherCurrency = limitCommissionTransferInUsdOrEquivalentInOtherCurrency;
    }

    // Проверить не превышен ли лимит по оплатам и переводам в сутки
    public boolean exceededLimitPaymentsTransfersDay(float sum, String currencyCode) {
        if (currencyCode.equals("RUB") && totalPaymentsTransfersDayInRUB + sum > limitPaymentsTransfersDayInRUB) return true;
        if (currencyCode.equals("USD") && totalPaymentsTransfersDayInUSD + sum > limitPaymentsTransfersDayInUSD) return true;
        if (currencyCode.equals("EUR") && totalPaymentsTransfersDayInEUR + sum > limitPaymentsTransfersDayInEUR) return true;
        return false;
    }

    // прибавить сумму оплаты или перевода к общей сумме совершенных оплат и переводов в сутки
    public void updateTotalPaymentsTransfersDay(float sum, String currencyCode) {
        if (currencyCode.equals("RUB")) totalPaymentsTransfersDayInRUB += sum;
        if (currencyCode.equals("USD")) totalPaymentsTransfersDayInUSD += sum;
        if (currencyCode.equals("EUR")) totalPaymentsTransfersDayInEUR += sum;
    }
}
