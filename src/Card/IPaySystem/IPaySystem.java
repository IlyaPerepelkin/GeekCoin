package Card.IPaySystem;

public interface IPaySystem {

    default float convertToCurrencyExchangeRatePaySystem(float sum, String fromCurrencyCode, String toBillingCurrencyCode) {
        float exchangeRateCurrencyToBillingCurrency = getExchangeRatePaySystem(fromCurrencyCode, toBillingCurrencyCode);
        float sumInBillingCurrency = sum * exchangeRateCurrencyToBillingCurrency;

        String currencyPayCode = bank.getCurrencyCode(country);
        String billingCurrencyCode = getCurrencyCodePaySystem(country);
        float sumPayInBillingCurrency = !currencyPayCode.equals(billingCurrencyCode) ? convertToCurrencyExchangeRatePaySystem(sum, currencyPayCode, billingCurrencyCode) : sum;
        String cardCurrencyCode = getPayCardAccount().getCurrencyCode();
        float sumPayInCardCurrency = !billingCurrencyCode.equals(cardCurrencyCode) ? bank.convertToCurrencyExchangeRateBank(sumPayInBillingCurrency, billingCurrencyCode, cardCurrencyCode) : sumPayInBillingCurrency;
        sumPayInCardCurrency = bank.round(sumPayInCardCurrency);

        return sumInBillingCurrency;
    }

    // Запросим код валюты платежной системы
    String getCurrencyCodePaySystem(String country);

    float getExchangeRatePaySystem(String currency, String currencyExchangeRate);

}
