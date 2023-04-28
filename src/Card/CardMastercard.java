package Card;

import Account.PayCardAccount;
import Bank.Bank;
import Card.IPaySystem.IMastercard;
import ClientProfile.PhysicalPersonProfile;

public abstract class CardMastercard extends Card implements IMastercard {

    public CardMastercard(Bank bank, PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(bank, cardHolder, payCardAccount, pinCode);
    }

    @Override
    // Запросим код валюты платежной системы
    public String getCurrencyCodePaySystem(String country) {
        // по умолчанию null, потому что не во всех странах может использоваться данная платежная система
        String billingCurrencyCode = null;
        // если покупка в Турции, то валюта биллинга в $
        if (country.equalsIgnoreCase("Турция")) billingCurrencyCode = currencyCodePaySystemUSD;
        // если покупка во Франции, то есть в Еврозоне, то валюта биллинга в €
        if (country.equalsIgnoreCase("Франция")) billingCurrencyCode = currencyCodePaySystemEurozone;

        return billingCurrencyCode;
    }

    // Запросить обменный курс валют платежной системы
    public float getExchangeRatePaySystem(String currency, String currencyExchangeRate) {
        // TODO: Запрос к API Mastercard
        float exchangeRate = 0;
        // курс лиры к доллару
        if (currency.equals("TRY") && currencyExchangeRate.equals("USD")) exchangeRate = 0.056f;
        // курс лиры к евро
        if (currency.equals("TRY") && currencyExchangeRate.equals("EUR")) exchangeRate = 0.059f;
        return exchangeRate;
    }
}
