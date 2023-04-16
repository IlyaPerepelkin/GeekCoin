package Bank;

import Account.Account;
import Card.Card;
import ClientProfile.PhysicalPersonProfile;
import PhysicalPerson.PhysicalPerson;

import java.util.ArrayList;

public interface IBankServicePhysicalPerson {

    PhysicalPersonProfile registerPhysicalPersonProfile(PhysicalPerson physicalPerson);

    default Card openCard(PhysicalPersonProfile physicalPersonProfile, Card card, String currencyCode, String pinCode){
        return card;
    }

    default Account openAccount(PhysicalPersonProfile physicalPersonProfile, Account account, String currencyCode) {
        return account;
    }

    default float convertToCurrencyExchangeRateBank(float sum, String fromCurrencyCode, String toCurrencyCode) {
        float exchangeRateToCardCurrency = getExchangeRateBank();
        float sumInCardCurrency = sum * exchangeRateToCardCurrency;

        return sumInCardCurrency;
    }

    public abstract float getExchangeRateBank();

}
