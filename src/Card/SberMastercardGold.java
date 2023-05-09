package Card;

import Account.PayCardAccount;
import ClientProfile.PhysicalPersonProfile;

public final class SberMastercardGold extends CardMastercard {

    public static int count;

    public SberMastercardGold(PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(cardHolder, payCardAccount, pinCode);
        count++;
    }

}
