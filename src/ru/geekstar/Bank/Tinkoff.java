package ru.geekstar.Bank;

import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.ClientProfile.TinkoffPhysicalPersonProfile;
import ru.geekstar.PhysicalPerson.PhysicalPerson;

import java.util.ArrayList;

public class Tinkoff extends Bank implements IBankServicePhysicalPersons {

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

        // установить лимиты
        tinkoffPhysicalPersonProfile.setLimitPaymentsTransfersDayInRUB(1000000.00f);
        tinkoffPhysicalPersonProfile.setLimitPaymentsTransfersDayInUSD(50000.00f);
        tinkoffPhysicalPersonProfile.setLimitPaymentsTransfersDayInEUR(3500.00f);

        tinkoffPhysicalPersonProfile.setPercentCashbackOfSumPay(1.00f);

        tinkoffPhysicalPersonProfile.setCostOf1MileRUB(60.00f);
        tinkoffPhysicalPersonProfile.setCostOf1MileUSD(1.00f);
        tinkoffPhysicalPersonProfile.setCostOf1MileEUR(1.00f);

        // установить проценты комиссий
        tinkoffPhysicalPersonProfile.setPercentOfCommissionForPayHousingCommunalServices(1.8f);
        tinkoffPhysicalPersonProfile.setPercentOfCommissionForTransferInRUB(1.1f);
        tinkoffPhysicalPersonProfile.setPercentOfCommissionForTransferInUsdOrOtherCurrency(1.20f);

        // установить лимиты на суммы комиссий
        tinkoffPhysicalPersonProfile.setLimitCommissionTransferInRUB(3000.00f);
        tinkoffPhysicalPersonProfile.setLimitCommissionTransferInUsdOrEquivalentInOtherCurrency(100.00f);

        // и привязать профиль клиента к банку
        getClientProfiles().add(tinkoffPhysicalPersonProfile);

        return tinkoffPhysicalPersonProfile;
    }


    public float getCommissionOfTransferToClientBank(PhysicalPersonProfile clientProfile, float sum, String fromCurrencyCode) {
        return 0;
    }

    public ArrayList<Float> getExchangeRateBank(String currency, String currencyExchangeRate) {
        // TODO: Запрос к API банка
        ArrayList<Float> exchangeRateBank = new ArrayList<>();

        // курс доллара
        if (currency.equals("USD")) {
            // в рублях
            if (currencyExchangeRate.equals("RUB")) {
                exchangeRateBank.add(79.1f); // курс покупки
                exchangeRateBank.add(84.85f); // курс продажи
            }
            // в евро
            if (currencyExchangeRate.equals("EUR")) {
                exchangeRateBank.add(0.88f);
                exchangeRateBank.add(0.97f);
            }
        }

        // курс евро
        if (currency.equals("EUR")) {
            // в рублях
            if (currencyExchangeRate.equals("RUB")) {
                exchangeRateBank.add(85.15f);
                exchangeRateBank.add(91.4f);
            }
            // в долларах
            if (currencyExchangeRate.equals("USD")) {
                exchangeRateBank.add(1.02f);
                exchangeRateBank.add(1.13f);
            }
        }

        // курс рубля
        if (currency.equals("RUB")) {
            // в долларах
            if (currencyExchangeRate.equals("USD")) {
                exchangeRateBank.add(0.0117f);
                exchangeRateBank.add(0.0126f);
            }
            // в евро
            if (currencyExchangeRate.equals("EUR")) {
                exchangeRateBank.add(0.0109f);
                exchangeRateBank.add(0.0117f);
            }
        }

        return exchangeRateBank;
    }
}
