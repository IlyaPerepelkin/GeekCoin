package ru.geekstar.Bank;

import ru.geekstar.ClientProfile.ClientProfile;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.ClientProfile.TinkoffPhysicalPersonProfile;
import ru.geekstar.PhysicalPerson.PhysicalPerson;

import java.util.ArrayList;

public class Tinkoff extends Bank implements IBankServicePhysicalPerson {

    public static final String TINKOFF;

    static {
        TINKOFF = "Тинькофф";
        System.out.println(TINKOFF + " для физических лиц");
    }

    public Tinkoff() {
        this(TINKOFF);
    }

    public Tinkoff(String bankName) {
        super(bankName);
    }

    public PhysicalPersonProfile registerPhysicalPersonProfile(PhysicalPerson physicalPerson) {
        // создать профиль клиента
        TinkoffPhysicalPersonProfile tinkoffPhysicalPersonProfile = new TinkoffPhysicalPersonProfile(this, physicalPerson);

        tinkoffPhysicalPersonProfile.setPercentCashbackOfSumPay(0.01f);

        // установить лимиты
        tinkoffPhysicalPersonProfile.setLimitPaymentsTransfersDayInRUB(1000000.00f);
        tinkoffPhysicalPersonProfile.setLimitPaymentsTransfersDayInUSD(50000.00f);
        tinkoffPhysicalPersonProfile.setLimitPaymentsTransfersDayInEUR(3500.00f);

        // установить проценты комиссий
        tinkoffPhysicalPersonProfile.setPercentOfCommissionForPayHousingCommunalServices(1.8f);
        tinkoffPhysicalPersonProfile.setPercentOfCommissionForTransferInRUB(1.1f);
        tinkoffPhysicalPersonProfile.setPercentOfCommissionForTransferInUsdOrOtherCurrency(1.20f);

        // установить лимиты на суммы комиссий
        tinkoffPhysicalPersonProfile.setLimitCommissionTransferInRUB(3000.00f);
        tinkoffPhysicalPersonProfile.setLimitCommissionTransferInUsdOrEquivalentInOtherCurrency(100.00f);

        tinkoffPhysicalPersonProfile.setCostOfMileRUB(60.00f);
        tinkoffPhysicalPersonProfile.setCostOfMileUSD(1.00f);
        tinkoffPhysicalPersonProfile.setCostOfMileEUR(1.00f);

        return tinkoffPhysicalPersonProfile;
    }

    public float getCommissionOfTransferToClientBank(ClientProfile clientProfile, float sum, String fromCurrencyCode) {
        return 0;
    }

    public ArrayList<Float> getExchangeRateBank(String currency, String currencyExchangeRate) {
        // TODO: Запрос к API банка
        ArrayList<Float> exchangeRateBank = new ArrayList<>();
        // курс доллара к рублю
        if (currency != null && currency.equals("USD") && currencyExchangeRate.equals("RUB")) {
            exchangeRateBank.add(83.34f); // курс покупки
            exchangeRateBank.add(81.67f); // курс продажи
        }
        // курс евро к рублю
        if (currency != null && currency.equals("EUR") && currencyExchangeRate.equals("RUB")) {
            exchangeRateBank.add(85.12f); // курс покупки
            exchangeRateBank.add(81.67f); // курс продажи
        }
        return exchangeRateBank;
    }


}
