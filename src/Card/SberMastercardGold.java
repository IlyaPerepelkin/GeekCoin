package Card;

import Account.PayCardAccount;
import ClientProfile.PhysicalPersonProfile;

public final class SberMastercardGold extends CardMastercard {

    public SberMastercardGold(PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(cardHolder, payCardAccount, pinCode);
    }

}
