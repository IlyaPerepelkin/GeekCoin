package Card;

public class CardMastercard extends Card {

    private String currencyCodePaySystemUSD = "USD";

    private String currencyCodePaySystemEurozone = "EUR";


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

    @Override
    // Конвертировать в валюту по курсу платежной системы
    public float convertToCurrencyExchangeRatePaySystem(float sum, String fromCurrencyCode, String toBillingCurrencyCode) {
        // запросим курс валюты покупки к курсу валюты биллинга (к $ или к €) по курсу платежной системы Mastercard
        float exchangeRateCurrencyToBillingCurrency = getExchangeRatePaySystem(fromCurrencyCode, toBillingCurrencyCode);
        // получаем сумму покупки в валюте биллинга умножив сумму покупки на обменный курс валюты биллинга ($ или к €)
        float sumInBillingCurrency = sum * exchangeRateCurrencyToBillingCurrency;

        // возможна двойная конвертация через $ и затем еще €

        return sumInBillingCurrency;
    }

    // Запросить обменный курс валют платежной системы
    private float getExchangeRatePaySystem(String currency, String currencyExchangeRate) {
        // TODO: Запрос к API Mastercard
        float exchangeRate = 0;
        // курс лиры к доллару
        if (currency.equals("TRY") && currencyExchangeRate.equals("USD")) exchangeRate = 0.056f;
        // курс лиры к евро
        if (currency.equals("TRY") && currencyExchangeRate.equals("EUR")) exchangeRate = 0.059f;
        return exchangeRate;
    }
}
