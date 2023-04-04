package Card;

import Account.PayCardAccount;
import Bank.IBankServicePhysicalPerson;
import ClientProfile.PhysicalPersonProfile;

import java.util.ArrayList;

public interface IMulticurrencyCard {

    ArrayList<PayCardAccount> getMulticurrencyAccounts();

    void setMulticurrencyAccounts(ArrayList<PayCardAccount> multicurrencyAccounts);

    void addAccount(IBankServicePhysicalPerson bank, PhysicalPersonProfile physicalPersonProfile, String currencyCodeAccount);

    void switchAccount(String currencyCodeAccount);

}
