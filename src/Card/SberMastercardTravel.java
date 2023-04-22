package Card;

import Account.Account;
import Account.SberPayCardAccount;
import Bank.Sberbank;

public class SberMastercardTravel extends CardMastercard implements IMulticurrencyCard {

    @Override
    public void setPayCardAccount(Account payCardAccount) {
        this.setPayCardAccount(payCardAccount);
    }

    @Override
    public Account getMulticurrencyAccounts() {
        return multicurrencyAccounts;
    }

    @Override
    public void setMulticurrencyAccounts(Account multicurrencyAccounts) {
        this.multicurrencyAccounts = multicurrencyAccounts;
    }

    @Override
    public void addAccount(String currencyCodeAccount) {
        // Открываем новый счет
        SberPayCardAccount sberPayCardAccount = (SberPayCardAccount) (((Sberbank) this.getBank()).openAccount(this.getCardHolder(), new SberPayCardAccount(), currencyCodeAccount));
        // Связываем созданный счет с картой
        sberPayCardAccount.getCards().add(this);
        // Добавляем созданный счет в массив других счетов мультивалютной карты
        getMulticurrencyAccounts().add(sberPayCardAccount);
    }

}
