package Card;

import Account.PayCardAccount;
import Card.IPaySystem.IMir;
import ClientProfile.PhysicalPersonProfile;

import java.util.ArrayList;

public abstract class CardMir extends Card implements IMir {


    public CardMir(PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(cardHolder, payCardAccount, pinCode);
    }

    @Override
    // Запросим код валюты платежной системы
    public String getCurrencyCodePaySystem(String country) {
        // по умолчанию null, потому что не во всех странах может использоваться данная платежная система
        String billingCurrencyCode = null;
        // если покупка в Казахстане, то валюта биллинга в ₽
        if (country.equalsIgnoreCase("Казахстан")) billingCurrencyCode = CURRENCY_CODE_PAY_SYSTEM_RUB;

        return billingCurrencyCode;
    }

    // Запросить обменный курс валют платежной системы
    public float getExchangeRatePaySystem(String currency, String currencyExchangeRate) {
        // TODO: Запрос к API Mir

        ArrayList<Float> exchangeRates = new ArrayList<>();
        exchangeRates.add(0.15f); // курс тенге к рублю

        if (currency.equals("KZT") && currencyExchangeRate.equals("RUB")) {
            return exchangeRates.get(0);
        } else {
            return 0;
        }

    }

}
