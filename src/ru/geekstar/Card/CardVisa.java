package ru.geekstar.Card;

import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.Card.IPaySystem.IVisa;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.Country;
import ru.geekstar.Currency;

import java.util.ArrayList;

public abstract class CardVisa extends Card implements IVisa {

    public CardVisa(PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(cardHolder, payCardAccount, pinCode);
    }

    @Override
    // Запросим код валюты платежной системы
    public String getCurrencyCodePaySystem(String country) {
        // по умолчанию null, потому что не во всех странах может использоваться данная платежная система
        String billingCurrencyCode = null;
        // если покупка в Турции или Казахстане или Франции, то валюта биллинга в $
        if (country.equalsIgnoreCase(Country.TURKEY.getCountry()) || country.equalsIgnoreCase(Country.KAZAKHSTAN.getCountry())
                || country.equalsIgnoreCase(Country.FRANCE.getCountry())) billingCurrencyCode = CURRENCY_CODE_PAY_SYSTEM_USD;

        return billingCurrencyCode;
    }

    // Запросить обменный курс валют платежной системы
    public ArrayList<Float> getExchangeRatePaySystem(String currency, String currencyExchangeRate) {
        // TODO: Запрос к API Visa
        ArrayList<Float> exchangeRatePaySystem = new ArrayList<>();

        // курс лиры в долларах
        if (currency.equals(Currency.TRY.toString()) && currencyExchangeRate.equals(Currency.USD.toString())) {
            exchangeRatePaySystem.add(0.0448f); // курс покупки
            exchangeRatePaySystem.add(0.0496f); // курс продажи
        }

        // курс тенге в долларах
        if (currency.equals(Currency.KZT.toString()) && currencyExchangeRate.equals(Currency.USD.toString())) {
            exchangeRatePaySystem.add(0.0021f);
            exchangeRatePaySystem.add(0.0023f);
        }

        // курс евро в долларах
        if (currency.equals(Currency.EUR.toString()) && currencyExchangeRate.equals(Currency.USD.toString())) {
            exchangeRatePaySystem.add(1.024f);
            exchangeRatePaySystem.add(1.132f);
        }

        return exchangeRatePaySystem;
    }

}
