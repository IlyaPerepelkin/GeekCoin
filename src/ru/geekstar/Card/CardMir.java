package ru.geekstar.Card;

import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.Card.IPaySystem.IMir;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.Country;
import ru.geekstar.Currency;

import java.util.ArrayList;

public abstract class CardMir extends Card implements IMir {


    public CardMir(PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(cardHolder, payCardAccount, pinCode);
    }

    @Override
    // Запросим код валюты платежной системы
    public String getCurrencyCodePaySystem(String country) {
        // по умолчанию null, потому что не во всех странах может использоваться данная платежная система
        String billingCurrencyCode = null;
        // если покупка в Казахстане или в Турции, то валюта биллинга в ₽
        if (country.equalsIgnoreCase(Country.KAZAKHSTAN.toString()) || country.equalsIgnoreCase(Country.TURKEY.toString())) billingCurrencyCode = CURRENCY_CODE_PAY_SYSTEM_RUB;

        return billingCurrencyCode;
    }

    // Запросить обменный курс валют платежной системы
    @Override
    public ArrayList<Float> getExchangeRatePaySystem(String currency, String currencyExchangeRate) {
        // TODO: Запрос к API Mir
        ArrayList<Float> exchangeRatePaySystem = new ArrayList<>();

        // курс лиры к рублю
        if (currency.equals(Currency.TRY.toString()) && currencyExchangeRate.equals(Currency.RUB.toString())) {
            exchangeRatePaySystem.add(3.64f);
            exchangeRatePaySystem.add(4.028f);
        }

        // курс тенге к рублю
        if (currency.equals(Currency.KZT.toString()) && currencyExchangeRate.equals(Currency.RUB.toString())) {
            exchangeRatePaySystem.add(0.1728f); // курс покупки
            exchangeRatePaySystem.add(0.1914f); // курс продажи
        }

        return exchangeRatePaySystem;
    }
}
