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

    default void displayMulticurrencyCardTransactions() {
        getPayCardAccount().displayAccountTransactions();
        for (int i = 0; i < getMulticurrencyAccounts().size(); i++) {
            getMulticurrencyAccounts().get(i).displayAccountTransactions();
        }
    }
    /*
    default void displayMulticurrencyCardTransactions() {
        int countAllTransactions = 0;
        for (int idAccount = 0; idAccount < getMulticurrencyAccounts().size(); idAccount++) {
            countAllTransactions += getMulticurrencyAccounts().get(idAccount).getAllAccountTransactions().length;
        }

        String[] allTransactions = new String[countAllTransactions];
        int destPos = 0;
        for (int idAccount = 0; idAccount < getMulticurrencyAccounts().size(); idAccount++) {
            String[] allAccountTransactions = getMulticurrencyAccounts().get(idAccount).getAllAccountTransactions();
            System.arraycopy(allAccountTransactions, 0, allTransactions, destPos, allAccountTransactions.length);
            destPos += allAccountTransactions.length;
        }

        Arrays.sort(allTransactions);

        for (int idTransaction = 0; idTransaction < countAllTransactions; idTransaction++) {
            System.out.println("#" + (idTransaction + 1) + " " + allTransactions[idTransaction]);
        }
        System.out.println();
    }
*/
}
