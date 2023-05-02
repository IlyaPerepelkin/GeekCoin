package Account;

import ClientProfile.PhysicalPersonProfile;

import java.util.ArrayList;

public class TinkoffPayCardAccount extends PayCardAccount {

    public TinkoffPayCardAccount(ArrayList<PhysicalPersonProfile> physicalPersonProfile, Class<? extends PayCardAccount> classPayCardAccount, String currencyCode) {
        super(physicalPersonProfile, classPayCardAccount, currencyCode);
    }


}
