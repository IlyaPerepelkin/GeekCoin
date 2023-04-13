package Card;

import Account.PayCardAccount;


import java.util.ArrayList;

public interface IMulticurrencyCard {

    ArrayList<PayCardAccount> getMulticurrencyAccounts();

    void setMulticurrencyAccounts(ArrayList<PayCardAccount> multicurrencyAccounts);

    void addAccount(String currencyCodeAccount);

    default void switchAccount(String currencyCodeAccount) {

    }

}
