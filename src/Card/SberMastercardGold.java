package Card;

import Account.PayCardAccount;
import ClientProfile.PhysicalPersonProfile;

public final class SberMastercardGold extends CardMastercard {

    private static int count = 0;


    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        SberMastercardGold.count = count;
    }

    public SberMastercardGold(PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(cardHolder, payCardAccount, pinCode);
        count++;
    }

}
