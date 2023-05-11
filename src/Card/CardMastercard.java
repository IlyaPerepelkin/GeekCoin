package Card;

import Account.PayCardAccount;
import Card.IPaySystem.IMastercard;
import ClientProfile.PhysicalPersonProfile;

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
        // если покупка в Турции, то валюта биллинга в $
        if (country.equalsIgnoreCase("Турция")) billingCurrencyCode = CURRENCY_CODE_PAY_SYSTEM_USD;
        // если покупка во Франции, то есть в Еврозоне, то валюта биллинга в €
        if (country.equalsIgnoreCase("Франция")) billingCurrencyCode = CURRENCY_CODE_PAY_SYSTEM_EUR;

        return billingCurrencyCode;
    }

    // Запросить обменный курс валют платежной системы
    public ArrayList<Float> getExchangeRatePaySystem(String currency, String currencyExchangeRate) {
        // TODO: Запрос к API Mastercard

        ArrayList<Float> exchangeRates = new ArrayList<>();
        exchangeRates.add(0.056f); // курс лиры к доллару
        exchangeRates.add(0.059f); //курс лиры к евро

        if (currency.equals("TRY") && currencyExchangeRate.equals("USD")) {
            return exchangeRates;
        } else if (currency.equals("TRY") && currencyExchangeRate.equals("EUR")) {
            return exchangeRates;
        }
        return new ArrayList<>();
    }
}
