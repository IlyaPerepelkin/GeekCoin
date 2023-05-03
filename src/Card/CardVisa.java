package Card;

import Account.PayCardAccount;
import Card.IPaySystem.IVisa;
import ClientProfile.PhysicalPersonProfile;

import java.util.ArrayList;

public abstract class CardVisa extends Card implements IVisa {

    public CardVisa(PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(cardHolder, payCardAccount, pinCode);
    }

    @Override
    // Запросим код валюты платежной системы
    public String getCurrencyCodePaySystem(String country) {
        // по умолчанию null, потому что не во всех странах может использоваться данная платежная система
        String billingCurrencyCode = null;
        // если покупка в Турции, то валюта биллинга в $
        if (country.equalsIgnoreCase("Турция")) billingCurrencyCode = CURRENCY_CODE_PAY_SYSTEM_USD;

        return billingCurrencyCode;
    }

    // Запросить обменный курс валют платежной системы
    public float getExchangeRatePaySystem(String currency, String currencyExchangeRate) {
        // TODO: Запрос к API Visa

        ArrayList<Float> exchangeRates = new ArrayList<>();
        exchangeRates.add(0.056f); // курс лиры к доллару

        if (currency.equals("TRY") && currencyExchangeRate.equals("USD")) {
            return exchangeRates.get(0);
        } else {
            return 0;
        }
    }

}
