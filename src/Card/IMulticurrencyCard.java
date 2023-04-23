package Card;

import Account.PayCardAccount;

import java.util.ArrayList;


public interface IMulticurrencyCard {

    PayCardAccount getPayCardAccount();

    void setPayCardAccount(PayCardAccount payCardAccount);

    ArrayList<PayCardAccount> getMulticurrencyAccounts();

    void setMulticurrencyAccounts(ArrayList<PayCardAccount> multicurrencyAccounts);


    void addAccount(String currencyCodeAccount);

    default void switchAccount(String currencyCodeAccount) {
        for (int i = 0; i < getMulticurrencyAccounts().size(); i++) {
            PayCardAccount payCardAccount = getMulticurrencyAccounts().get(i);
            if (payCardAccount.getCurrencyCode().equals(currencyCodeAccount)) {
                getMulticurrencyAccounts().remove(payCardAccount);
                getMulticurrencyAccounts().add(getPayCardAccount());
                setPayCardAccount(payCardAccount);
            }
        }
    }

}
