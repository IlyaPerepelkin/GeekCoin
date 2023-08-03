package ru.geekstar.Card;

import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.Card.IPaySystem.IMastercard;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.Country;
import ru.geekstar.Currency;

import java.util.ArrayList;

public abstract class CardMastercard extends Card implements IMastercard {

    public CardMastercard(PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(cardHolder, payCardAccount, pinCode);
    }

    @Override
    // Запросим код валюты платежной системы
    public String getCurrencyCodePaySystem(String country) {
        // по умолчанию null, потому что не во всех странах может использоваться данная платежная система
        String billingCurrencyCode = null;
        // если покупка в Турции или Казахстане, то валюта биллинга в $
        if (country.equalsIgnoreCase(Country.TURKEY.getCountry()) || country.equalsIgnoreCase(Country.KAZAKHSTAN.getCountry())) billingCurrencyCode = CURRENCY_CODE_PAY_SYSTEM_USD;
        // если покупка во Франции, то есть в Еврозоне, то валюта биллинга в €
        if (country.equalsIgnoreCase(Country.FRANCE.getCountry())) billingCurrencyCode = CURRENCY_CODE_PAY_SYSTEM_EUROZONE;

        return billingCurrencyCode;
    }

    // Запросить обменный курс валют платежной системы
    public ArrayList<Float> getExchangeRatePaySystem(String currency, String currencyExchangeRate) {
        // TODO: Запрос к API Mastercard
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

        return exchangeRatePaySystem;
    }
}
