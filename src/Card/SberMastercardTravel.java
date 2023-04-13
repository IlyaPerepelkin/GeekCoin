package Card;

import Account.PayCardAccount;
import Account.SberPayCardAccount;

import java.util.ArrayList;

public class SberMastercardTravel extends CardMastercard implements IMulticurrencyCard {

    private ArrayList<PayCardAccount> multicurrencyAccounts = new ArrayList<>();

    @Override
    public ArrayList<PayCardAccount> getMulticurrencyAccounts() {
        return multicurrencyAccounts;
    }

    @Override
    public void setMulticurrencyAccounts(ArrayList<PayCardAccount> multicurrencyAccounts) {
        this.multicurrencyAccounts = multicurrencyAccounts;
    }

    @Override
    public void addAccount(String currencyCodeAccount) {
        // Открываем новый счет
        SberPayCardAccount sberPayCardAccount = (SberPayCardAccount) this.getBank().openAccount(this.getCardHolder(), new SberPayCardAccount(), currencyCodeAccount);
        // Связываем созданный счет с картой
        sberPayCardAccount.getCards().add(this);
        // Добавляем созданный счет в массив других счетов мультивалютной карты
        getMulticurrencyAccounts().add(sberPayCardAccount);
    }

    @Override
    public void switchAccount(String currencyCodeAccount) {
        for (int i = 0; i < multicurrencyAccounts.size(); i++) {
            PayCardAccount payCardAccount = multicurrencyAccounts.get(i);
            if (payCardAccount.getCurrencyCode().equals(currencyCodeAccount)) {
                multicurrencyAccounts.remove(payCardAccount);
                multicurrencyAccounts.add(getPayCardAccount());
                setPayCardAccount(payCardAccount);
            }
        }
    }

}
