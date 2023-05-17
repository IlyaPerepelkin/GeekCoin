package ru.geekstar.Card;

import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.Account.SberPayCardAccount;
import ru.geekstar.Bank.Sberbank;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;

import java.util.ArrayList;

public final class SberMastercardTravel extends CardMastercard implements IMulticurrencyCard {

    public static int count;

    ArrayList<PayCardAccount> multicurrencyAccounts = new ArrayList<>();


    @Override
    public ArrayList<PayCardAccount> getMulticurrencyAccounts() {
        return multicurrencyAccounts;
    }

    @Override
    public void setMulticurrencyAccounts(ArrayList<PayCardAccount> multicurrencyAccounts) {
        this.multicurrencyAccounts = multicurrencyAccounts;
    }

    public SberMastercardTravel(PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(cardHolder, payCardAccount, pinCode);
        addAccount("USD");
        addAccount("EUR");
        count++;
    }

    @Override
    public void addAccount(String currencyCodeAccount) {
        // Открываем новый счет
        SberPayCardAccount sberPayCardAccount = (SberPayCardAccount) (((Sberbank) this.getBank()).openAccount(this.getCardHolder(), SberPayCardAccount.class, currencyCodeAccount));
        // Связываем созданный счет с картой
        sberPayCardAccount.getCards().add(this);
        // Добавляем созданный счет в массив других счетов мультивалютной карты
        getMulticurrencyAccounts().add(sberPayCardAccount);
    }

}
