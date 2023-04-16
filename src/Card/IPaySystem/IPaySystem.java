package Card.IPaySystem;

public interface IPaySystem {

    float sumInBillingCurrency = 0;
    
    // Конвертировать в валюту по курсу платежной системы
    default float convertToCurrencyExchangeRatePaySystem(float sum, String fromCurrencyCode, String toBillingCurrencyCode) {
        return sumInBillingCurrency;
    }

    // Запросим код валюты платежной системы
    String getCurrencyCodePaySystem(String country);

    float getExchangeRatePaySystem(String currency, String currencyExchangeRate);

}
