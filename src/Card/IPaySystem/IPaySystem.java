package Card.IPaySystem;

public interface IPaySystem {

    default float convertToCurrencyExchangeRatePaySystem(float sum, String fromCurrencyCode, String toBillingCurrencyCode) {
        float exchangeRateCurrencyToBillingCurrency = getExchangeRatePaySystem(fromCurrencyCode, toBillingCurrencyCode);
        float sumInBillingCurrency = sum * exchangeRateCurrencyToBillingCurrency;

        return sumInBillingCurrency;
    }

    // Запросим код валюты платежной системы
    String getCurrencyCodePaySystem(String country);

    float getExchangeRatePaySystem(String currency, String currencyExchangeRate);

}
