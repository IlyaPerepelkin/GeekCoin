package ru.geekstar.Card;

import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.Account.SberPayCardAccount;
import ru.geekstar.Bank.Sberbank;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.Currency;

import java.util.ArrayList;

public final class SberMastercardTravel extends CardMastercard implements IMulticurrencyCard {

    public static int countCards;

    private ArrayList<PayCardAccount> multicurrencyAccounts = new ArrayList<>();

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
        addAccount(Currency.USD.toString());
        addAccount(Currency.EUR.toString());
        countCards++;
    }

    @Override
    public void addAccount(String currencyCodeAccount) {
        // Открываем новый счёт
        PayCardAccount payCardAccount = (PayCardAccount) ((Sberbank) this.getBank()).openAccount(this.getCardHolder(), SberPayCardAccount.class, currencyCodeAccount);
        // Связываем созданный счёт с картой
        payCardAccount.getCards().add(this);
        // Добавляем созданный счёт в массив других счетов мультивалютной карты
        getMulticurrencyAccounts().add(payCardAccount);
    }

}
