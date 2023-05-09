package Account;

import ClientProfile.PhysicalPersonProfile;
import Bank.Bank;

public class TinkoffPayCardAccount extends PayCardAccount {

    public TinkoffPayCardAccount(PhysicalPersonProfile accountHolder, String currencyCode, Bank bank) {
        super(accountHolder, currencyCode, bank);
    }


}
