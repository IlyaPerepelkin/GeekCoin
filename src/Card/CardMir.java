package Card;

import Card.IPaySystem.IMir;

public abstract class CardMir extends Card implements IMir {

    @Override
    // Запросим код валюты платежной системы
    public String getCurrencyCodePaySystem(String country) {
        // по умолчанию null, потому что не во всех странах может использоваться данная платежная система
        String billingCurrencyCode = null;
        // если покупка в Казахстане, то валюта биллинга в ₽
        if (country.equalsIgnoreCase("Казахстан")) billingCurrencyCode = currencyCodePaySystemRUB;

        return billingCurrencyCode;
    }

    // Запросить обменный курс валют платежной системы
    public float getExchangeRatePaySystem(String currency, String currencyExchangeRate) {
        // TODO: Запрос к API Mir
        float exchangeRate = 0;
        // курс тенге к рублю
        if (currency.equals("KZT") && currencyExchangeRate.equals("RUB")) exchangeRate = 0.15f;
        return exchangeRate;
    }

}
