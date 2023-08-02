package ru.geekstar.Bank;

import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.ClientProfile.TinkoffPhysicalPersonProfile;
import ru.geekstar.Currency;
import ru.geekstar.PhysicalPerson.PhysicalPerson;

import java.util.ArrayList;

public class Tinkoff extends Bank implements IBankServicePhysicalPersons {

    public static final String TINKOFF;

    public static final float limitPaymentsTransfersDayInRUB = 1000000.00f;
    public static final float limitPaymentsTransfersDayInUSD = 50000.00f;
    public static final float limitPaymentsTransfersDayInEUR = 3500.00f;
    public static final float percentCashbackOfSumPay = 1.00f;
    public static final float costOf1MileRUB = 60.00f;
    public static final float costOf1MileUSD = 1.00f;
    public static final float costOf1MileEUR = 1.00f;
    public static final float percentOfCommissionForPayHousingCommunalServices = 1.8f;
    public static final float percentOfCommissionForTransferInRUB = 1.1f;
    public static final float percentOfCommissionForTransferInUsdOrOtherCurrency = 1.20f;
    public static final float limitCommissionTransferInRUB = 3000.00f;
    public static final float limitCommissionTransferInUsdOrEquivalentInOtherCurrency = 100.00f;

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
        tinkoffPhysicalPersonProfile.setLimitPaymentsTransfersDayInRUB(limitPaymentsTransfersDayInRUB);
        tinkoffPhysicalPersonProfile.setLimitPaymentsTransfersDayInUSD(limitPaymentsTransfersDayInUSD);
        tinkoffPhysicalPersonProfile.setLimitPaymentsTransfersDayInEUR(limitPaymentsTransfersDayInEUR);

        tinkoffPhysicalPersonProfile.setPercentCashbackOfSumPay(percentCashbackOfSumPay);

        tinkoffPhysicalPersonProfile.setCostOf1MileRUB(costOf1MileRUB);
        tinkoffPhysicalPersonProfile.setCostOf1MileUSD(costOf1MileUSD);
        tinkoffPhysicalPersonProfile.setCostOf1MileEUR(costOf1MileEUR);

        // установить проценты комиссий
        tinkoffPhysicalPersonProfile.setPercentOfCommissionForPayHousingCommunalServices(percentOfCommissionForPayHousingCommunalServices);
        tinkoffPhysicalPersonProfile.setPercentOfCommissionForTransferInRUB(percentOfCommissionForTransferInRUB);
        tinkoffPhysicalPersonProfile.setPercentOfCommissionForTransferInUsdOrOtherCurrency(percentOfCommissionForTransferInUsdOrOtherCurrency);

        // установить лимиты на суммы комиссий
        tinkoffPhysicalPersonProfile.setLimitCommissionTransferInRUB(limitCommissionTransferInRUB);
        tinkoffPhysicalPersonProfile.setLimitCommissionTransferInUsdOrEquivalentInOtherCurrency(limitCommissionTransferInUsdOrEquivalentInOtherCurrency);

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
        if (currency.equals(Currency.USD.toString())) {
            // в рублях
            if (currencyExchangeRate.equals(Currency.RUB.toString())) {
                exchangeRateBank.add(79.1f); // курс покупки
                exchangeRateBank.add(84.85f); // курс продажи
            }
            // в евро
            if (currencyExchangeRate.equals(Currency.EUR.toString())) {
                exchangeRateBank.add(0.88f);
                exchangeRateBank.add(0.97f);
            }
        }

        // курс евро
        if (currency.equals(Currency.EUR.toString())) {
            // в рублях
            if (currencyExchangeRate.equals(Currency.RUB.toString())) {
                exchangeRateBank.add(85.15f);
                exchangeRateBank.add(91.4f);
            }
            // в долларах
            if (currencyExchangeRate.equals(Currency.USD.toString())) {
                exchangeRateBank.add(1.02f);
                exchangeRateBank.add(1.13f);
            }
        }

        // курс рубля
        if (currency.equals(Currency.RUB.toString())) {
            // в долларах
            if (currencyExchangeRate.equals(Currency.USD.toString())) {
                exchangeRateBank.add(0.0117f);
                exchangeRateBank.add(0.0126f);
            }
            // в евро
            if (currencyExchangeRate.equals(Currency.EUR.toString())) {
                exchangeRateBank.add(0.0109f);
                exchangeRateBank.add(0.0117f);
            }
        }

        return exchangeRateBank;
    }
}
