package Bank;

import Account.Account;
import Card.Card;
import ClientProfile.PhysicalPersonProfile;
import PhysicalPerson.PhysicalPerson;

import java.util.ArrayList;

public interface IBankServicePhysicalPerson {

    PhysicalPersonProfile registerPhysicalPersonProfile(ArrayList<PhysicalPersonProfile> physicalPersonProfile);

    default Card openCard(ArrayList<PhysicalPersonProfile> physicalPersonProfile, Card card, String currencyCode, String pinCode){
        return card;
    }

    default Account openAccount(ArrayList<PhysicalPersonProfile> physicalPersonProfile, Account account, String currencyCode) {
        return account;
    }

}
