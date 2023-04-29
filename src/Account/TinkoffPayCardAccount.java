package Account;

import ClientProfile.PhysicalPersonProfile;

import java.util.ArrayList;

public class TinkoffPayCardAccount extends PayCardAccount {

    public TinkoffPayCardAccount(ArrayList<PhysicalPersonProfile> physicalPersonProfile, ArrayList<Account> account, String currencyCode) {
        super(physicalPersonProfile, account, currencyCode);
    }


}
