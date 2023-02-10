package Card;

public class CardVisa extends Card {

    private String currencyCodePaySystemUSD = "USD";

    @Override
    // Запросим код валюты платежной системы
    public String getCurrencyCodePaySystem(String country) {
        // по умолчанию null, потому что не во всех странах может использоваться данная платежная система
        String billingCurrencyCode = null;
        // если покупка в Турции, то валюта биллинга в $
        if (country.equalsIgnoreCase("Турция")) billingCurrencyCode = currencyCodePaySystemUSD;

        return billingCurrencyCode;
    }

    @Override
    // Конвертировать в валюту по курсу платежной системы
    public float convertToCurrencyExchangeRatePaySystem(float sum, String fromCurrencyCode, String toBillingCurrencyCode) {
        // запросим курс валюты покупки к курсу валюты биллинга ($) по курсу платежной системы VISA
        float exchangeRateCurrencyToBillingCurrency = getExchangeRatePaySystem(fromCurrencyCode, toBillingCurrencyCode);
        // получаем сумму покупки в валюте биллинга умножив сумму покупки на обменный курс валюты биллинга ($)
        float sumInBillingCurrency = sum * exchangeRateCurrencyToBillingCurrency;

        return sumInBillingCurrency;
    }

    // Запросить обменный курс валют платежной системы
    private float getExchangeRatePaySystem(String currency, String currencyExchangeRate) {
        // TODO: Запрос к API Visa
        float exchangeRate = 0;
        // курс лиры к доллару
        if (currency.equals("TRY") && currencyExchangeRate.equals("USD")) exchangeRate = 0.056f;
        return exchangeRate;
    }

}
