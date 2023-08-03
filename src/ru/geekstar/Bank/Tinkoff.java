package ru.geekstar.Bank;

import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.ClientProfile.TinkoffPhysicalPersonProfile;
import ru.geekstar.Currency;
import ru.geekstar.PhysicalPerson.PhysicalPerson;

import java.util.ArrayList;

public class Tinkoff extends Bank implements IBankServicePhysicalPersons {

    public static final String TINKOFF;

    public static final float LIMIT_PAYMENTS_TRANSFERS_DAY_IN_RUB = 1000000.00f;
    public static final float LIMIT_PAYMENTS_TRANSFERS_DAY_IN_USD = 50000.00f;
    public static final float LIMIT_PAYMENTS_TRANSFERS_DAY_IN_EUR = 3500.00f;
    public static final float PERCENT_CASHBACK_OF_SUM_PAY = 1.00f;
    public static final float COST_OF_1_MILE_RUB = 60.00f;
    public static final float COST_OF_1_MILE_USD = 1.00f;
    public static final float COST_OF_1_MILE_EUR = 1.00f;
    public static final float PERCENT_OF_COMMISSION_FOR_PAY_HOUSING_COMMUNAL_SERVICES = 1.8f;
    public static final float PERCENT_OF_COMMISSION_FOR_TRANSFER_IN_RUB = 1.1f;
    public static final float PERCENT_OF_COMMISSION_FOR_TRANSFER_IN_USD_OR_OTHER_CURRENCY = 1.20f;
    public static final float LIMIT_COMMISSION_TRANSFER_IN_RUB = 3000.00f;
    public static final float LIMIT_COMMISSION_TRANSFER_IN_USD_OR_EQUIVALENT_IN_OTHER_CURRENCY = 100.00f;

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
        tinkoffPhysicalPersonProfile.setLimitPaymentsTransfersDayInRUB(LIMIT_PAYMENTS_TRANSFERS_DAY_IN_RUB);
        tinkoffPhysicalPersonProfile.setLimitPaymentsTransfersDayInUSD(LIMIT_PAYMENTS_TRANSFERS_DAY_IN_USD);
        tinkoffPhysicalPersonProfile.setLimitPaymentsTransfersDayInEUR(LIMIT_PAYMENTS_TRANSFERS_DAY_IN_EUR);

        tinkoffPhysicalPersonProfile.setPercentCashbackOfSumPay(PERCENT_CASHBACK_OF_SUM_PAY);

        tinkoffPhysicalPersonProfile.setCostOf1MileRUB(COST_OF_1_MILE_RUB);
        tinkoffPhysicalPersonProfile.setCostOf1MileUSD(COST_OF_1_MILE_USD);
        tinkoffPhysicalPersonProfile.setCostOf1MileEUR(COST_OF_1_MILE_EUR);

        // установить проценты комиссий
        tinkoffPhysicalPersonProfile.setPercentOfCommissionForPayHousingCommunalServices(PERCENT_OF_COMMISSION_FOR_PAY_HOUSING_COMMUNAL_SERVICES);
        tinkoffPhysicalPersonProfile.setPercentOfCommissionForTransferInRUB(PERCENT_OF_COMMISSION_FOR_TRANSFER_IN_RUB);
        tinkoffPhysicalPersonProfile.setPercentOfCommissionForTransferInUsdOrOtherCurrency(PERCENT_OF_COMMISSION_FOR_TRANSFER_IN_USD_OR_OTHER_CURRENCY);

        // установить лимиты на суммы комиссий
        tinkoffPhysicalPersonProfile.setLimitCommissionTransferInRUB(LIMIT_COMMISSION_TRANSFER_IN_RUB);
        tinkoffPhysicalPersonProfile.setLimitCommissionTransferInUsdOrEquivalentInOtherCurrency(LIMIT_COMMISSION_TRANSFER_IN_USD_OR_EQUIVALENT_IN_OTHER_CURRENCY);

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
