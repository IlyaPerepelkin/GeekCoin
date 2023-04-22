package Card;

import Account.Account;
import Account.PayCardAccount;


public interface IMulticurrencyCard {

    Account multicurrencyAccounts;

    Account getPayCardAccount();

    void setPayCardAccount(Account payCardAccount);


    default Account getMulticurrencyAccounts() {
        return multicurrencyAccounts;
    }

    default void setMulticurrencyAccounts(Account multicurrencyAccounts) {
        this.multicurrencyAccounts = multicurrencyAccounts;

    }

    void addAccount(String currencyCodeAccount);

    default void switchAccount(String currencyCodeAccount) {
        for (int i = 0; i < multicurrencyAccounts.length(); i++) {
            PayCardAccount payCardAccount = multicurrencyAccounts.get(i);
            if (payCardAccount.getCurrencyCode().equals(currencyCodeAccount)) {
                multicurrencyAccounts.remove(payCardAccount);
                multicurrencyAccounts.add(getPayCardAccount());
                setPayCardAccount(payCardAccount);
            }
        }
    }

}
