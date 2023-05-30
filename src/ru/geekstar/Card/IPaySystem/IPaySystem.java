package ru.geekstar.Card.IPaySystem;

import java.util.ArrayList;

public interface IPaySystem {

    default float convertToCurrencyExchangeRatePaySystem(float sum, String fromCurrencyCode, String toBillingCurrencyCode) {
        if (!fromCurrencyCode.equalsIgnoreCase(toBillingCurrencyCode)) {
            // запросить курс валюты покупки к курсу валюты биллинга по курсу платёжной системы
            ArrayList<Float> exchangeRateCurrencyToBillingCurrency = getExchangeRatePaySystem(fromCurrencyCode, toBillingCurrencyCode);
            // получаем сумму покупки в валюте биллинга умножив сумму покупки на обменный курс валюты биллинга
            sum *= exchangeRateCurrencyToBillingCurrency.get(0);
        }
        return sum;
    }


    // Запросим код валюты платежной системы
    String getCurrencyCodePaySystem(String country);

    ArrayList<Float> getExchangeRatePaySystem(String currency, String currencyExchangeRate);

}
