package Card;

import Account.PayCardAccount;


import java.util.ArrayList;

public interface IMulticurrencyCard {

    ArrayList<PayCardAccount> multicurrencyAccounts = new ArrayList<>();


    ArrayList<PayCardAccount> getMulticurrencyAccounts();

    void setMulticurrencyAccounts(ArrayList<PayCardAccount> multicurrencyAccounts);


    void addAccount(String currencyCodeAccount);

    default void switchAccount(String currencyCodeAccount) {
        for (int i = 0; i < multicurrencyAccounts.size(); i++) {
            PayCardAccount payCardAccount = multicurrencyAccounts.get(i);
            if (payCardAccount.getCurrencyCode().equals(currencyCodeAccount)) {
                multicurrencyAccounts.remove(payCardAccount);
                multicurrencyAccounts.add(getPayCardAccount());
                setPayCardAccount(payCardAccount);
            }
        }
    }

    PayCardAccount getPayCardAccount();

    void setPayCardAccount(PayCardAccount payCardAccount);
    

}
