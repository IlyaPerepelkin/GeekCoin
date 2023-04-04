package Card;

import Account.PayCardAccount;
import Account.SberPayCardAccount;
import Bank.IBankServicePhysicalPerson;
import ClientProfile.PhysicalPersonProfile;

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
    public void addAccount(IBankServicePhysicalPerson bank, PhysicalPersonProfile physicalPersonProfile, String currencyCodeAccount) {
        // Открываем новый счет
        SberPayCardAccount sberPayCardAccount = (SberPayCardAccount) bank.openAccount(physicalPersonProfile, new SberPayCardAccount(), currencyCodeAccount);
        // Связываем созданный счет с картой
        sberPayCardAccount.addCard(this);
        // Добавляем созданный счет в массив других счетов мультивалютной карты
        getMulticurrencyAccounts().add(sberPayCardAccount);
    }

    @Override
    public void switchAccount(String currencyCodeAccount) {
        // реализовать и прислать свой вариант
        // если сложно будет подсказка в виде псеводокод
    }
}
