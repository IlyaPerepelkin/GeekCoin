package ru.geekstar.Bank;

import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.ClientProfile.SberPhysicalPersonProfile;
import ru.geekstar.Currency;
import ru.geekstar.PhysicalPerson.PhysicalPerson;

import java.util.ArrayList;

public class Sberbank extends Bank implements IBankServicePhysicalPersons {

    public static final String SBER;
    public static final float percentBonusOfSumPay = 0.5f;
    public static final float limitPaymentsTransfersDayInRUB = 1000000.00f;
    public static final float limitPaymentsTransfersDayInUSD = 50000.00f;
    public static final float limitPaymentsTransfersDayInEUR = 3800.00f;
    public static final float limitTransfersToClientSberWithoutCommissionMonthInRUB = 50000.00f;
    public static final float percentOfCommissionForPayHousingCommunalServices = 2.0f;
    public static final float percentOfCommissionForTransferInRUB = 1.0f;
    public static final float percentOfCommissionForTransferInUsdOrOtherCurrency = 1.25f;
    public static final float limitCommissionTransferInRUB = 3000.00f;
    public static final float limitCommissionTransferInUsdOrEquivalentInOtherCurrency = 100.00f;

    static {
        SBER = "Сбер";
        System.out.println(SBER + " для физических лиц");
    }

    public Sberbank() {
        this(SBER);
    }

    public Sberbank(String bankName) {
        super(bankName);
    }

    // Зарегистрировать профиль физ лица
    @Override
    public PhysicalPersonProfile registerPhysicalPersonProfile(PhysicalPerson physicalPerson) {
        // создать профиль клиента
        SberPhysicalPersonProfile sberPhysicalPersonProfile = new SberPhysicalPersonProfile(this, physicalPerson);

        sberPhysicalPersonProfile.setPercentBonusOfSumPay(percentBonusOfSumPay);

        // установить лимиты
        sberPhysicalPersonProfile.setLimitPaymentsTransfersDayInRUB(limitPaymentsTransfersDayInRUB);
        sberPhysicalPersonProfile.setLimitPaymentsTransfersDayInUSD(limitPaymentsTransfersDayInUSD);
        sberPhysicalPersonProfile.setLimitPaymentsTransfersDayInEUR(limitPaymentsTransfersDayInEUR);
        sberPhysicalPersonProfile.setLimitTransfersToClientSberWithoutCommissionMonthInRUB(limitTransfersToClientSberWithoutCommissionMonthInRUB);

        // установить проценты комиссий
        sberPhysicalPersonProfile.setPercentOfCommissionForPayHousingCommunalServices(percentOfCommissionForPayHousingCommunalServices);
        sberPhysicalPersonProfile.setPercentOfCommissionForTransferInRUB(percentOfCommissionForTransferInRUB);
        sberPhysicalPersonProfile.setPercentOfCommissionForTransferInUsdOrOtherCurrency(percentOfCommissionForTransferInUsdOrOtherCurrency);

        // установить лимиты на суммы комиссий
        sberPhysicalPersonProfile.setLimitCommissionTransferInRUB(limitCommissionTransferInRUB);
        sberPhysicalPersonProfile.setLimitCommissionTransferInUsdOrEquivalentInOtherCurrency(limitCommissionTransferInUsdOrEquivalentInOtherCurrency);

        // и привязать профиль клиента к банку
        getClientProfiles().add(sberPhysicalPersonProfile);

        return sberPhysicalPersonProfile;
    }

    @Override
    // Предоставить обменный курс валют Сбера
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


    // Рассчитать комиссию за перевод клиенту моего банка Сбер
    public float getCommissionOfTransferToClientBank(PhysicalPersonProfile clientProfile, float sum, String fromCurrencyCode) {
        // по умолчанию комиссия 0
        float commission = 0;
        // если сумма перевода в рублях
        if (fromCurrencyCode.equals(Currency.RUB.toString())) {
            // и если превышен лимит по переводам клиентам Сбера в месяц, то рассчитываем комиссию за перевод
            boolean exceededLimitTransfersToClientSberWithoutCommissionMonthInRUB = ((SberPhysicalPersonProfile) clientProfile).exceededLimitTransfersToClientSberWithoutCommissionMonthInRUB(sum);
            if (exceededLimitTransfersToClientSberWithoutCommissionMonthInRUB)
                commission = (sum / 100) * clientProfile.getPercentOfCommissionForTransferInRUB();
        }
        return commission;
    }
}
