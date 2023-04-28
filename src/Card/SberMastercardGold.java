package Card;

import Account.PayCardAccount;
import Bank.Bank;
import ClientProfile.PhysicalPersonProfile;

public class SberMastercardGold extends CardMastercard {

    public SberMastercardGold(Bank bank, PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(bank, cardHolder, payCardAccount, pinCode);
    }

}
