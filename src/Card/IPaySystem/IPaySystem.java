package Card.IPaySystem;

public interface IPaySystem {

    // Конвертировать в валюту по курсу платежной системы
    float convertToCurrencyExchangeRatePaySystem(float sum, String fromCurrencyCode, String toBillingCurrencyCode);

    // Запросим код валюты платежной системы
    String getCurrencyCodePaySystem(String country);

    float getExchangeRatePaySystem(String currency, String currencyExchangeRate);

}
